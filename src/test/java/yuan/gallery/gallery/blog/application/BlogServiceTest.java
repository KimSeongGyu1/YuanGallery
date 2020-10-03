package yuan.gallery.gallery.blog.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static yuan.gallery.gallery.blog.domain.BlogTest.*;
import static yuan.gallery.gallery.user.domain.UserTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import yuan.gallery.gallery.blog.domain.BlogRepository;
import yuan.gallery.gallery.blog.dto.BlogRegisterRequest;
import yuan.gallery.gallery.user.domain.User;
import yuan.gallery.gallery.user.exception.NotAuthorizedException;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

    private BlogService blogService;

    @Mock
    BlogRepository blogRepository;

    @BeforeEach
    void setUp() {
        blogService = new BlogService(blogRepository);
    }

    @Test
    void registerBlog() {
        given(blogRepository.save(any())).willReturn(BLOG);

        User user = ADMIN_USER;
        BlogRegisterRequest blogRegisterRequest = new BlogRegisterRequest(BLOG_NAME, BLOG_URL, BLOG_RSS_URL);
        assertThat(blogService.registerBlog(user, blogRegisterRequest)).isEqualTo(BLOG_ID);
    }

    @Test
    void registerBlogWithoutAuth() {
        User user = User.of("name", "password", false);
        BlogRegisterRequest blogRegisterRequest = new BlogRegisterRequest(BLOG_NAME, BLOG_URL, BLOG_RSS_URL);
        assertThatThrownBy(() -> blogService.registerBlog(user, blogRegisterRequest))
            .isInstanceOf(NotAuthorizedException.class);
    }
}