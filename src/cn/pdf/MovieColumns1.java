
package cn.pdf;
 
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
 

 
public class MovieColumns1 {
 
    /** The resulting PDF file. */
    public static final String RESULT = "F:/movie_columns1"+System.currentTimeMillis()+".pdf";
 
    /** Definition of two columns */
    public static final float[][] COLUMNS = {
			        { 36, 36, 296, 806 } , 
			        { 299, 36, 559, 806 }
			    };
 
    /**
     * Creates a PDF with information about the movies
     * @param    filename the name of the PDF file that will be created.
     * @throws    DocumentException 
     * @throws    IOException 
     * @throws    SQLException
     */
    public void createPdf(String filename)
        throws IOException, DocumentException, SQLException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        ColumnText ct = new ColumnText(writer.getDirectContent());
        for (int i=0;i<200;i++) {
            ct.addText(new Phrase("asdfadsf"+i));
            ct.addText(Chunk.NEWLINE);
        }
        ct.setAlignment(Element.ALIGN_JUSTIFIED);
        ct.setExtraParagraphSpace(6);
        ct.setLeading(0, 1.2f);
        ct.setFollowingIndent(27);
        int linesWritten = 0;
        int column = 0;
        int status = ColumnText.START_COLUMN;
        while (ColumnText.hasMoreText(status)) {
            ct.setSimpleColumn(
                    COLUMNS[column][0], COLUMNS[column][1],
                    COLUMNS[column][2], COLUMNS[column][3]);
            ct.setYLine(COLUMNS[column][3]);
            status = ct.go();
            linesWritten += ct.getLinesWritten();
            column = Math.abs(column - 1);
            if (column == 0)
                document.newPage();
        }
 
        ct.addText(new Phrase("<p>Lines</p>written: " + linesWritten));
        ct.go();
        // step 5
        document.close();
    }

   
 
    /**
     * Main method.
     *
     * @param    args    no arguments needed
     * @throws DocumentException 
     * @throws IOException 
     * @throws SQLException
     */
    public static void main(String[] args)
        throws IOException, DocumentException, SQLException {
        new MovieColumns1().createPdf(RESULT);
    }
}