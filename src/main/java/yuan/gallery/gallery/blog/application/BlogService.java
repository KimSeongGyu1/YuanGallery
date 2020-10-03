package yuan.gallery.gallery.blog.application;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import yuan.gallery.gallery.blog.domain.Blog;
import yuan.gallery.gallery.blog.domain.BlogRepository;
import yuan.gallery.gallery.blog.dto.BlogRegisterRequest;
import yuan.gallery.gallery.user.domain.User;
import yuan.gallery.gallery.user.exception.NotAuthorizedException;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public long registerBlog(User user, BlogRegisterRequest blogRegisterRequest) {
        if (user.isAdmin()) {
            Blog blog = blogRegisterRequest.toEntity();
            Blog persistedBlog = blogRepository.save(blog);

            return persistedBlog.getId();
        }
        throw new NotAuthorizedException();
    }
}
