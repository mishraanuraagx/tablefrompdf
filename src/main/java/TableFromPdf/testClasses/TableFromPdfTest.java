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
    PDFTextStripper pdfStripper = new PDFTextStripper();
    String text = pdfStripper.getText(document);



//    String lines[] = text.split("\\r?\\n"); // give you all the lines separated by new line
//    System.out.println(Arrays.toString(lines));
//    System.out.println("------------------------------------------------");
//    String cols[] = lines[0].split("\\s+");// gives array separated by whitespaces
//    System.out.println(Arrays.toString(cols));
//    System.out.println("------------------------------------------------");


    // output all text


    System.out.println(text);
    System.out.println("------------------------------------------------");

    //output height and width of the document, can also be read from mediatype in pdf
    double w = document.getPage(0).getMediaBox().getWidth();
    double h = document.getPage(0).getMediaBox().getHeight();
    System.out.println(w +" , " + h);

    Rectangle2D region = new Rectangle2D.Double(8.4,470,/* 370,*/ 575.52, 100);
//    308.04 425.80 m 308.04 399.32 l S
//    308.04 398.16 m 308.04 298.64 l S
//    140.40 390.96 TD
//    for 1X2.000
//    Rectangle2D region = new Rectangle2D.Double(8.4,399.32,/* 370,*/ 575.52,50 ); // Line between data and column header
    String regionName = "region";
    PDFTextStripperByArea stripper;

    stripper = new PDFTextStripperByArea();
//    List<String> regionList = stripper.getRegions();
//    for (String str : regionList){
//      System.out.println(str);
//    }
//    System.out.println("------------------------------------------------");


    // Extract text within a location
    stripper.addRegion(regionName, region);
    stripper.extractRegions(document.getPage(0));
    text = stripper.getTextForRegion(regionName);
    System.out.println(text);



    System.out.println(System.getProperty("line.separator"));



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

    text = stripper2.getText(document);
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
        int a=0;

//        for(String str : textList){
//          if(xCoordinates.get(a)>pc.getX() && xCoordinates.get(a)<=(pc.getX()+pc.getW())
//              && yCoordinates.get(a)>pc.getY() && yCoordinates.get(a)<=(pc.getY()+pc.getH())){
//
//            System.out.println(xCoordinates.get(a)+","+yCoordinates.get(a)+","+textList.get(a));
//          }
//          a++;
//        }
//        pdfCells.add(pc);

        System.out.println();
      }
    }




  }
}
