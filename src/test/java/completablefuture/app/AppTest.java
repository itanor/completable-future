package completablefuture.app;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppTest {

  private static final Logger log = LoggerFactory.getLogger(AppTest.class);

  private ExecutorService executor = Executors.newFixedThreadPool(10);

  // blocking method calls
  @Test
  public void blockingCall() {
    ScrapService scrap = new ScrapService("https://stackoverflow.com");
    log.debug("stackoverflow title: {}", scrap.title());
  }

  @Test
  public void executorService() throws Exception {
    ScrapService scrap = new ScrapService("https://google.com");

    Callable<String> task = () -> scrap.title();
    Future<String> titleFuture = executor.submit(task);

    String title = titleFuture.get();
    log.debug("google title: {}", title);
  }

  // cannot compose...
  @Test
  public void waitForFirstOrAll() throws Exception {
    Future<String> githubTitleFuture = findTitleFor("http://github.com");
    Future<String> ubuntuTitleFuture = findTitleFor("https://ubuntu.com");

    String githubTitle = githubTitleFuture.get();
    log.debug("github title: {}", githubTitle);

    String ubuntuTitle = ubuntuTitleFuture.get();
    log.debug("ubuntu title: {}", ubuntuTitle);
  }

  private Future<String> findTitleFor(String url) {
    Callable<String> task = () -> new ScrapService(url).title();
    return executor.submit(task);
  }
}
