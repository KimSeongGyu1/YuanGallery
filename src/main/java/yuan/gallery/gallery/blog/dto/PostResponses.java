package yuan.gallery.gallery.blog.dto;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yuan.gallery.gallery.blog.domain.Post;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class PostResponses {

    private List<PostResponse> postResponses;
    private long pageCount;

    public static PostResponses from(Page<Post> page) {
        List<Post> posts = page.getContent();
        long pageCount = page.getTotalPages();

        return posts.stream()
            .map(PostResponse::from)
            .collect(collectingAndThen(toList(), postResponses -> new PostResponses(postResponses, pageCount)));
    }
}
