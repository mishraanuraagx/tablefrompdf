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

          linesCoordinates.add(singleLineCoordinates);
          System.out.println("---added this lineCoordinate");
        } else {
          singleRectCoordinates = new Double[4];
          singleRectCoordinates[0] = sc.nextDouble();
          singleRectCoordinates[1] = sc.nextDouble();
          singleRectCoordinates[2] = sc.nextDouble();
          singleRectCoordinates[3] = sc.nextDouble();
          rectanglesCoordinates.add(singleRectCoordinates);
          System.out.println("---added this rectCoordinate");
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
       * and x1 coordinate should be <=x or <=x-10 (for lines not fully touching edges)
       * and x2 >=x+w or >=x+w-10
       * going to use 15 instead of 10
       * where 10 is in points , can use smaller points*/
      int sizeRC = rectanglesCoordinates.size();

      if (slc[1] == slc[3]) {
        for (int j = 0; j < sizeRC; j++) {

          double[]
              src =
              Stream.of(rectanglesCoordinates.get(j)).mapToDouble(Double::doubleValue).toArray();
          if (slc[1] > src[1] && slc[1] < (
              src[1] + src[3])) {
            if (slc[0] <= (src[0] + 10) && slc[2] >= (
                src[0] + src[2] - 10)) {
              System.out.println(slc[0] + "," + slc[1] + "," + slc[2] + "," + slc[3]);
              System.out.println(src[0] + "," + src[1] + "," + src[2] + "," + src[3]);
              System.out.println("Found one Horizontal Divider");
            }
          }
        }
      } else if (slc[0] == slc[2]) {
        for (int j = 0; j < sizeRC; j++) {

          double[]
              src =
              Stream.of(rectanglesCoordinates.get(j)).mapToDouble(Double::doubleValue).toArray();
          if (slc[0] > src[0] && slc[0] < (
              src[0] + src[2])) {
            if (slc[1] <= (src[1] + 10) && slc[3] >= (
                src[1] + src[3] - 10)) {
              System.out.println(slc[0] + "," + slc[1] + "," + slc[2] + "," + slc[3]);
              System.out.println(src[0] + "," + src[1] + "," + src[2] + "," + src[3]);
              System.out.println("Found one Vertical Divider");
            }
          }

        }
      } else {
        System.out.println("Found a weird line");
        System.out.println(slc[0] + "," + slc[1] + "," + slc[2] + "," + slc[3]);
      }


    }
  }
}
