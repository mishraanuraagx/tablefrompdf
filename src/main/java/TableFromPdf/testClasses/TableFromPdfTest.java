package TableFromPdf.testClasses;



import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class TableFromPdfTest {
  public static void run() throws IOException {
    File file = new File("assests\\PdfWithTable.pdf");
    PDDocument document = PDDocument.load(file);
    PDFTextStripper pdfStripper = new PDFTextStripper();
    String text = pdfStripper.getText(document);
    String lines[] = text.split("\\r?\\n"); // give you all the lines separated by new line
    System.out.println(Arrays.toString(lines));
    System.out.println("------------------------------------------------");
    String cols[] = lines[0].split("\\s+");// gives array separated by whitespaces
    System.out.println(Arrays.toString(cols));
    System.out.println("------------------------------------------------");
    System.out.println(text);
    System.out.println("------------------------------------------------");
    double w = document.getPage(0).getMediaBox().getWidth();
    double h = document.getPage(0).getMediaBox().getHeight();
    System.out.println(w +" , " + h);

    Rectangle2D region = new Rectangle2D.Double(5, 370, w, 50);
    String regionName = "region";
    PDFTextStripperByArea stripper;

    stripper = new PDFTextStripperByArea();
    stripper.addRegion(regionName, region);
    stripper.extractRegions(document.getPage(0));
    text = stripper.getTextForRegion(regionName);
    System.out.println(text);


    document.close();



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
