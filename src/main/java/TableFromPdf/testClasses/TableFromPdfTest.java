package TableFromPdf.testClasses;




import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import TableFromPdf.Application;


public class TableFromPdfTest {

  public static List<Double> xCoordinates = new ArrayList<>();
  public static List<Double> yCoordinates = new ArrayList<>();
  public static List<String> textList = new ArrayList<>();


  public static void run() throws IOException {
    File file = new File(Application.url);
    PDDocument document = PDDocument.load(file);
    String text;



    //output height and width of the document, can also be read from mediatype in pdf
    double w = document.getPage(0).getMediaBox().getWidth();
    double h = document.getPage(0).getMediaBox().getHeight();
    System.out.println(w +" , " + h);

//    System.out.println(System.getProperty("line.separator"));



    PDFTextStripper stripper2 = new PDFTextStripper()
    {
      @Override
      protected void startPage(PDPage page) throws IOException
      {
        startOfLine = true;
        super.startPage(page);
      }

      @Override
      protected void writeLineSeparator() throws IOException
      {
        startOfLine = true;
        super.writeLineSeparator();
      }

      @Override
      protected void writeString(String text, List<TextPosition> textPositions) throws IOException
      {
        if (startOfLine)
        {
          TextPosition firstPosition = textPositions.get(0);

          writeString(String.format("%s,%s,", firstPosition.getXDirAdj(), firstPosition.getYDirAdj()));
          startOfLine = false;
        }
        super.writeString(text, textPositions);
      }
      boolean startOfLine = true;
    };

//    text = stripper2.getText(document);
    text = Application.stripper.getText(document);
    System.out.println("------------------------------------------------");
    int i =0;
//    System.out.println(text);
    for(String str : text.split("\n")){
      if(!str.isEmpty()){
        String[] str2 = str.split(",");
        xCoordinates.add(Double.parseDouble(str2[0]));
        yCoordinates.add(Double.parseDouble(str2[1]));
        textList.add(str2[2]);
        i++;
      }
    }

    for(int n=0;n<i;n++){
      System.out.println(xCoordinates.get(n)+","+yCoordinates.get(n)+","+textList.get(n));
    }
    document.close();

    System.out.println("------------------------------------------------");





    // Output all drawing instruction for Rectangles
    List<PDFCell> pdfCells = new ArrayList<>();
    BufferedReader br = new BufferedReader(new FileReader(file));

    Scanner sc;
    String currentLine;
    int j=0;
    while((currentLine = br.readLine())!= null){
      if(currentLine.contains(" re ") || currentLine.contains(" l ")) {
        System.out.println(j++ + " : " + currentLine);
//        sc = new Scanner(currentLine);
//        while (sc.hasNextDouble()) {
//          System.out.print(sc.nextDouble() + " ");
//        }
//        PDFCell pc = new PDFCell(sc.nextDouble(),sc.nextDouble(),sc.nextDouble(),sc.nextDouble());
        PDFTextByRegion pdfTextByRegion = new PDFTextByRegion();
//        pdfTextByRegion.textByCell();
//        pdfCells.add(pc);

        System.out.println();
      }
    }




  }
}
