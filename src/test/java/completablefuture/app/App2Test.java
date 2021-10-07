package completablefuture.app;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App2Test {

  private static final Logger log = LoggerFactory.getLogger(App2Test.class);

  private ExecutorService executor = Executors.newFixedThreadPool(5);

  // already completed future
  @Test
  public void completed() throws Exception {
    CompletableFuture<Integer> answer = CompletableFuture.completedFuture(42);

    // does not block
    int fortyTwo = answer.get();
    log.debug("{}", fortyTwo);
  }

  // built-in thread pool (common thread pool)
  @Test
  public void supplyAsync() throws Exception {
    ScrapService scrap = new ScrapService("https://google.com");
    CompletableFuture<String> title = CompletableFuture.supplyAsync(() -> scrap.title());

    log.debug("Found: {}", title.get(3, TimeUnit.SECONDS));
  }

  // custom thread pool
  @Test
  public void supplyAsyncWithCustomExecutor() throws Exception {
    ScrapService scrap = new ScrapService("https://ubuntu.com");
    CompletableFuture<String> title = CompletableFuture
        .supplyAsync(() -> scrap.title(), executor)
        .orTimeout(3, TimeUnit.SECONDS);

    log.debug("Found: {}", title.get());
  }
}
