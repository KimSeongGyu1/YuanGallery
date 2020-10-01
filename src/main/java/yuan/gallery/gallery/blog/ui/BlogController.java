package yuan.gallery.gallery.blog.ui;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import yuan.gallery.gallery.blog.domain.Post;
import yuan.gallery.gallery.blog.domain.PostRepository;
import yuan.gallery.gallery.blog.dto.PostResponses;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogController {

    private final PostRepository postRepository;

    @GetMapping("/posts")
    public ResponseEntity<PostResponses> readAllPosts() {
        List<Post> posts = postRepository.findAll();

        return ResponseEntity.ok(PostResponses.from(posts));
    }

    //page=0&size=10&sort=publishedDate,desc
    @GetMapping("/postsInPage")
    public ResponseEntity<PostResponses> readInPage(Pageable pageable) {
        Page<Post> page = postRepository.findAll(pageable);
        List<Post> posts = page.getContent();

        return ResponseEntity.ok(PostResponses.from(posts));
    }
}
