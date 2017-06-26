package TableFromPdf.pdfMethods;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PDFCell implements Comparator<PDFCell>{
  public double x;
  public double y;
  public double w;
  public double h;
  public String[][] textListWithPosition;
  public String text;


  PDFCell(double x,double y,double w,double h){
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
  PDFCell(){

  }

  public void textExtractionWithPosition() throws IOException{
    File file = new File("assests\\PdfWithTable.pdf");
    PDDocument document = PDDocument.load(file);
    double pw = document.getPage(0).getMediaBox().getWidth();
    double ph = document.getPage(0).getMediaBox().getHeight();

    PDFTextStripperByArea stripper = new PDFTextStripperByArea()
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

          writeString(String.format("[%s , %s]", firstPosition.getXDirAdj(), firstPosition.getYDirAdj()));
          startOfLine = false;
        }
        super.writeString(text, textPositions);
      }
      boolean startOfLine = true;
    };

    Rectangle2D region = new Rectangle2D.Double(x,y, w, h);
    String regionName = "region";

    stripper.addRegion(regionName, region);
    stripper.extractRegions(document.getPage(0));
    text = stripper.getTextForRegion(regionName);
//    System.out.println(this.getX()+","+this.getY()+","+this.getW()+","+this.getH());
    System.out.println(text);

  }



  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public double getW() {
    return w;
  }

  public void setW(double w) {
    this.w = w;
  }

  public double getH() {
    return h;
  }

  public void setH(double h) {
    this.h = h;
  }

  public String[][] getTextListWithPosition() {
    return textListWithPosition;
  }

  public void setTextListWithPosition(String[][] textListWithPosition) {
    this.textListWithPosition = textListWithPosition;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public int compare(PDFCell o1, PDFCell o2) {
    if(o1.getY() == o2.getY()){
      return (int) (o1.getX()-o2.getX());
    }
    return (int) (o1.getY()-o2.getY());
  }
  @Override
  public String toString(){
    return this.getX()+","+this.getY()+","+this.getW()+","+this.getH();
  }
}
