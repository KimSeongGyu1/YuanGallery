package yuan.gallery.gallery.blog.domain.reader;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import yuan.gallery.gallery.blog.domain.Blog;
import yuan.gallery.gallery.blog.domain.Post;


@ExtendWith(MockitoExtension.class)
class BlogReaderTest {

    private static final String BLOG_NAME = "blog name";
    private static final String BLOG_URL = "blog url";
    private static final String BLOG_RSS_URL = "blog rss url";

    private static final String POST_ONE_TITLE = "post one title";
    private static final String POST_ONE_LINK = "post one link";
    private static final Date POST_ONE_DATE = new Date();
    private static final LocalDateTime POST_ONE_LOCAL_DATE_TIME = POST_ONE_DATE.toInstant()
        .atZone(ZoneId.of("Asia/Seoul"))
        .toLocalDateTime();

    private static final String POST_TWO_TITLE = "post two title";
    private static final String POST_TWO_LINK = "post two link";
    private static final Date POST_TWO_DATE = new Date();
    private static final LocalDateTime POST_TWO_LOCAL_DATE_TIME = POST_TWO_DATE.toInstant()
        .atZone(ZoneId.of("Asia/Seoul"))
        .toLocalDateTime();

    @Mock
    private RssFeedReader rssFeedReader;

    @DisplayName("rss 피드 읽어오기 테스트")
    @Test
    void read() {
        given(rssFeedReader.readFeed(BLOG_RSS_URL)).willReturn(expectedFeed());

        Blog blog = Blog.of(BLOG_NAME, BLOG_URL, BLOG_RSS_URL);
        BlogReader blogReader = new BlogReader(rssFeedReader);
        Set<Post> posts = blogReader.readBlog(blog);

        assertAll(
            () -> assertThat(posts.size()).isEqualTo(2),
            () -> assertThat(posts).contains(Post.of(blog, POST_ONE_TITLE, POST_ONE_LINK, POST_ONE_LOCAL_DATE_TIME)),
            () -> assertThat(posts).contains(Post.of(blog, POST_TWO_TITLE, POST_TWO_LINK, POST_TWO_LOCAL_DATE_TIME))
        );
    }

    private SyndFeed expectedFeed() {
        SyndFeed syndFeed = new SyndFeedImpl();

        syndFeed.setLink(BLOG_URL);

        SyndEntry entry1 = new SyndEntryImpl();
        entry1.setTitle(POST_ONE_TITLE);
        entry1.setLink(POST_ONE_LINK);
        entry1.setPublishedDate(POST_ONE_DATE);

        SyndEntry entry2 = new SyndEntryImpl();
        entry2.setTitle(POST_TWO_TITLE);
        entry2.setLink(POST_TWO_LINK);
        entry2.setPublishedDate(POST_TWO_DATE);

        syndFeed.setEntries(Arrays.asList(entry1, entry2));

        return syndFeed;
    }
}