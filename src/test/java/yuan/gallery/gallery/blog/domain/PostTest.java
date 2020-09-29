package yuan.gallery.gallery.blog.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static yuan.gallery.gallery.blog.domain.BlogTest.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PostTest {

    public static final String POST_TITLE = "post title";

    @DisplayName("정적 팩토리 메서드 테스트")
    @Test
    void of() {
        Blog blog = Blog.of(BLOG_NAME, BLOG_URL, BLOG_RSS_URL);
        Post post = Post.of(blog, POST_TITLE, LocalDateTime.now());

        assertThat(post).isExactlyInstanceOf(Post.class);
    }
}