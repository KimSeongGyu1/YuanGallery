package yuan.gallery.gallery.blog.ui;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import yuan.gallery.gallery.blog.application.BlogService;
import yuan.gallery.gallery.blog.domain.Blog;
import yuan.gallery.gallery.blog.domain.Post;
import yuan.gallery.gallery.blog.domain.PostRepository;
import yuan.gallery.gallery.blog.dto.BlogRegisterRequest;
import yuan.gallery.gallery.blog.dto.PostResponses;
import yuan.gallery.gallery.user.domain.User;
import yuan.gallery.gallery.user.ui.auth.LoginUser;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final PostRepository postRepository;

    //page=0&size=10&sort=publishedDate,desc
    @GetMapping("/posts")
    public ResponseEntity<PostResponses> readInPage(Pageable pageable) {
        Page<Post> page = postRepository.findAll(pageable);
        return ResponseEntity.ok(PostResponses.from(page));
    }

    //searchTitle=title&page=0&size=3&sort=publishedDate,desc
    @GetMapping("/search")
    public ResponseEntity<PostResponses> search(
        @RequestParam String searchTitle,
        Pageable pageable
    ) {
        Page<Post> page = postRepository.findByTitleContaining(searchTitle, pageable);
        return ResponseEntity.ok(PostResponses.from(page));
    }

    @PostMapping
    public ResponseEntity<Void> registerBlog(
        @LoginUser User user,
        @RequestBody BlogRegisterRequest blogRegisterRequest
    ) {
        long blogId = blogService.registerBlog(user, blogRegisterRequest);
        return ResponseEntity
                .created(URI.create("/api/blog/" + blogId))
                .build();
    }
}
