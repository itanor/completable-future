package completablefuture.app;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ScrapService {

  private final String url;

  public ScrapService(String url) {
    this.url = url;
  }

  public String title() {
    try {
      Document document = Jsoup.connect(url).get();
      return document.title();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
