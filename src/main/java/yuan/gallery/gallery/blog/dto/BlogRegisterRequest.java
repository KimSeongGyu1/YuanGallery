package yuan.gallery.gallery.blog.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yuan.gallery.gallery.blog.domain.Blog;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BlogRegisterRequest {

    private String name;
    private String url;
    private String rssUrl;

    public Blog toEntity() {
        return Blog.of(name, url, rssUrl);
    }
}
