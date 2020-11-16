package cn.pdf;

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PDF2 {

	public static void main(String[] args) throws DocumentException, IOException {
		Rectangle A4Page = PageSize.A4;
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		
		//��һ��Ŀ¼����
		Font font4FisttTitle = new Font(bfChinese, 24, Font.BOLD);
		//�ڶ���Ŀ¼����
		Font font4SecondTitle = new Font(bfChinese, 20, Font.BOLD);
		//������Ŀ¼����
		Font font4ThirdName = new Font(bfChinese, 16, Font.BOLD);
		//ԺУ��Ϣ����
		Font font4SchoolName = new Font(bfChinese,12, Font.BOLD);
		//רҵ��Ϣ����
		Font font4specialty = new Font(bfChinese,12, Font.NORMAL);
		
		Document document = new Document(A4Page);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("F:/"+System.currentTimeMillis()+".pdf"));
		
		writer.setPageEvent(new PdfReportM1HeaderFooter("", 12, PageSize.A4));
		
		//writer.setPageEvent(new OnEndPageEventPDF(writer,document));
		document.open();
		
		//tableColspanAndRowspan(document); //�С��кϲ�
		//tableNested(document);//�����Ƕ
		//tableWidth(document);  //�����
		//cellHeight(font4FisttTitle,font4SecondTitle,font4ThirdName,font4SchoolName,font4specialty, document); //��Ԫ�߶�
		//cellWidth(document); 
		
		
		tableTitle(document,font4specialty); //���ñ��title 
		
		document.close();

	}

	private static void cellWidth(Document document)throws DocumentException {
		//���������õ�Ԫ����  
		float[] widths = {0.1f, 0.1f, 0.05f, 0.75f};  
		PdfPTable table = new PdfPTable(widths);  
		table.addCell("10%");  
		table.addCell("10%");  
		table.addCell("5%");  
		table.addCell("75%");  
		table.addCell("aa");  
		table.addCell("aa");  
		table.addCell("a");  
		table.addCell("aaaaaaaaaaaaaaa");  
		table.addCell("bb");  
		table.addCell("bb");  
		table.addCell("b");  
		table.addCell("bbbbbbbbbbbbbbb");  
		table.addCell("cc");  
		table.addCell("cc");  
		table.addCell("c");  
		table.addCell("ccccccccccccccc");  
		document.add(table);  
		document.add(new Paragraph("\n\n"));  
		  
		//��������  
		widths[0] = 20f;  
		widths[1] = 20f;  
		widths[2] = 10f;  
		widths[3] = 50f;  
		table.setWidths(widths);  
		document.add(table);  
		  
		//������ֵ���õ�Ԫ����  
		widths[0] = 50f;  
		widths[1] = 50f;  
		widths[2] = 20f;  
		widths[3] = 50f;  
		Rectangle r = new Rectangle(PageSize.A4.getRight(), PageSize.A4.getTop());  
		table.setWidthPercentage(widths, r);  
		document.add(new Paragraph("\n\n"));  
		document.add(table);
	}

	private static void tableTitle(Document document,Font font4specialty) throws DocumentException {
		String[] titleData = { "����", "ԺУרҵ", "�ƻ�", "����","ѧ��" ,"ѧ��" };  
		// 12  
		PdfPTable datatable = new PdfPTable(titleData.length);  
		int headerwidths[] = { 50,168 ,50, 50,50 ,50}; // percentage  
		
		//datatable.setTotalWidth(300f);
		datatable.setWidths(headerwidths);  
		datatable.setWidthPercentage(100);  
		datatable.getDefaultCell().setPadding(3);  
		datatable.getDefaultCell().setBorderWidth(2);  
		datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);  
		//table ��ӱ�ͷ
		for(int i=0;i<titleData.length;i++){
			datatable.addCell(new PdfPCell(new Paragraph(titleData[i], font4specialty)) );  
		}
		datatable.setHeaderRows(1);  
		//�߿�  
		datatable.getDefaultCell().setBorderWidth(1);  
		//���������
		String[] detailData ={ "0174", "����ʦ����ѧ�麣��У(�㶫�麣)", "20", "����","4" ,"2200" };  
		for (int i = 1; i < 1000; i++) {  
		    for (int x = 0; x < titleData.length; x++) {  
		        datatable.addCell( new PdfPCell(new Paragraph(detailData[x], font4specialty)));  
		    }  
		}  
		  
		document.add(datatable);
	}

	private static void cellHeight(Font f1,Font f2,Font f3,Font f4,Font f5, Document document)throws DocumentException {
		PdfPTable table = new PdfPTable(2);  
		  
		PdfPCell cell;  
		  
		//����  
		table.addCell(new PdfPCell(new Paragraph("����", f1)));  
		cell = new PdfPCell(new Paragraph("blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah"));  
		cell.setNoWrap(false);  
		table.addCell(cell);  
		  
		//������  
		table.addCell(new PdfPCell(new Paragraph("������", f2)));  
		cell.setNoWrap(true);  
		table.addCell(cell);
		  
		//���ø߶�  
		table.addCell(new PdfPCell(new Paragraph("����߶�",f3)));  
		cell = new PdfPCell(new Paragraph("1. blah blah\n2. blah blah blah\n3. blah blah\n4. blah blah blah\n5. blah blah\n6. blah blah blah\n7. blah blah\n8. blah blah blah"));  
		table.addCell(cell);
		  
		//�̶��߶�  
		table.addCell(new PdfPCell(new Paragraph("�̶��߶�",f4)));  
		cell.setFixedHeight(500f);  
		table.addCell(cell);  
		  
		//��С�߶�  
		table.addCell(new PdfPCell(new Paragraph("��С�߶�",f5)));  
		cell = new PdfPCell(new Paragraph("��С�߶ȣ�50",f5));  
		cell.setMinimumHeight(50f);  
		table.addCell(cell);  
		  
		//���һ��������page�ײ�  
		table.setExtendLastRow(true);  
		table.addCell(new PdfPCell(new Paragraph("�������һ��",f5)));  
		cell = new PdfPCell(new Paragraph("���һ��������page�ײ�",f5));  
		table.addCell(cell);  
		  
		
		//����  
		table.addCell(new PdfPCell(new Paragraph("����", f1)));  
		cell = new PdfPCell(new Paragraph("blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah"));  
		cell.setNoWrap(false);  
		table.addCell(cell);  
		  
		//������  
		table.addCell(new PdfPCell(new Paragraph("������", f2)));  
		cell.setNoWrap(true);  
		table.addCell(cell);
		  
		//���ø߶�  
		table.addCell(new PdfPCell(new Paragraph("����߶�",f3)));  
		cell = new PdfPCell(new Paragraph("1. blah blah\n2. blah blah blah\n3. blah blah\n4. blah blah blah\n5. blah blah\n6. blah blah blah\n7. blah blah\n8. blah blah blah"));  
		table.addCell(cell);
		  
		//�̶��߶�  
		table.addCell(new PdfPCell(new Paragraph("�̶��߶�",f4)));  
		cell.setFixedHeight(500f);  
		table.addCell(cell);  
		  
		//��С�߶�  
		table.addCell(new PdfPCell(new Paragraph("��С�߶�",f5)));  
		cell = new PdfPCell(new Paragraph("��С�߶ȣ�50",f5));  
		cell.setMinimumHeight(50f);  
		table.addCell(cell);  
		  
		//���һ��������page�ײ�  
		table.setExtendLastRow(true);  
		table.addCell(new PdfPCell(new Paragraph("�������һ��",f5)));  
		cell = new PdfPCell(new Paragraph("���һ��������page�ײ�",f5));  
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

	private static void tableColspanAndRowspan(Document document)throws DocumentException {
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
