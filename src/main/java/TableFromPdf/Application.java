package TableFromPdf;


import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import TableFromPdf.testClasses.PDFCell;
import TableFromPdf.testClasses.PDFDrawMethods;
import TableFromPdf.traprange.TraprangeTest;

public class Application {
  //TODO max : Output text with location within a rectangle
  //TODO max : write sorting methods for PDFDrawMethod class and PDFTextWithLocation
  //TODO max : Write Algorithm to extract table from grouping cells
  //TODO max : See whether removing rectangles is good idea, dispose of bad cells/rectangles
  //TODO max: needs more abstraction of PDFDrawMethods  class

  /**
   * url is used to point to file
   */
  public static String url = "assests\\PdfWithTable.pdf";
  public static PDFTextStripper stripper;

  public static void main(String[] args) throws IOException {
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
    } catch (IOException ioe) {
      System.out.println(ioe.toString());
    }

    TraprangeTest.run();

//    try {
////      TableFromPdfTest.run();
//      PDFDrawMethods pdfDM = new PDFDrawMethods();
//      pdfDM.getAllRectAndLines();
//      pdfDM.getOtherRect();
//      pdfDM.formMoreCellsUsingUnUsedLines();
//      List<PDFCell> pc = pdfDM.createCellsFromRect();
//
////      PDFTextWithLocation pdfTWL = new PDFTextWithLocation();
////      pdfTWL.printAllTextWithLocation();
////      PDFTextByRegion pdftbr = new PDFTextByRegion();
////      pdftbr.textByCell(pdfTWL,);
//
//      int i = 0;
//      for (PDFCell pdfCell : pc) {
//        pdfCell.textExtractionWithPosition();
//        System.out.println(
//            "---------------------------Text Within Cell : " + i++ + "-------------------------------------");
//      }
//
////      PDFTableGenerator pdft = new PDFTableGenerator();
////      pdft.setCells(pc);
////      pdft.buildTable();
//
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
  }
}
