package TableFromPdf;


import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import TableFromPdf.testClasses.PDFDrawMethods;
import TableFromPdf.testClasses.PDFTextWithLocation;
import TableFromPdf.testClasses.TableFromPdfTest;

public class Application {

  //TODO max : write sorting methods for PDFDrawMethod class and PDFTextWithLocation
  //TODO max : Write Algo to extract table from grouping cells
  //TODO max : Try to figure out more cells using left over lines coordinates
  //TODO max : See whether removing rectangles is good idea, dispose of bad cells/rectangles

  public static String url = "assests\\PdfWithTable.pdf";
  public static PDFTextStripper stripper;

  public static void main(String[] args) {
    java.util.logging.Logger.getLogger("org.apache.pdfbox").setLevel(java.util.logging.Level.OFF);

    try {
      stripper = new PDFTextStripper() {
        @Override
        protected void startPage(PDPage page) throws IOException {
          startOfLine = true;
          super.startPage(page);
        }

        @Override
        protected void writeLineSeparator() throws IOException {
          startOfLine = true;
          super.writeLineSeparator();
        }

        @Override
        protected void writeString(String text, List<TextPosition> textPositions)
            throws IOException {
          if (startOfLine) {
            TextPosition firstPosition = textPositions.get(0);

            writeString(
                String.format("%s,%s,", firstPosition.getXDirAdj(), firstPosition.getYDirAdj()));
            startOfLine = false;
          }
          super.writeString(text, textPositions);
        }

        boolean startOfLine = true;
      };
    }catch (IOException ioe){
      System.out.println(ioe.toString());
    }

    System.out.println("Hello");
    try {
//      TableFromPdfTest.run();
      PDFDrawMethods pdfDM = new PDFDrawMethods();
      pdfDM.getAllRectAndLines();
//      pdfDM.printAllLinesCoordinates();
//      pdfDM.printAllRectanglesCoordinates();
      pdfDM.getOtherRect();
      pdfDM.printAllLinesCoordinates();

      PDFTextWithLocation pdfTWL = new PDFTextWithLocation();
//      pdfTWL.printAllTextWithLocation();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
