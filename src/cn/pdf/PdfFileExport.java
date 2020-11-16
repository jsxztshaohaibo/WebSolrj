package cn.pdf;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PdfFileExport {
	
	/**
	 * 从数据库中导出数据并以PDF文件形式存储，行信息可能超过100万
	 * 文档仅有只读权限，设置文档作者信息
	 * 在文档页头设置公司信息版权信息
	 * 添加公司的文字和图片水印信息
	 * @param fullFilePath
	 * @param tableContent
	 * @param rowsNumber
	 * @param submitAmount
	 * @return
	 */
	public boolean exportTableContent(String fullFilePath,String[][] tableContent,int rowsNumber,int submitAmount){
		Document pdfDocument = new Document(PageSize.A4,50,50,50,50);
        try {
        	//构建一个PDF文档输出流程
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	
        	
        	//设置作者信息
        	//PdfFileExportUtil.setCreatorInfo(pdfDocument);
        	//设置文件只读权限
        	//PdfFileExportUtil.setReadOnlyPDFFile(pdfWriter);
        	
        	PdfFileExportUtil pdfFileExportUtil = new PdfFileExportUtil();
        	//通过PDF页面事件模式添加文字水印功能
        	//pdfWriter.setPageEvent(pdfFileExportUtil.new TextWaterMarkPdfPageEvent("杭州水果大王信息技术"));
        	
        	//通过PDF页面事件模式添加图片水印功能
        	//String waterMarkFullFilePath = "F:/1.jpg";//水印图片
        	//pdfWriter.setPageEvent(pdfFileExportUtil.new PictureWaterMarkPdfPageEvent(waterMarkFullFilePath));
        	
        	//通过PDF页面事件模式添加页头和页脚信息功能
        	//pdfWriter.setPageEvent(pdfFileExportUtil.new HeadFootInfoPdfPageEvent());
        	//pdfWriter.setPageEvent(pdfFileExportUtil.new NewHeadFootInfoPdfPageEvent());
        	
        	this.setHeadAndFoot(pdfDocument);
        	
        	
        	//设置中文字体和字体样式
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font f8 = new Font(bfChinese, 16, Font.NORMAL);
            //打开PDF文件流
        	pdfDocument.open();
        	
            //创建一个N列的表格控件
            PdfPTable pdfTable = new PdfPTable(tableContent[0].length);
            
            //设置表格占PDF文档100%宽度
            pdfTable.setWidthPercentage(100);
            //水平方向表格控件左对齐
            pdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            
            //创建一个表格的表头单元格
            PdfPCell pdfTableHeaderCell = new PdfPCell();
            //设置表格的表头单元格颜色
            pdfTableHeaderCell.setBackgroundColor(new Color(213, 141, 69));
            pdfTableHeaderCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            
            for(String tableHeaderInfo : tableContent[0]){
            	pdfTableHeaderCell.setPhrase(new Paragraph(tableHeaderInfo, f8));
            	pdfTable.addCell(pdfTableHeaderCell);
            }
            //创建一个表格的正文内容单元格
            PdfPCell pdfTableContentCell = new PdfPCell();
            pdfTableContentCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pdfTableContentCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            
            
            //表格内容行数的填充
            for(int i = 0;i < rowsNumber;i++){
	            for(String tableContentInfo : tableContent[1]){
	            	pdfTableContentCell.setPhrase(new Paragraph(tableContentInfo+"("+i+")", f8));
	            	pdfTable.addCell(pdfTableContentCell);
	            }
	            //表格内容每写满某个数字的行数时，其内容一方面写入物理文件，另一方面释放内存中存留的内容。
	            if((i%submitAmount)==0){
	            	pdfDocument.add(pdfTable);
	            	pdfTable.deleteBodyRows();
                }else if(i==rowsNumber){//i== (rowsNumber-1)
                	//如果全部类容完毕且又没达到某个行数限制，则也要写入物理文件中。
                	pdfDocument.add(pdfTable);
                	pdfTable.deleteBodyRows();
                }
            }
            
            
            return true;
        }catch(FileNotFoundException de) {
            de.printStackTrace();
            System.err.println("pdf file: " + de.getMessage());
            return false;
        }catch(DocumentException de) {
            de.printStackTrace();
            System.err.println("document: " + de.getMessage());
            return false;
        }catch(IOException de) {
            de.printStackTrace();
            System.err.println("pdf font: " + de.getMessage());
            return false;
        }finally{
            //关闭PDF文档流，OutputStream文件输出流也将在PDF文档流关闭方法内部关闭
        	if(pdfDocument!=null){
        		pdfDocument.close();
        	}
        }        
    }
	/**
	 * 设置页面的页眉页脚
	 * @param pdfDocument
	 */
	private void setHeadAndFoot(Document pdfDocument) {
		HeaderFooter header = new HeaderFooter(new Phrase("This is a header without a page number"), false);
		header.setAlignment(HeaderFooter.ALIGN_RIGHT);
		header.setBorder(Rectangle.BOTTOM);
		header.setBorderColor(new Color(23, 141, 169));
		pdfDocument.setHeader(header);

		HeaderFooter footer = new HeaderFooter(new Phrase("This is page: "), true);
		footer.setAlignment(HeaderFooter.ALIGN_RIGHT);
		footer.setBorder(Rectangle.TOP);
		footer.setBorderColor(new Color(23, 141, 169));
		pdfDocument.setFooter(footer);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[][] tableContent = new String[][]{
										{"序号","姓名"},
										{"1","许果"}
								  };
		/*String[][] tableContent = new String[][]{
				{"序号","姓名","年龄"},
				{"1","许果"},
				{"2","大虾"}
		};
		System.out.println(tableContent.length);
		for(int i = 0;i<tableContent.length;i++){
			for(int j =0;j<tableContent[i].length;j++){
				System.out.println("i="+i+" ; j="+j+"   :"+tableContent[i][j]);
			}
		}*/
		PdfFileExport pdfFileExport = new PdfFileExport();
		pdfFileExport.exportTableContent("F:/41导出PDF文档.pdf", tableContent, 1000, 20);
	}
	
}
