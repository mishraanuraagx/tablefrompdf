package TableFromPdf.testClasses;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import TableFromPdf.Application;

/**
 * Created by MAX on 25-06-2017.
 */
public class PDFTextByRegion{

  //TODO max: Prints without coordinates
  public static void TextByRectangle() throws IOException{
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

    Rectangle2D region = new Rectangle2D.Double(8.4,370,/* 370,*/ 575.52, 100);
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
  }
  
  public void textByCell(PDFTextWithLocation pdftwl,PDFCell pdfCell){
    int a=0;

        for(String str : pdftwl.getTextList()){
          if(pdftwl.getxCoordinates().get(a)>pdfCell.getX() && pdftwl.getxCoordinates().get(a)<=(pdfCell.getX()+pdfCell.getW())
              && pdftwl.getyCoordinates().get(a)>pdfCell.getY() && pdftwl.getyCoordinates().get(a)<=(pdfCell.getY()+pdfCell.getH())){

            System.out.println(pdftwl.getxCoordinates().get(a)+","+pdftwl.getyCoordinates().get(a)+","+pdftwl.getTextList().get(a));
          }
          a++;
        }
  }
}
