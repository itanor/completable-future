package completablefuture.app;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App3Test {

  private static final Logger log = LoggerFactory.getLogger(App2Test.class);

  private ExecutorService executor = Executors.newFixedThreadPool(5);

  // callback hell, does not compose
  @Test
  public void callbacks() throws Exception {
    ScrapService scrap = new ScrapService("https://google.com");
    CompletableFuture<String> titleFuture = CompletableFuture
        .supplyAsync(() -> scrap.title());

    titleFuture
      .completeOnTimeout("no title", 200, TimeUnit.MILLISECONDS)
      .thenAccept(title -> log.debug("Found: {}", title));
  }
}
