package yuan.gallery.gallery.blog.domain;

import static org.assertj.core.api.Assertions.*;
import static yuan.gallery.gallery.blog.domain.BlogTest.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BlogRepositoryTest {

    @Autowired
    private BlogRepository blogRepository;

    @DisplayName("DataJpaTest 학습 테스트 - 주입 확인")
    @Test
    void injection() {
        assertThat(blogRepository).isNotNull();
    }

    @DisplayName("DataJpaTest 학습 테스트 - save() 동작 확인")
    @Test
    void save() {
        Blog blog = Blog.of(BLOG_NAME, BLOG_URL, BLOG_RSS_URL);
        assertThat(blog.getId()).isNull();

        blogRepository.save(blog);
        assertThat(blog.getId()).isNotNull();
    }

    @DisplayName("DataJpaTest 학습 테스트 - findById() 동작 확인")
    @Test
    void findById() {
        Blog blog = Blog.of(BLOG_NAME, BLOG_URL, BLOG_RSS_URL);
        blogRepository.save(blog);

        Blog persistedBlog = blogRepository.findById(blog.getId()).get();

        assertThat(persistedBlog).isEqualTo(blog);
    }
}