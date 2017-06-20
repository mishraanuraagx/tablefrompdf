package TableFromPdf;


import java.io.IOException;
import TableFromPdf.testClasses.TableFromPdfTest;

public class Application {
  public static void main(String[] args) {
    System.out.println("Hello");
    try {
      TableFromPdfTest.run();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
