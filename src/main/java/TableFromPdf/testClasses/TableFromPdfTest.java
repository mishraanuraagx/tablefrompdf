package TableFromPdf.testClasses;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class TableFromPdfTest {
  public static void run() throws IOException {
//    File file = new File("C:\\Users\\MAX\\Downloads\\COMMBILLPRINTING23.pdf");
    File file = new File("assests\\PdfWithTable.pdf");
    PDDocument document = PDDocument.load(file);
    PDFTextStripper pdfStripper = new PDFTextStripper();
    String text = pdfStripper.getText(document);
    System.out.println(text);
    document.close();

  }
}
