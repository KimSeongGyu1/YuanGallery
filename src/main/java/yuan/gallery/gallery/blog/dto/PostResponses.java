package yuan.gallery.gallery.blog.dto;

import static java.util.stream.Collectors.*;

import java.util.List;

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

    public static PostResponses from(List<Post> posts) {
        return posts.stream()
            .map(PostResponse::from)
            .collect(collectingAndThen(toList(), PostResponses::new));
    }
}
