## TableFromPDF
This is an API (in-progress) to Auto-detect and Extract tables from pdf file. Technology used : Apache pdfBox, JAVA.
Working with Traprange to improve result.

## How to use
Results are in 'assests' folder. A sample pdf file is provided with name 'PdfWithTable'.
Output files are 'ToHTML.html' contains output in HTML table format and 'toCSV.csv' in csv format.
Change 'url' variable in main function in Application class for file located at different source.

## Classes
1. PDFDrawMethods
This class is used to get all drawing instruction inside the pdf file.
Only drawing instruction for rectangles and lines are taken into considerations.
Manual detection is used to produce all rectangles.

2. PDFCell
This class is used to store cells/rectangles as objects and provide sorting and text extraction methods

3. PDFTextWithLocation
Use Override methods to get all text out of the file with their location

4. PDFTextByRegion
Use Apache pdfBox default method to extract text for a given rectangle coordinates

5. PDFTableGenerator
Helps in production of Table from merging and combining possible cells into groups

## Issue
1. Need to use better cell detection algorithm, converting pdf without text to png and using some line detection algorithm might solve this problem
2. PDFTableGenerator is still in-progress, runtime of this class is too high, doesn't even work properly.

## Other online Solution
1. Tabula : http://tabula.technology/
2. Traprange : https://github.com/thoqbk/traprange
3. iText : http://itextpdf.com/

Tabula and Traprange both need manual input to get desired tables.
iText is more machine level then apache pdfBox.
