package TableFromPdf.testClasses;




import com.itextpdf.text.pdf.PdfReader;
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
    String out = "";
    String text = pdfStripper.getText(document);
    System.out.println(text);
    document.close();

    PdfReader reader = new PdfReader("assests\\PdfWithTable.pdf");

//    ObjectExtractor oe = new ObjectExtractor(document);
//    ExtractionAlgorithm extractor = new BasicExtractionAlgorithm();
//
//    PageIterator it = oe.extract();
//
//    while (it.hasNext()) {
//      Page page = it.next();
//
//      for (Table table : extractor.extract(page)) {
//        for (List<RectangularTextContainer> row : table.getRows()) {
//          for (RectangularTextContainer cell : row) {
//
//          }
//        }
//      }
//    }

  }
}
