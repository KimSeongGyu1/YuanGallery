package yuan.gallery.gallery.blog.ui;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static yuan.gallery.gallery.blog.domain.BlogTest.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import yuan.gallery.gallery.MvcTest;
import yuan.gallery.gallery.blog.domain.Blog;
import yuan.gallery.gallery.blog.domain.Post;
import yuan.gallery.gallery.blog.domain.PostRepository;

@WebMvcTest(BlogController.class)
class BlogControllerTest extends MvcTest {

    @MockBean
    PostRepository postRepository;

    @Test
    void readAllPosts() throws Exception {
        Blog blog = Blog.of(BLOG_NAME, BLOG_URL, BLOG_RSS_URL);
        List<Post> posts = Arrays.asList(
            Post.of(blog, "title1", "link1", LocalDateTime.now()),
            Post.of(blog, "title2", "link2", LocalDateTime.now()));
        given(postRepository.findAll()).willReturn(posts);

        getAction("/api/blog/posts")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.postResponses.length()", is(2)))
            .andExpect(jsonPath("$.postResponses[0].title", is("title1")))
            .andExpect(jsonPath("$.postResponses[1].title", is("title2")));
    }
}