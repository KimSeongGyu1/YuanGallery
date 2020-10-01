package yuan.gallery.gallery.blog.dto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static yuan.gallery.gallery.blog.domain.BlogTest.*;
import static yuan.gallery.gallery.blog.domain.PostTest.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import yuan.gallery.gallery.blog.domain.Blog;
import yuan.gallery.gallery.blog.domain.Post;

class PostResponseTest {

    @DisplayName("정적 팩토리 메서드 테스트")
    @Test
    void from() {
        LocalDateTime publishedDate = LocalDateTime.now();
        Blog blog = Blog.of(BLOG_NAME, BLOG_URL, BLOG_RSS_URL);
        Post post = Post.of(blog, POST_TITLE, POST_LINK, publishedDate);

        PostResponse postResponse = PostResponse.from(post);

        assertAll(
            () -> assertThat(postResponse.getId()).isEqualTo(post.getId()),
            () -> assertThat(postResponse.getBlogName()).isEqualTo(blog.getName()),
            () -> assertThat(postResponse.getTitle()).isEqualTo(post.getTitle()),
            () -> assertThat(postResponse.getLink()).isEqualTo(post.getLink()),
            () -> assertThat(postResponse.getPublishedDate()).isEqualTo(post.getPublishedDate())
        );
    }
}