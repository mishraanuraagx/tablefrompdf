package TableFromPdf.testClasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MAX on 25-06-2017.
 */
public class PDFTable {
  Map<Integer, List<Integer>> cellIndexesBelow = new HashMap<>();
  List<PDFCell> cells = new ArrayList<>();
  int totalTables = 0;
  int temp1 = 0;
  int temp2 = 0;
  int temp3 = 0;

  List<Integer[]> allRectIndexes;
  List<Integer> oneRectIndexes;

  public void buildTable() throws IOException {
    Collections.sort(cells, new PDFCell());
    System.out.println("----cells -----");
    for (int i = 0; i < cells.size(); i++) {
      System.out.println(cells.get(i).toString());
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
    System.out.println(i.size());
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
}
