package yuan.gallery.gallery.blog.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yuan.gallery.gallery.blog.domain.Post;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class PostResponse {

    private Long id;
    private String blogName;
    private String title;
    private String link;
    private LocalDateTime publishedDate;

    public static PostResponse from(Post post) {
        return new PostResponse(post.getId(),
            post.getBlog().getName(),
            post.getTitle(),
            post.getLink(),
            post.getPublishedDate());
    }
}
