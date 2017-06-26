package TableFromPdf.traprange;

/**
 * Copyright (C) 2015, GIAYBAC
 *
 * Released under the MIT license
 */

import com.giaybac.traprange.entity.Table;


import com.giaybac.traprange.PDFTableExtractor;
import com.giaybac.traprange.entity.Table;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import TableFromPdf.Application;
import TableFromPdf.pdfMethods.PDFTableGeneratorHelper;

/**
 * @author THOQ LUONG Mar 22, 2015 5:36:40 PM
 */
public class TestExtractor {
  public static int totalfiles = 0;

  //--------------------------------------------------------------------------
  //  Members
  //--------------------------------------------------------------------------
  //  Initialization and releasation
  //--------------------------------------------------------------------------
  //  Getter N Setter
  //--------------------------------------------------------------------------
  //  Method binding
  @Test
  public void test() throws IOException {
    PropertyConfigurator
        .configure(TestExtractor.class.getResource("/com/giaybac/traprange/log4j.properties"));

//    for (int idx = 0; idx < 5; idx++) {
//    PDFTableExtractor extractor = (new PDFTableExtractor())
//        .setSource(Application.url);

//    int[] i = new int[150];
    /**
     * only for sample pdf manual extraction*/
//    if(Application.url == "assests\\PdfWithTable.pdf"){
//    for(int j=0;j<Application.uniqueDefLines;j++){
//      if(j==25 || j==26 || j==27 || j==28 )continue;
//      i[j]=j;
//    }}
    int[] i1 = new int[Application.uniqueDefLines+10];
    int[] i2 = new int[Application.uniqueDefLines+10];
    for(int j=0;j<Application.uniqueDefLines;j++){
      i1[j]=j;
      i2[j]=j;
    }
    int name =0;
    for(Integer[] k : PDFTableGeneratorHelper.tableStartFinishLines){
      for(int l=k[0];l<(k[0]+k[1]);l++){
        i2[l]=0;
      }
      PDFTableExtractor extractor = (new PDFTableExtractor())
          .setSource(Application.url);
      extractor.exceptLine(i2);

      Table table = extractor.extract().get(0);
      try (Writer writer = new OutputStreamWriter(new FileOutputStream("assests\\ToHTML"+name+".html"),
          "UTF-8")) {

        writer.write("Page: " + (table.getPageIdx() + 1) + "\n");
        writer.write(table.toHtml());

      }
      try (Writer writer = new OutputStreamWriter(new FileOutputStream("assests\\toCSV"+name+".csv"),
          "UTF-8")) {

        writer.write("Page: " + (table.getPageIdx() + 1) + "\n");
        writer.write(table.toString());
        totalfiles++;
      }
      name++;
      System.arraycopy( i1, 0, i2, 0, i1.length );
      totalfiles++;
    }


  }
}