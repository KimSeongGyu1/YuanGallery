package yuan.gallery.gallery.blog.domain.reader;

import java.io.IOException;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import yuan.gallery.gallery.blog.exception.FailToReadFeedException;

@Component
public class RssFeedReader {

    public SyndFeed readFeed(String rssUrl) {
        try {
            URL url = new URL(rssUrl);
            XmlReader reader = new XmlReader(url);
            return new SyndFeedInput().build(reader);
        } catch (FeedException | IOException e) {
            throw new FailToReadFeedException();
        }
    }
}
