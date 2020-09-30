package yuan.gallery.gallery.blog.domain.reader;

import static java.util.stream.Collectors.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import lombok.AllArgsConstructor;
import yuan.gallery.gallery.blog.domain.Blog;
import yuan.gallery.gallery.blog.domain.Post;

@AllArgsConstructor
public class BlogReader {

    private RssFeedReader rssFeedReader;

    public Set<Post> readBlog(Blog blog) {
        SyndFeed feed = rssFeedReader.readFeed(blog.getRssUrl());

        return feed.getEntries()
            .stream()
            .map(entry -> parseToPost(blog, entry))
            .collect(toSet());
    }

    private Post parseToPost(Blog blog, SyndEntry syndEntry) {
        String title = syndEntry.getTitle();
        String link = syndEntry.getLink();
        LocalDateTime publishedDate = parseToLocalDateTime(syndEntry.getPublishedDate());

        return Post.of(blog, title, link, publishedDate);
    }

    private LocalDateTime parseToLocalDateTime(Date date) {
        return date.toInstant()
            .atZone(ZoneId.of("Asia/Seoul"))
            .toLocalDateTime();
    }
}
