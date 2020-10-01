package yuan.gallery.gallery.blog.ui;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import yuan.gallery.gallery.blog.domain.Post;
import yuan.gallery.gallery.blog.domain.PostRepository;
import yuan.gallery.gallery.blog.dto.PostResponses;

@RestController
@RequestMapping("/api/blog")
@AllArgsConstructor
public class BlogController {

    private PostRepository postRepository;

    @GetMapping("/posts")
    public ResponseEntity<PostResponses> readAllPosts() {
        List<Post> posts = postRepository.findAll();

        return ResponseEntity.ok(PostResponses.from(posts));
    }
}