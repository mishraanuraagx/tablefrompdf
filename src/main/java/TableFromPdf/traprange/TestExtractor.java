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

/**
 * @author THOQ LUONG Mar 22, 2015 5:36:40 PM
 */
public class TestExtractor {

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
    PDFTableExtractor extractor = (new PDFTableExtractor())
        .setSource(Application.url);

    int[] i = new int[100];
    /**
     * only for sample pdf manual extraction*/
    if(Application.url == "assests\\PdfWithTable.pdf"){
    for(int j=0;j<45;j++){
      if(j==26 || j==27 || j==25 )continue;
      i[j]=j;
    }}
    extractor.exceptLine(i);

    Table table = extractor.extract().get(0);
    try (Writer writer = new OutputStreamWriter(new FileOutputStream("assests\\ToHTML.html"),
        "UTF-8")) {

      writer.write("Page: " + (table.getPageIdx() + 1) + "\n");
      writer.write(table.toHtml());

    }
    try (Writer writer = new OutputStreamWriter(new FileOutputStream("assests\\toCSV.csv"),
        "UTF-8")) {

      writer.write("Page: " + (table.getPageIdx() + 1) + "\n");
      writer.write(table.toString());
    }


  }
}