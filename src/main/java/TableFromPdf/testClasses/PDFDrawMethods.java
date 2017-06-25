package TableFromPdf.testClasses;


import com.sun.deploy.util.ArrayUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import TableFromPdf.Application;

//TODO max : remove used lines
//TODO max: check remaining left over lines and reason why they don't fit the condition for separating the rectangles
public class PDFDrawMethods {
  public List<Double[]> rectanglesCoordinates = new ArrayList<Double[]>();
  public Double[] singleRectCoordinates;
  public List<Double[]> linesCoordinates = new ArrayList<Double[]>();
  public Double[] singleLineCoordinates;

  public void getAllRectAndLines() throws IOException {
    // Output all drawing instruction for Rectangles
    File file = new File(Application.url);
    List<PDFCell> pdfCells = new ArrayList<>();
    BufferedReader br = new BufferedReader(new FileReader(file));

    Scanner sc;
    String currentLine;
    int j = 0;
    while ((currentLine = br.readLine()) != null) {
      if (currentLine.contains(" re ") || currentLine.contains(" l ")) {

//        System.out.print(j++ + " : " + currentLine);
        sc = new Scanner(currentLine);
        if (currentLine.contains(" l ")) {
          singleLineCoordinates = new Double[4];
          singleLineCoordinates[0] = sc.nextDouble();
          singleLineCoordinates[1] = sc.nextDouble();
          // TODO max: faulty way to skip 'm' char on draw method for a line
          sc.next();
          singleLineCoordinates[2] = sc.nextDouble();
          singleLineCoordinates[3] = sc.nextDouble();
          //TODO max: fix, make smaller coordinate values store in first 2
          if (singleLineCoordinates[0].doubleValue() == singleLineCoordinates[2].doubleValue()
              && singleLineCoordinates[1].doubleValue() > singleLineCoordinates[3].doubleValue()) {
            double temp1 = singleLineCoordinates[1].doubleValue();
            double temp2 = singleLineCoordinates[3].doubleValue();
            singleLineCoordinates[1] = temp2;
            singleLineCoordinates[3] = temp1;
          } else if (
              singleLineCoordinates[1].doubleValue() == singleLineCoordinates[3].doubleValue() &&
                  singleLineCoordinates[0].doubleValue() > singleLineCoordinates[2].doubleValue()) {
            double temp1 = singleLineCoordinates[0].doubleValue();
            double temp2 = singleLineCoordinates[2].doubleValue();
            singleLineCoordinates[0] = temp2;
            singleLineCoordinates[2] = temp1;
          }
          linesCoordinates.add(singleLineCoordinates);
//          System.out.println("---added this lineCoordinate");
        } else {
          singleRectCoordinates = new Double[4];
          singleRectCoordinates[0] = sc.nextDouble();
          singleRectCoordinates[1] = sc.nextDouble();
          singleRectCoordinates[2] = sc.nextDouble();
          singleRectCoordinates[3] = sc.nextDouble();
          rectanglesCoordinates.add(singleRectCoordinates);
//          System.out.println("---added this rectCoordinate");
        }
//        PDFCell pc = new PDFCell(sc.nextDouble(),sc.nextDouble(),sc.nextDouble(),sc.nextDouble());
//        PDFTextByRegion pdfTextByRegion = new PDFTextByRegion();
//        pdfTextByRegion.textByCell();
//        pdfCells.add(pc);

      }
    }
  }

  public void printAllRectanglesCoordinates() {
    int size = rectanglesCoordinates.size();
    System.out.println(
        "--------------------Printing All Rectangles Coordinates----------------------------");
    for (int i = 0; i < size; i++) {
      Double[] d = rectanglesCoordinates.get(i);
      System.out.println(d[0] + "," + d[1] + "," + d[2] + "," + d[3]);
    }
  }

  public void printAllLinesCoordinates() {
    int size = linesCoordinates.size();
    System.out
        .println("--------------------Printing All Lines Coordinates----------------------------");
    for (int i = 0; i < size; i++) {
      Double[] d = linesCoordinates.get(i);
      System.out.println(d[0] + "," + d[1] + "," + d[2] + "," + d[3]);
    }
  }


  //TODO max: many cases are not included like rect created by only lines

  /**
   * Figure out all rect created by lines intersecting with rect
   */


  public void getOtherRect() {
    int gap = 15;
    int minDisBetweenParallelLines = 15;
    System.out.println(
        "--------------------Figuring out All Rectangles created by lines intersecting with rect----------------------------");

    /**
     *Figure out whether line is horizontal or vertical*/
    int sizeLC = linesCoordinates.size();
    for (int i = 0; i < sizeLC; i++) {
      double[] slc = Stream.of(linesCoordinates.get(i)).mapToDouble(Double::doubleValue).toArray();
      /**
       * if y coordinate is same then horizontal
       * check whether its between y and y+h of rect coordinates
       * add 15 in y and minus from y+h to remove small cell casses
       * and x1 coordinate should be <=x or <=x-15 (for lines not fully touching edges)
       * and x2 >=x+w or >=x+w-15
       * where 15 is in points , can use smaller points*/
      int sizeRC = rectanglesCoordinates.size();
      boolean lineUsed = false;

      if (slc[1] == slc[3]) {
        for (int j = 0; j < sizeRC; j++) {

          double[]
              src =
              Stream.of(rectanglesCoordinates.get(j)).mapToDouble(Double::doubleValue).toArray();
          if (slc[1] > src[1] + minDisBetweenParallelLines && slc[1] < (
              src[1] + src[3] - minDisBetweenParallelLines)) {
            if (slc[0] <= (src[0] + gap) && slc[2] >= (
                src[0] + src[2] - gap)) {
//              System.out.println(slc[0] + "," + slc[1] + "," + slc[2] + "," + slc[3]);
//              System.out.println(src[0] + "," + src[1] + "," + src[2] + "," + src[3]);
//              System.out.println("Found one Horizontal Divider");
              breakRectHorizontaly(j,slc[1]);
              lineUsed = true;
            }
          }
        }
      } else if (slc[0] == slc[2]) {
        for (int j = 0; j < sizeRC; j++) {

          double[]
              src =
              Stream.of(rectanglesCoordinates.get(j)).mapToDouble(Double::doubleValue).toArray();
          if (slc[0] > src[0] + minDisBetweenParallelLines && slc[0] < (
              src[0] + src[2] - minDisBetweenParallelLines)) {
            if (slc[1] <= (src[1] + gap) && slc[3] >= (
                src[1] + src[3] - gap)) {
//              System.out.println(slc[0] + "," + slc[1] + "," + slc[2] + "," + slc[3]);
//              System.out.println(src[0] + "," + src[1] + "," + src[2] + "," + src[3]);
//              System.out.println("Found one Vertical Divider");
              breakRectVertically(j,slc[0]);
              lineUsed = true;
            }
          }

        }
      } else {
        System.out.println("Found a weird line");
        System.out.println(slc[0] + "," + slc[1] + "," + slc[2] + "," + slc[3]);
      }
      if(lineUsed){
        linesCoordinates.remove(i);
        sizeLC--;
        i--;

      }

    }
  }

  public void breakRectVertically(int index, double x) {
    /**
     * from coordinates x1, y1, w, h
     * make one as x1, y1, |x-x1|, h
     * and other as x, y1, w-|x-x1|, h*/
    Double[] d1 = rectanglesCoordinates.get(index);
//    System.out.println(d1[0]+","+d1[1]+","+d1[2]+","+d1[3]);
    Double[] d2 = new Double[]{x,d1[1],d1[2]-Math.abs(x-d1[0]),d1[3]};
    d1[2]=Math.abs(x-d1[0]);
//    System.out.println(d1[0]+","+d1[1]+","+d1[2]+","+d1[3]);
//    System.out.println(d2[0]+","+d2[1]+","+d2[2]+","+d2[3]);
    System.out.println("----------------just found 1 more Vertical Separator-----------");
    rectanglesCoordinates.set(index,d1);
    rectanglesCoordinates.add(d2);


  }

  public void breakRectHorizontaly(int index, double y) {
    /**
     * from coordinates x1, y1, w, h
     * make one as x1 y1, w, |y-y1|
     * and other as x1, y, w, h-|y-y1|*/
    Double[] d1 = rectanglesCoordinates.get(index);
//    System.out.println(d1[0]+","+d1[1]+","+d1[2]+","+d1[3]);
    Double[] d2 = new Double[]{d1[0],y,d1[2],d1[3]-Math.abs(y-d1[1])};
    d1[3]=Math.abs(y-d1[1]);
//    System.out.println(d1[0]+","+d1[1]+","+d1[2]+","+d1[3]);
//    System.out.println(d2[0]+","+d2[1]+","+d2[2]+","+d2[3]);
    System.out.println("----------------just found 2 more Horizontal Separator-----------");
    rectanglesCoordinates.set(index,d1);
    rectanglesCoordinates.add(d2);

  }
}
