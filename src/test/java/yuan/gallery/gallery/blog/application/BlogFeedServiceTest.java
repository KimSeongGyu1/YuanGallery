package yuan.gallery.gallery.blog.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static yuan.gallery.gallery.blog.domain.BlogTest.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import yuan.gallery.gallery.blog.domain.Blog;
import yuan.gallery.gallery.blog.domain.BlogRepository;
import yuan.gallery.gallery.blog.domain.Post;
import yuan.gallery.gallery.blog.domain.PostRepository;
import yuan.gallery.gallery.blog.domain.reader.BlogReader;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class BlogFeedServiceTest {

    @Mock
    BlogReader blogReader;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    PostRepository postRepository;

    @DisplayName("기존의 Post는 유지되고 새로운 Post만 추가되는지 테스트")
    @Test
    void updateBlogsFeed() {
        Blog blog = Blog.of(BLOG_NAME, BLOG_URL, BLOG_RSS_URL);
        blogRepository.save(blog);

        Set<Post> posts = new HashSet<Post>() {{
           add(Post.of(blog, "title one", "link one", LocalDateTime.now()));
           add(Post.of(blog, "title two", "link two", LocalDateTime.now()));
        }};
        given(blogReader.readBlog(any())).willReturn(posts);

        BlogFeedService blogFeedService = new BlogFeedService(blogReader, blogRepository, postRepository);

        blogFeedService.updateBlogsFeed();
        Long firstCount = postRepository.count();

        blogFeedService.updateBlogsFeed();
        assertThat(postRepository.count()).isEqualTo(firstCount);
    }
}