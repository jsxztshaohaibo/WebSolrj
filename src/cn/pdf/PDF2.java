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
		
		//第一级目录字体
		Font font4FisttTitle = new Font(bfChinese, 24, Font.BOLD);
		//第二级目录字体
		Font font4SecondTitle = new Font(bfChinese, 20, Font.BOLD);
		//第三级目录字体
		Font font4ThirdName = new Font(bfChinese, 16, Font.BOLD);
		//院校信息字体
		Font font4SchoolName = new Font(bfChinese,12, Font.BOLD);
		//专业信息字体
		Font font4specialty = new Font(bfChinese,12, Font.NORMAL);
		
		Document document = new Document(A4Page);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("F:/"+System.currentTimeMillis()+".pdf"));
		
		writer.setPageEvent(new PdfReportM1HeaderFooter("", 12, PageSize.A4));
		
		//writer.setPageEvent(new OnEndPageEventPDF(writer,document));
		document.open();
		
		//tableColspanAndRowspan(document); //行、列合并
		//tableNested(document);//表格内嵌
		//tableWidth(document);  //表格宽度
		//cellHeight(font4FisttTitle,font4SecondTitle,font4ThirdName,font4SchoolName,font4specialty, document); //单元高度
		//cellWidth(document); 
		
		
		tableTitle(document,font4specialty); //设置表格title 
		
		document.close();

	}

	private static void cellWidth(Document document)throws DocumentException {
		//按比例设置单元格宽度  
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
		  
		//调整比例  
		widths[0] = 20f;  
		widths[1] = 20f;  
		widths[2] = 10f;  
		widths[3] = 50f;  
		table.setWidths(widths);  
		document.add(table);  
		  
		//按绝对值设置单元格宽度  
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
		String[] titleData = { "代码", "院校专业", "计划", "语种","学制" ,"学费" };  
		// 12  
		PdfPTable datatable = new PdfPTable(titleData.length);  
		int headerwidths[] = { 50,168 ,50, 50,50 ,50}; // percentage  
		
		//datatable.setTotalWidth(300f);
		datatable.setWidths(headerwidths);  
		datatable.setWidthPercentage(100);  
		datatable.getDefaultCell().setPadding(3);  
		datatable.getDefaultCell().setBorderWidth(2);  
		datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);  
		//table 添加表头
		for(int i=0;i<titleData.length;i++){
			datatable.addCell(new PdfPCell(new Paragraph(titleData[i], font4specialty)) );  
		}
		datatable.setHeaderRows(1);  
		//边框  
		datatable.getDefaultCell().setBorderWidth(1);  
		//添加数据行
		String[] detailData ={ "0174", "北京师范大学珠海分校(广东珠海)", "20", "不限","4" ,"2200" };  
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
		  
		//折行  
		table.addCell(new PdfPCell(new Paragraph("折行", f1)));  
		cell = new PdfPCell(new Paragraph("blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah"));  
		cell.setNoWrap(false);  
		table.addCell(cell);  
		  
		//不折行  
		table.addCell(new PdfPCell(new Paragraph("不折行", f2)));  
		cell.setNoWrap(true);  
		table.addCell(cell);
		  
		//设置高度  
		table.addCell(new PdfPCell(new Paragraph("任意高度",f3)));  
		cell = new PdfPCell(new Paragraph("1. blah blah\n2. blah blah blah\n3. blah blah\n4. blah blah blah\n5. blah blah\n6. blah blah blah\n7. blah blah\n8. blah blah blah"));  
		table.addCell(cell);
		  
		//固定高度  
		table.addCell(new PdfPCell(new Paragraph("固定高度",f4)));  
		cell.setFixedHeight(500f);  
		table.addCell(cell);  
		  
		//最小高度  
		table.addCell(new PdfPCell(new Paragraph("最小高度",f5)));  
		cell = new PdfPCell(new Paragraph("最小高度：50",f5));  
		cell.setMinimumHeight(50f);  
		table.addCell(cell);  
		  
		//最后一行拉长到page底部  
		table.setExtendLastRow(true);  
		table.addCell(new PdfPCell(new Paragraph("拉长最后一行",f5)));  
		cell = new PdfPCell(new Paragraph("最后一行拉长到page底部",f5));  
		table.addCell(cell);  
		  
		
		//折行  
		table.addCell(new PdfPCell(new Paragraph("折行", f1)));  
		cell = new PdfPCell(new Paragraph("blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah"));  
		cell.setNoWrap(false);  
		table.addCell(cell);  
		  
		//不折行  
		table.addCell(new PdfPCell(new Paragraph("不折行", f2)));  
		cell.setNoWrap(true);  
		table.addCell(cell);
		  
		//设置高度  
		table.addCell(new PdfPCell(new Paragraph("任意高度",f3)));  
		cell = new PdfPCell(new Paragraph("1. blah blah\n2. blah blah blah\n3. blah blah\n4. blah blah blah\n5. blah blah\n6. blah blah blah\n7. blah blah\n8. blah blah blah"));  
		table.addCell(cell);
		  
		//固定高度  
		table.addCell(new PdfPCell(new Paragraph("固定高度",f4)));  
		cell.setFixedHeight(500f);  
		table.addCell(cell);  
		  
		//最小高度  
		table.addCell(new PdfPCell(new Paragraph("最小高度",f5)));  
		cell = new PdfPCell(new Paragraph("最小高度：50",f5));  
		cell.setMinimumHeight(50f);  
		table.addCell(cell);  
		  
		//最后一行拉长到page底部  
		table.setExtendLastRow(true);  
		table.addCell(new PdfPCell(new Paragraph("拉长最后一行",f5)));  
		cell = new PdfPCell(new Paragraph("最后一行拉长到page底部",f5));  
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
		  
		//宽度50% 居左  
		table.setHorizontalAlignment(Element.ALIGN_LEFT);  
		document.add(table);  
		document.add(new Paragraph("\n\n"));  
		  
		//宽度50% 居中  
		table.setHorizontalAlignment(Element.ALIGN_CENTER);  
		document.add(table);  
		document.add(new Paragraph("\n\n"));  
		  
		//宽度50% 居右  
		table.setWidthPercentage(50);  
		table.setHorizontalAlignment(Element.ALIGN_RIGHT);  
		document.add(table);  
		document.add(new Paragraph("\n\n"));  
		  
		//固定宽度  
		table.setTotalWidth(300);  
		table.setLockedWidth(true);  
		document.add(table);
	}

	private static void tableNested(Document document) throws DocumentException {
		PdfPTable table = new PdfPTable(4);  
		  
		//1行2列  
		PdfPTable nested1 = new PdfPTable(2);  
		nested1.addCell("1.1");  
		nested1.addCell("1.2");  
		  
		//2行1列  
		PdfPTable nested2 = new PdfPTable(1);  
		nested2.addCell("2.1");  
		nested2.addCell("2.2");  
		  
		//将表格插入到指定位置  
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
