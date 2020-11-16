package cn.pdf;

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.MultiColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class PDF {
	/**
	 * @param args
	 * @throws DocumentException
	 * @throws IOException 
	 */
	public static void main(String[] args) throws DocumentException, IOException {
		Rectangle A4Page = PageSize.A4;
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font  FontChinese = new Font(bfChinese, 12, Font.NORMAL);
		
		Document document = new Document(A4Page);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("F:/Chap0101.pdf"));
		
		//writer.setPageEvent(new OnEndPageEventPDF(writer,document));
		
		document.open();
		
		//设置“拆行fasle”还是“换页 true”
		//table.setSplitLate(false);//设置是否因为行内的内容太多，直接把这行移到下一页显示还是只是把内容移到下一页
		
		
		/*PdfAction action = PdfAction.javaScript("app.alert('Welcome at my site');\r", writer);
		action.next(new PdfAction("http://www.java2s.com"));
		Paragraph p = new Paragraph(new Chunk("Click to go to a website").setAction(action));
		document.add(p);*/
		
		
        PdfContentByte cb = writer.getDirectContent();
        
        ColumnText ct = new ColumnText(cb);
        
        Phrase col1 = new Phrase(15, "", FontChinese);
	    //Phrase col2 = new Phrase(15, "", FontChinese);
        for (int i = 0; i < 200; i++) {
        	col1.add("aaa "+i+"\n");
        	ct.addText(col1);
        	ct.addText(Chunk.NEWLINE);
        }
        float[][] COLUMNS = {
	            { 36, 36, 296, 806 } , 
	            { 299, 36, 559, 806 }
	        };
        int linesWritten = 0;
        int column = 0;
        int status = ColumnText.START_COLUMN;
        while(ColumnText.hasMoreText(status)){
        	ct.setSimpleColumn(
                    COLUMNS[column][0], COLUMNS[column][1],
                    COLUMNS[column][2], COLUMNS[column][3]);
            ct.setYLine(COLUMNS[column][3]);
        	status = ct.go();
        	System.out.println("status:"+status);
        	linesWritten +=ct.getLinesWritten();
        	column = Math.abs(column-1);
        	if(column==0){
        		document.newPage();
        	}
        }
        ct.addText(new Phrase("lines written : " +linesWritten));
        ct.go();
        
        
     /*   
        ct.setSimpleColumn(col1, 60, 300, 100, 300 + 28 * 15, 15, Element.ALIGN_CENTER);
        int go = ct.go();
        System.out.println(go);
        System.out.println("-----");
     
       ct.setSimpleColumn(col1, 105, 300, 150, 300 + 28 * 15, 15, Element.ALIGN_RIGHT);
        int go2 = ct.go();
        System.out.println(go2);*/
    /*    System.out.println("NO_MORE_COLUMN:"+ColumnText.NO_MORE_COLUMN);
        System.out.println("NO_MORE_TEXT:"+ColumnText.NO_MORE_TEXT);*/
		/**
		PdfPTable table = new PdfPTable(5);//5列
		table.setSplitLate(false);//-----------------
		table.setWidthPercentage(100f);//100%宽度
		
		 for(int i=0;i<500;i++){
			PdfPCell cell1 = new PdfPCell(new Paragraph("基本信息"+i,FontChinese));
			cell1.setBorder(1);
			cell1.setMinimumHeight(20f);
			cell1.setBorderColor(Color.BLUE);
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
			table.addCell(cell1);
		}
		
		document.add(table);
		//页眉
		Rectangle page = document.getPageSize();
	    PdfPTable head = new PdfPTable(1);
	    head.addCell("head");
	    head.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
	    head.writeSelectedRows(0, -1, document.leftMargin(), page.getHeight() - document.topMargin()+ head.getTotalHeight(), writer.getDirectContent());

	    //页脚
        PdfPTable foot = new PdfPTable(1);
        foot.addCell("foot");
        foot.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
        foot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(),writer.getDirectContent());
*/
		document.close();
	}
	
	static	class  OnEndPageEventPDF extends PdfPageEventHelper{
		private PdfWriter _writer;
		private Document  _document;
		public OnEndPageEventPDF(PdfWriter _writer, Document _document) {
			this._writer = _writer;
			this._document = _document;
		}
		public PdfWriter get_writer() {
			return _writer;
		}
		public void set_writer(PdfWriter _writer) {
			this._writer = _writer;
		}
		public Document get_document() {
			return _document;
		}
		public void set_document(Document _document) {
			this._document = _document;
		}
		
		/**
		 * 新建一页
		 */
		public void onEndPage(PdfWriter writer, Document document) {
			_document.newPage();
			
			BaseFont bfChinese=null;;
			try {
				bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Font  FontChinese = new Font(bfChinese, 12, Font.NORMAL);
			
			Phrase col1 = new Phrase(15, "", FontChinese);
			
			PdfContentByte cb = _writer.getDirectContent();
	        
	        ColumnText ct = new ColumnText(cb);
	        ct.setSimpleColumn(col1, 60, 300, 100, 300 + 28 * 15, 15, Element.ALIGN_CENTER);
	        int go=-1;
			try {
				go = ct.go();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        ct.setSimpleColumn(col1, 105, 300, 150, 300 + 28 * 15, 15, Element.ALIGN_RIGHT);
	        int go2=-1;
			try {
				go2 = ct.go();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void onOpenDocument(PdfWriter writer, Document document) {
			System.out.println("onOpenDocument");
		}

	
		public void onStartPage(PdfWriter writer, Document document) {
			
		}

		public void onCloseDocument(PdfWriter writer, Document document) {
			System.out.println("onCloseDocument");
		}

	}
}
