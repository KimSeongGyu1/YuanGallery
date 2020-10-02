package yuan.gallery.gallery.blog.ui;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static yuan.gallery.gallery.blog.domain.BlogTest.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import yuan.gallery.gallery.MvcTest;
import yuan.gallery.gallery.blog.domain.Blog;
import yuan.gallery.gallery.blog.domain.Post;
import yuan.gallery.gallery.blog.domain.PostRepository;

@WebMvcTest(BlogController.class)
class BlogControllerTest extends MvcTest {

    @MockBean
    PostRepository postRepository;

    @DisplayName("페이지네이션 테스트")
    @Test
    void readInPage() throws Exception {
        Blog blog = Blog.of(BLOG_NAME, BLOG_URL, BLOG_RSS_URL);
        List<Post> posts = Arrays.asList(
            Post.of(blog, "title1", "link1", LocalDateTime.now()),
            Post.of(blog, "title2", "link2", LocalDateTime.now()),
            Post.of(blog, "title3", "link3", LocalDateTime.now()));
        Page<Post> page = new PageImpl<>(posts);
        given(postRepository.findAll((Pageable)any())).willReturn(page);

        getAction("/api/blog/posts?page=0&size=3&sort=publishedDate,desc")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.postResponses.length()", is(3)))
            .andExpect(jsonPath("$.postResponses[0].title", is("title1")))
            .andExpect(jsonPath("$.postResponses[1].title", is("title2")))
            .andExpect(jsonPath("$.postResponses[2].title", is("title3")));
    }

    @DisplayName("검색 테스트")
    @Test
    void search() throws Exception {
        Blog blog = Blog.of(BLOG_NAME, BLOG_URL, BLOG_RSS_URL);
        List<Post> posts = Arrays.asList(
            Post.of(blog, "title1", "link1", LocalDateTime.now()),
            Post.of(blog, "title2", "link2", LocalDateTime.now()),
            Post.of(blog, "title3", "link3", LocalDateTime.now()));
        Page<Post> page = new PageImpl<>(posts);
        given(postRepository.findByTitleContaining(anyString(), any())).willReturn(page);

        getAction("/api/blog/search?searchTitle=title&page=0&size=3&sort=publishedDate,desc")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.postResponses.length()", is(3)))
            .andExpect(jsonPath("$.postResponses[0].title", is("title1")))
            .andExpect(jsonPath("$.postResponses[1].title", is("title2")))
            .andExpect(jsonPath("$.postResponses[2].title", is("title3")));
    }
}