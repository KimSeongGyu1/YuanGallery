package yuan.gallery.gallery.blog.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BlogTest {

    public static final String BLOG_NAME = "test blog name";
    public static final String BLOG_URL = "https://jojoldu.tistory.com";
    public static final String BLOG_RSS_URL = "https://jojoldu.tistory.com/rss";

    @DisplayName("정적 팩토리 메서드 테스트")
    @Test
    void of() {
        Blog blog = Blog.of(BLOG_NAME,BLOG_URL, BLOG_RSS_URL);
        assertThat(blog).isExactlyInstanceOf(Blog.class);
    }
}