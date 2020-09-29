package yuan.gallery.gallery.blog.domain;

import static org.assertj.core.api.Assertions.*;
import static yuan.gallery.gallery.blog.domain.BlogTest.*;
import static yuan.gallery.gallery.blog.domain.PostTest.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
}