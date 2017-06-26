package TableFromPdf.testClasses;


import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import TableFromPdf.Application;

public class PDFTextWithLocation {
  public List<Double> xCoordinates = new ArrayList<>();
  public List<Double> yCoordinates = new ArrayList<>();
  public List<String> textList = new ArrayList<>();
  public String text;

  public PDFTextWithLocation(List<Double> xCoordinates, List<Double> yCoordinates,
                             List<String> textList) {
    this.xCoordinates = xCoordinates;
    this.yCoordinates = yCoordinates;
    this.textList = textList;
  }

  public PDFTextWithLocation() throws IOException {
    this.getAllTextWithCoordinates();
  }

  public int uniqueLines(){
    Set<Double> uniqueLines = new HashSet<Double>(yCoordinates);
    return uniqueLines.size();
  }

  public void getAllTextWithCoordinates() throws IOException {

    System.out.println(
        "--------------------Extracting Text With Coordinates----------------------------");
    PDDocument document = PDDocument.load(new File(Application.url));
    text = Application.stripper.getText(document);

    for (String str : text.split("\n")) {
      if (!str.isEmpty()) {
        String[] str2 = str.split(",");
        xCoordinates.add(Double.parseDouble(str2[0]));
        yCoordinates.add(Double.parseDouble(str2[1]));
        textList.add(str2[2]);
//        double d1 = Double.parseDouble(str2[0]);
//        double d2 = Double.parseDouble(str2[1]);
//        String text = str2[2];
//        if (xCoordinates.size() != 0) {
//          int size = xCoordinates.size();
//          for (int i = 0; i < size; i++) {
//            if(d1 < xCoordinates.get(i).doubleValue()){
//              xCoordinates.add(i,d1);
//              yCoordinates.add(i,d2);
//              textList.add(i,text);
//            } else if(d1 == xCoordinates.get(i).doubleValue()){
//              if(d2 < yCoordinates.get(i).doubleValue()){
//                xCoordinates.add(i,d1);
//                yCoordinates.add(i,d2);
//                textList.add(i,text);
//              }
//            }
//          }
//        }
      }
    }

  }



  public void printAllTextWithLocation() {

    System.out
        .println("--------------------Printing Text With Coordinates----------------------------");
    System.out.println(text);
  }


  public List<Double> getxCoordinates() {
    return xCoordinates;
  }

  public void setxCoordinates(List<Double> xCoordinates) {
    this.xCoordinates = xCoordinates;
  }

  public List<Double> getyCoordinates() {
    return yCoordinates;
  }

  public void setyCoordinates(List<Double> yCoordinates) {
    this.yCoordinates = yCoordinates;
  }

  public List<String> getTextList() {
    return textList;
  }

  public void setTextList(List<String> textList) {
    this.textList = textList;
  }

}
