package yuan.gallery.gallery.blog.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"blog_id", "title"}
        )
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    private String title;

    private String link;

    private LocalDateTime publishedDate;

    public static Post of(Blog blog, String title, String link, LocalDateTime publishedDate) {
        return new Post(null, blog, title, link, publishedDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Post post = (Post)o;
        return Objects.equals(blog, post.blog) &&
            Objects.equals(title, post.title) &&
            Objects.equals(link, post.link) &&
            Objects.equals(publishedDate, post.publishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blog, title, link, publishedDate);
    }
}
