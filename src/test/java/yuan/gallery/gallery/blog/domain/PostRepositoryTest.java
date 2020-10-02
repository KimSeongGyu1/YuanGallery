package yuan.gallery.gallery.blog.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static yuan.gallery.gallery.blog.domain.BlogTest.*;
import static yuan.gallery.gallery.blog.domain.PostTest.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    BlogRepository blogRepository;

    @DisplayName("주입 확인")
    @Test
    void injection() {
        assertThat(postRepository).isNotNull();
    }

    @DisplayName("ManyToOne 관계 학습 테스트 - save()")
    @Test
    void saveManyToOne() {
        Blog blog = Blog.of(BLOG_NAME, BLOG_URL, BLOG_RSS_URL);
        Post post = Post.of(blog, POST_TITLE, POST_LINK, LocalDateTime.now());

        postRepository.save(post);

        Post persistedPost = postRepository.findById(post.getId()).get();
        assertThat(persistedPost.getBlog().getName()).isEqualTo(BLOG_NAME);
        assertThat(persistedPost.getBlog().getId()).isNull();

        blogRepository.save(blog);
        assertThat(persistedPost.getBlog().getId()).isNotNull();
    }

    @DisplayName("복합 유니크 키 학습 테스트")
    @Test
    void saveUnique() {
        Blog blog = Blog.of(BLOG_NAME, BLOG_URL, BLOG_RSS_URL);
        Post post = Post.of(blog, POST_TITLE, POST_LINK, LocalDateTime.now());
        blogRepository.save(blog);
        postRepository.save(post);

        Post post2 = Post.of(blog, POST_TITLE, POST_LINK, LocalDateTime.now());
        assertThatThrownBy(() -> postRepository.save(post2)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("페이지네이션 학습 테스트")
    @Test
    void findInPage() {
        blogRepository.deleteAll();
        postRepository.deleteAll();

        Blog blog = Blog.of(BLOG_NAME, BLOG_URL, BLOG_RSS_URL);
        blogRepository.save(blog);
        postRepository.save(Post.of(blog, "title1", "link1", LocalDateTime.now()));
        postRepository.save(Post.of(blog, "title2", "link2", LocalDateTime.now()));
        postRepository.save(Post.of(blog, "title3", "link3", LocalDateTime.now()));
        postRepository.save(Post.of(blog, "title4", "link4", LocalDateTime.now()));
        postRepository.save(Post.of(blog, "title5", "link5", LocalDateTime.now()));

        PageRequest pageRequest =
            PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "publishedDate"));

        Page<Post> page = postRepository.findAll(pageRequest);
        List<Post> posts = page.getContent();

        assertAll(
            () -> assertThat(page.getTotalElements()).isEqualTo(5),
            () -> assertThat(page.getNumber()).isEqualTo(0),
            () -> assertThat(page.getTotalPages()).isEqualTo(2),
            () -> assertThat(page.isFirst()).isTrue(),
            () -> assertThat(page.hasNext()).isTrue(),

            () -> assertThat(posts.size()).isEqualTo(3),
            () -> assertThat(posts.get(0).getTitle()).isEqualTo("title5"),
            () -> assertThat(posts.get(1).getTitle()).isEqualTo("title4"),
            () -> assertThat(posts.get(2).getTitle()).isEqualTo("title3")
        );
    }

    @DisplayName("검색 학습 테스트")
    @Test
    void findByTitleContaining() {
        blogRepository.deleteAll();
        postRepository.deleteAll();

        Blog blog = Blog.of(BLOG_NAME, BLOG_URL, BLOG_RSS_URL);
        blogRepository.save(blog);
        postRepository.save(Post.of(blog, "title1", "link1", LocalDateTime.now()));
        postRepository.save(Post.of(blog, "title2", "link2", LocalDateTime.now()));
        postRepository.save(Post.of(blog, "title3", "link3", LocalDateTime.now()));
        postRepository.save(Post.of(blog, "title4", "link4", LocalDateTime.now()));
        postRepository.save(Post.of(blog, "other", "link5", LocalDateTime.now()));

        PageRequest pageRequest =
            PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "publishedDate"));

        Page<Post> page1 = postRepository.findByTitleContaining("title", pageRequest);
        Page<Post> page2 = postRepository.findByTitleContaining("other", pageRequest);

        assertAll(
            () -> assertThat(page1.getTotalElements()).isEqualTo(4),
            () -> assertThat(page1.getContent().get(0).getTitle()).isEqualTo("title4"),

            () -> assertThat(page2.getTotalElements()).isEqualTo(1),
            () -> assertThat(page2.getContent().get(0).getTitle()).isEqualTo("other")
        );
    }
}
