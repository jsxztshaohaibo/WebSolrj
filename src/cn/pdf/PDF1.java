package cn.pdf;

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

public class PDF1 {

	public static void main(String[] args) throws DocumentException, IOException {
		Rectangle A4Page = PageSize.A4;
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font  FontChinese = new Font(bfChinese, 12, Font.NORMAL);
		
		Document document = new Document(A4Page);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("F:/Chap0101.pdf"));
		
		//writer.setPageEvent(new OnEndPageEventPDF(writer,document));
		
		document.open();
		
		//tableColspanAndRowspan(document); 
		//tableNested(document);
		//tableWidth(document);  
		cellHeight(FontChinese, document); 
		
		document.close();
	

	}

	private static void cellHeight(Font FontChinese, Document document)
			throws DocumentException {
		PdfPTable table = new PdfPTable(2);  
		
		PdfPCell cell;  
		  
		//����  
		table.addCell(new PdfPCell(new Paragraph("����", FontChinese)));  
		cell = new PdfPCell(new Paragraph("<P>blah--------------</P> blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah"));  
		cell.setNoWrap(false); 
		cell.setUseAscender(true); 
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
		table.addCell(cell);  
		  
		//������  
		table.addCell(new PdfPCell(new Paragraph("������", FontChinese)));  
		cell.setNoWrap(true);  
		table.addCell(cell);
		  
		//���ø߶�  
		table.addCell(new PdfPCell(new Paragraph("����߶�",FontChinese)));  
		cell = new PdfPCell(new Paragraph("1. blah blah\n2. blah blah blah\n3. blah blah\n4. blah blah blah\n5. blah blah\n6. blah blah blah\n7. blah blah\n8. blah blah blah"));  
		table.addCell(cell);
		  
		//�̶��߶�  
		table.addCell(new PdfPCell(new Paragraph("�̶��߶�",FontChinese)));  
		cell.setFixedHeight(50f);  
		table.addCell(cell);  
		  
		//��С�߶�  
		table.addCell(new PdfPCell(new Paragraph("��С�߶�",FontChinese)));  
		cell = new PdfPCell(new Paragraph("��С�߶ȣ�50",FontChinese));  
		cell.setMinimumHeight(50f);  
		table.addCell(cell);  
		  
		//���һ��������page�ײ�  
		table.setExtendLastRow(true);  
		table.addCell(new PdfPCell(new Paragraph("�������һ��",FontChinese)));  
		cell = new PdfPCell(new Paragraph("���һ��������page�ײ�",FontChinese));  
		table.addCell(cell);  
		  
		document.add(table);
	}

	private static void tableWidth(Document document) throws DocumentException {
		PdfPTable table = new PdfPTable(3);  
		PdfPCell cell;  
		cell = new PdfPCell(new Phrase("Cell with colspan 3"));  
		cell.setColspan(3);  
		table.addCell(cell);  
		cell = new PdfPCell(new Phrase("Cell with rowspan 2"));  
		cell.setRowspan(2);  
		table.addCell(cell);  
		table.addCell("row 1; cell 1");  
		table.addCell("row 1; cell 2");  
		table.addCell("row 2; cell 1");  
		table.addCell("row 2; cell 2");  
		  
		//100%  
		table.setWidthPercentage(100);  
		document.add(table);          
		document.add(new Paragraph("\n\n"));  
		  
		//���50% ����  
		table.setHorizontalAlignment(Element.ALIGN_LEFT);  
		document.add(table);  
		document.add(new Paragraph("\n\n"));  
		  
		//���50% ����  
		table.setHorizontalAlignment(Element.ALIGN_CENTER);  
		document.add(table);  
		document.add(new Paragraph("\n\n"));  
		  
		//���50% ����  
		table.setWidthPercentage(50);  
		table.setHorizontalAlignment(Element.ALIGN_RIGHT);  
		document.add(table);  
		document.add(new Paragraph("\n\n"));  
		  
		//�̶����  
		table.setTotalWidth(300);  
		table.setLockedWidth(true);  
		document.add(table);
	}

	private static void tableNested(Document document) throws DocumentException {
		PdfPTable table = new PdfPTable(4);  
		  
		//1��2��  
		PdfPTable nested1 = new PdfPTable(2);  
		nested1.addCell("1.1");  
		nested1.addCell("1.2");  
		  
		//2��1��  
		PdfPTable nested2 = new PdfPTable(1);  
		nested2.addCell("2.1");  
		nested2.addCell("2.2");  
		  
		//�������뵽ָ��λ��  
		for (int k = 0; k < 24; ++k) {  
		    if (k == 1) {  
		        table.addCell(nested1);  
		    } else if (k == 20) {  
		        table.addCell(nested2);  
		    } else {  
		        table.addCell("cell " + k);  
		    }  
		}  
		  
		document.add(table);
	}

	private static void tableColspanAndRowspan(Document document)
			throws DocumentException {
		PdfPTable table = new PdfPTable(3);  
		PdfPCell cell;  
		cell = new PdfPCell(new Phrase("Cell with colspan 3"));  
		cell.setColspan(3);  
		table.addCell(cell);  
		cell = new PdfPCell(new Phrase("Cell with rowspan 2"));  
		cell.setRowspan(2);  
		table.addCell(cell);  
		table.addCell("row 1; cell 1");  
		table.addCell("row 1; cell 2");  
		table.addCell("row 2; cell 1");  
		table.addCell("row 2; cell 2");  
		  
		document.add(table);
	}

}
