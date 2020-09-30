package yuan.gallery.gallery.blog.domain.reader;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.model.HttpRequest.*;
import static org.mockserver.model.HttpResponse.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import yuan.gallery.gallery.blog.exception.FailToReadFeedException;

class RssFeedReaderTest {

    private static final String BLOG_URL = "localhost";
    private static final int BLOG_PORT = 8888;
    private static final String BLOG_RSS_PATH = "/rss";

    private static final String POST_ONE_TITLE = "포스트 하나";
    private static final String POST_ONE_LINK = "https://yuan.tistory.com/1";
    private static final String POST_ONE_DATE_IN_XML = "Tue, 29 Sep 2020 09:58:44 +0900";
    private static final String POST_ONE_DATE_TO_STRING = "Tue Sep 29 09:58:44 KST 2020";

    private static final String POST_TWO_TITLE = "포스트 둘";
    private static final String POST_TWO_LINK = "https://yuan.tistory.com/2";
    private static final String POST_TWO_DATE_IN_XML = "Mon, 21 Sep 2020 22:31:45 +0900";
    private static final String POST_TWO_DATE_TO_STRING = "Mon Sep 21 22:31:45 KST 2020";

    private ClientAndServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = ClientAndServer.startClientAndServer(BLOG_PORT);
        new MockServerClient(BLOG_URL, BLOG_PORT)
            .when(
                request()
                    .withMethod("GET")
                    .withPath(BLOG_RSS_PATH)
            )
            .respond(
                response()
                    .withHeader(new Header("Content-Type", "text/xml;charset=utf-8"))
                    .withBody("<rss version=\"2.0\">\n"
                        + "<channel>\n"
                        + "<title>유안 블로그</title>\n"
                        + "<link>" + BLOG_URL + ":" + BLOG_PORT + "</link>\n"
                        + "<description>우테코 2기 유안입니다</description>\n"
                        + "<language>ko</language>\n"
                        + "<pubDate>Tue, 29 Sep 2020 23:58:05 +0900</pubDate>\n"
                        + "<generator>TISTORY</generator>\n"
                        + "<ttl>100</ttl>\n"
                        + "<managingEditor>유안</managingEditor>"
                        + "\n"
                        + "<item>\n"
                        + "<title>" + POST_ONE_TITLE + "</title>\n"
                        + "<link>" + POST_ONE_LINK + "</link>\n"
                        + "<pubDate>" + POST_ONE_DATE_IN_XML + "</pubDate>\n"
                        + "</item>"
                        + "\n"
                        + "<item>\n"
                        + "<title>" + POST_TWO_TITLE + "</title>\n"
                        + "<link>" + POST_TWO_LINK + "</link>\n"
                        + "<pubDate>" + POST_TWO_DATE_IN_XML + "</pubDate>\n"
                        + "</item>"
                        + "\n"
                        + "</channel>\n"
                        + "</rss>")
            );
    }

    @AfterEach
    void shutDown() {
        mockServer.stop();
    }

    @DisplayName("피드 읽어오기 테스트")
    @Test
    void readFeed() {
        RssFeedReader rssFeedReader = new RssFeedReader();
        SyndFeed syndFeed = rssFeedReader.readFeed("http://" + BLOG_URL + ":" + BLOG_PORT + BLOG_RSS_PATH);
        List<SyndEntry> entries = syndFeed.getEntries();

        assertAll(
            () -> assertThat(syndFeed.getLink()).isEqualTo(BLOG_URL + ":" + BLOG_PORT),
            () -> assertThat(entries.get(0).getTitle()).isEqualTo(POST_ONE_TITLE),
            () -> assertThat(entries.get(0).getLink()).isEqualTo(POST_ONE_LINK),
            () -> assertThat(entries.get(0).getPublishedDate().toString()).isEqualTo(POST_ONE_DATE_TO_STRING),
            () -> assertThat(entries.get(1).getTitle()).isEqualTo(POST_TWO_TITLE),
            () -> assertThat(entries.get(1).getLink()).isEqualTo(POST_TWO_LINK),
            () -> assertThat(entries.get(1).getPublishedDate().toString()).isEqualTo(POST_TWO_DATE_TO_STRING)
        );
    }

    @DisplayName("피드 읽어오기 실패 테스트")
    @Test
    void FailToReadFeed() {
        RssFeedReader rssFeedReader = new RssFeedReader();

        assertThatThrownBy(() -> rssFeedReader.readFeed("strangeUrl"))
            .isInstanceOf(FailToReadFeedException.class);
    }
}