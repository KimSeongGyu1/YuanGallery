package yuan.gallery.gallery.blog.dto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static yuan.gallery.gallery.blog.domain.BlogTest.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import yuan.gallery.gallery.blog.domain.Blog;
import yuan.gallery.gallery.blog.domain.Post;

class PostResponsesTest {

    @Test
    void from() {
        Blog blog = Blog.of(BLOG_NAME, BLOG_URL, BLOG_RSS_URL);
        Post post1 = Post.of(blog, "title1", "link1", LocalDateTime.now());
        Post post2 = Post.of(blog, "title2", "link2", LocalDateTime.now());
        List<Post> posts = Arrays.asList(post1, post2);
        Page<Post> page = new PageImpl<>(posts);

        PostResponses postResponses = PostResponses.from(page);

        assertAll(
            () -> assertThat(postResponses.getPageCount()).isEqualTo(1),

            () -> assertThat(postResponses.getPostResponses().get(0).getId()).isEqualTo(post1.getId()),
            () -> assertThat(postResponses.getPostResponses().get(0).getBlogName()).isEqualTo(blog.getName()),
            () -> assertThat(postResponses.getPostResponses().get(0).getTitle()).isEqualTo(post1.getTitle()),
            () -> assertThat(postResponses.getPostResponses().get(0).getLink()).isEqualTo(post1.getLink()),
            () -> assertThat(postResponses.getPostResponses().get(0).getPublishedDate()).isEqualTo(post1.getPublishedDate()),

            () -> assertThat(postResponses.getPostResponses().get(1).getId()).isEqualTo(post2.getId()),
            () -> assertThat(postResponses.getPostResponses().get(1).getBlogName()).isEqualTo(blog.getName()),
            () -> assertThat(postResponses.getPostResponses().get(1).getTitle()).isEqualTo(post2.getTitle()),
            () -> assertThat(postResponses.getPostResponses().get(1).getLink()).isEqualTo(post2.getLink()),
            () -> assertThat(postResponses.getPostResponses().get(1).getPublishedDate()).isEqualTo(post2.getPublishedDate())
        );
    }
}