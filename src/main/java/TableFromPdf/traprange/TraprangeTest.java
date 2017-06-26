package TableFromPdf.traprange;


import java.io.IOException;

public class TraprangeTest {
  public static void run() throws IOException {
    TestExtractor te = new TestExtractor();
    try {
      te.test();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
