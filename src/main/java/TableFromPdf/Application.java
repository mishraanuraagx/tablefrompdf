package TableFromPdf;


import java.io.IOException;
import TableFromPdf.testClasses.TableFromPdfTest;

public class Application {
  public static void main(String[] args) {
    java.util.logging.Logger.getLogger("org.apache.pdfbox").setLevel(java.util.logging.Level.OFF);
    System.out.println("Hello");
    try {
      TableFromPdfTest.run();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
