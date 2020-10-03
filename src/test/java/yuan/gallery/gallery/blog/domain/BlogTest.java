package yuan.gallery.gallery.blog.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BlogTest {

    public static final Long BLOG_ID = 1L;
    public static final String BLOG_NAME = "test blog name";
    public static final String BLOG_URL = "https://kimseonggyu.tistory.com";
    public static final String BLOG_RSS_URL = "https://kimseonggyu.tistory.com/rss";
    public static final Blog BLOG = new Blog(BLOG_ID, BLOG_NAME, BLOG_URL, BLOG_RSS_URL);

    @DisplayName("정적 팩토리 메서드 테스트")
    @Test
    void of() {
        Blog blog = Blog.of(BLOG_NAME,BLOG_URL, BLOG_RSS_URL);

        assertThat(blog.getName()).isEqualTo(BLOG_NAME);
        assertThat(blog.getUrl()).isEqualTo(BLOG_URL);
        assertThat(blog.getRssUrl()).isEqualTo(BLOG_RSS_URL);
    }
}