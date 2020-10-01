package yuan.gallery.gallery.blog.application;

import static java.util.stream.Collectors.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import yuan.gallery.gallery.blog.domain.Blog;
import yuan.gallery.gallery.blog.domain.BlogRepository;
import yuan.gallery.gallery.blog.domain.Post;
import yuan.gallery.gallery.blog.domain.PostRepository;
import yuan.gallery.gallery.blog.domain.reader.BlogReader;

@Service
@RequiredArgsConstructor
public class BlogFeedService {

    private final BlogReader blogReader;
    private final BlogRepository blogRepository;
    private final PostRepository postRepository;

    // 1800000ms = 1800s = 30min
    @Scheduled(fixedRate = 1800000)
    public void updateBlogsFeed() {
        List<Blog> blogs = blogRepository.findAll();
        Set<Post> posts = readAllPosts(blogs);
        Set<Post> newPosts = filterOnlyNewPosts(posts);
        postRepository.saveAll(newPosts);
    }

    private Set<Post> readAllPosts(List<Blog> blogs) {
        return blogs.stream()
            .map(blog -> blogReader.readBlog(blog))
            .flatMap(Collection::stream)
            .collect(toSet());
    }

    private Set<Post> filterOnlyNewPosts(Set<Post> posts) {
        List<Post> existingPosts = postRepository.findAll();
        for (Post existingPost : existingPosts) {
            posts.remove(existingPost);
        }
        return posts;
    }
}
