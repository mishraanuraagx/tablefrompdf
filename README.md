## TableFromPDF
This is an API (in-progress) to Auto-detect and Extract tables from pdf file. Technology used : Apache pdfBox, JAVA.
Working with Traprange to improve result. As of now manual input is required to get desired result.

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

6. TestExtractor
Use of traprange api to get desired result by manual input.

## Issue
1. Need manual input on table start and end lines, need to find a way to automate it

## Other online Solution
1. Tabula : http://tabula.technology/
2. Traprange : https://github.com/thoqbk/traprange
3. iText : http://itextpdf.com/

Tabula and Traprange both need manual input to get desired tables.
iText is more machine level then apache pdfBox.
