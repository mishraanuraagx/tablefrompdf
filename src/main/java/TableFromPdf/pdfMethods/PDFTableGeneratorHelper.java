package TableFromPdf.pdfMethods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jws.Oneway;
import TableFromPdf.Application;


public class PDFTableGeneratorHelper {
  Map<Integer, List<Integer>> cellIndexesBelow = new HashMap<>();
  List<PDFCell> cells = new ArrayList<>();
  int totalTables = 0;
  int temp1 = 0;
  int temp2 = 0;
  int temp3 = 0;

  List<Integer[]> allRectIndexes;
  List<Integer> oneRectIndexes;
  List<Integer> noOfLinesPerGap = new ArrayList<>();
  public static List<Integer[]> tableStartFinishLines = new ArrayList<>();

  public void tableStartEndFinder(PDFTextWithLocation pdftwl) {
    double h = pdftwl.getDocumentHeight();
    int lines = Application.uniqueDefLines;
    double parts = h / lines;
    for (int i = 1; i < lines; i++) {
      int n = 0;
      for (int j = 0; j < cells.size(); j++) {
        if ((cells.get(j).getY() >= parts * (i - 1) && cells.get(j).getY() <= parts * i)
            || (cells.get(j).getY() <= parts * (i - 1) && cells.get(j).getY()+cells.get(j).getH() >= parts * i)) {
          n++;
        }
      }
      System.out.println(i-1 + " : " + n);
      noOfLinesPerGap.add(i-1,n);
    }

    boolean onGoing = false;
    int start=0;
    int count=0;
    int value=0;
    Integer[] temp;
    for(int i=0;i<noOfLinesPerGap.size();i++) {
      if(!onGoing){
        if(noOfLinesPerGap.get(i)<2)continue;
        value=noOfLinesPerGap.get(i);
        count=1;
        start=i;
        onGoing=true;
      }
      else{
        if(value == noOfLinesPerGap.get(i).intValue()){
          count++;
        } else if(count>=2){
          temp = new Integer[]{start,count};
          tableStartFinishLines.add(temp);
          count=0;start=0;value=-1;onGoing=false;
          if(noOfLinesPerGap.get(i)>=2){
            count=1;
            start=i;
            value=noOfLinesPerGap.get(i).intValue();
            onGoing=true;
          }
        }
        else {
          count=0;start=0;onGoing=false;
          if(noOfLinesPerGap.get(i)>=2){
            count=1;
            start=i;
            value=noOfLinesPerGap.get(i).intValue();
            onGoing=true;
          }
        }
      }

    }
    for(Integer[] i : tableStartFinishLines){
    System.out.println(i[0]+" : " + i[1]);}

  }

  public int uniqueRect() {
    int j;

    List<Integer> indexes;
    for (int k = 0; k < cells.size(); k++) {
      indexes = new ArrayList<>();
      PDFCell cell1 = cells.get(k);
      for (int i = k; i < cells.size(); i++) {
        PDFCell cell2 = cells.get(i);
        if (Math.abs(cell1.getX() - cell2.getX()) < 2 && Math.abs(cell1.getY() - cell2.getY()) < 2
            && Math.abs(cell1.getH() * cell1.getW() - cell2.getH() * cell2.getW()) < 1) {
          indexes.add(k);
        }
      }
      for (int i = 0; i < indexes.size(); i++) {
        cells.remove(indexes.get(i).intValue());
      }
    }
    j = cells.size();
    return j;
  }


  public void buildTable() throws IOException {
    Collections.sort(cells, new PDFCell());
//    System.out.println("----cells -----");
    for (int i = 0; i < cells.size(); i++) {
//      System.out.println(cells.get(i).toString());
    }
    for (int i = 0; i < cells.size(); i++) {
      cellsBelow(i);
    }
    for (int i = 0; i < cells.size(); i++) {
      if (cellIndexesBelow.get(i).size() == 0) {
        continue;
      }
      boolean possible = buildTrue(i);
      /**
       * need to find a better way to solve this*/
//      System.out.println(cells.get(i).toString() + " it can do with " + cells.get(temp3) + " via cell " +
//      cellIndexesBelow.get(i).get(temp1).toString() + "   and   " + cellIndexesBelow.get(temp3).get(temp2).toString());
      if (possible) {
        System.out.println("---------------");
        cells.get(i).textExtractionWithPosition();
        System.out.print("  , ");
        cells.get(temp3).textExtractionWithPosition();
        System.out.println();
        cells.get(temp1).textExtractionWithPosition();
        System.out.print("  , ");
        cells.get(temp2).textExtractionWithPosition();
        System.out.println();
        System.out.println("---------------");
      }
    }

  }

  public boolean buildTrue(int index) {
    boolean possible = false;
    for (int i = 0; i < cells.size(); i++) {
      if (index == i || cellIndexesBelow.get(i).size() == 0) {
        continue;
      }
      if (cellAdjacent(index, i)) {
        List<Integer> cellBelowListForIndex = cellIndexesBelow.get(index);
        List<Integer> cellBelowListForIndex2 = cellIndexesBelow.get(i);
        for (int j = 0; j < cellBelowListForIndex.size(); j++) {
          if (j == index || j == i) {
            continue;
          }
          for (int k = 0; k < cellBelowListForIndex2.size(); k++) {
            if (k == i || k == j || k == index) {
              continue;
            }
            possible = cellAdjacent(cellBelowListForIndex.get(j), cellBelowListForIndex2.get(k));
            if (possible) {
              temp1 = j;
              temp2 = k;
              temp3 = i;
              return possible;
            }
          }
          if (possible) {
            break;
          }
        }
      }
      if (possible) {
        break;
      }
    }

    return possible;
  }

  public List<Integer> cellsBelow(int index) {
    List<Integer> i = new ArrayList<>();
    PDFCell cell = cells.get(index);
    for (int j = 0; j < cells.size(); j++) {
      if (j == index) {
        continue;
      }
      PDFCell cell2 = cells.get(j);
      if (Math.abs(cell.getX() - cell2.getX()) < 10
          && Math.abs(cell.getY() + cell.getH() - cell2.getY()) < 10
          && Math.abs(cell.getW() - cell2.getW()) < 10) {
        i.add(j);
      }
    }
//    System.out.println(i.size());
    cellIndexesBelow.put(index, i);
    return i;
  }

  public boolean cellAdjacent(int index1, int index2) {
    boolean found = false;
    PDFCell cell = cells.get(index1);
    PDFCell cell2 = cells.get(index2);
    if (Math.abs(cell.getX() + cell.getW() - cell2.getX()) < 10
        && Math.abs(cell.getY() - cell2.getY()) < 10
        && Math.abs(cell.getH() - cell2.getH()) < 10) {
      found = true;
    }
    return found;
  }

  public List<PDFCell> getCells() {
    return cells;
  }

  public void setCells(List<PDFCell> cells) {
    this.cells = cells;
  }

  public int getTotalTables() {
    return totalTables;
  }

  public void setTotalTables(int totalTables) {
    this.totalTables = totalTables;
  }

  public List<Integer[]> getAllRectIndexes() {
    return allRectIndexes;
  }

  public void setAllRectIndexes(List<Integer[]> allRectIndexes) {
    this.allRectIndexes = allRectIndexes;
  }

  public List<Integer> getOneRectIndexes() {
    return oneRectIndexes;
  }

  public void setOneRectIndexes(List<Integer> oneRectIndexes) {
    this.oneRectIndexes = oneRectIndexes;
  }

  public void boundingLines() {

  }
}
