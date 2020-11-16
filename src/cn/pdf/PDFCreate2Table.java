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
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PDFCreate2Table {
	
	private String[][] normalTableContent = new String[][]{
			{"序号","姓名","年龄","职业","籍贯","学历","单位名称","联系电话","联系地址","备注"},
			{"1","许果","31","软件工程师","浙江杭州","大学本科","浙江水果大王信息技术有限公司","18905710571","浙江省杭州市西湖区三墩镇三墩街188号","他是一名优秀的IT工程师，日常爱好旅游，运动"}
	};
	
	private String[][] columnTooMuchTableContent = new String[][]{
			{"序号","姓名","年龄","职业","籍贯","学历","单位名称","联系电话","联系地址",
				"语文","数学","英语","物理","化学","生物","政治","历史","地理","音乐","美术","体育","课外实践","学校名称","备注"},
			{"1","许果","31","软件工程师","浙江杭州","大学本科","浙江水果大王信息技术有限公司","18905710571","浙江省杭州市西湖区三墩镇三墩街188号",
					"85","95","75","90","90","85","80","90","90","75","65","75","80","石城中学","他是一名优秀的IT工程师，日常爱好旅游，运动"}
	};
	
	/**
	 * 创建一份普通表格的PDF文件
	 * @param fullFilePath
	 * @return
	 */
	public boolean createNormalTable(String fullFilePath,int rowsNumber){
        Document pdfDocument = new Document();
        try {
        	//构建一个PDF文档输出流程
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	//设置中文字体和字体样式
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font f8 = new Font(bfChinese, 8, Font.NORMAL);
            //打开PDF文件流
        	pdfDocument.open();
            //创建一个N列的表格控件
            PdfPTable pdfTable = new PdfPTable(normalTableContent[0].length);
            //设置表格占PDF文档100%宽度
            pdfTable.setWidthPercentage(100);
            //水平方向表格控件左对齐
            pdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            //创建一个表格的表头单元格
            PdfPCell pdfTableHeaderCell = new PdfPCell();
            //设置表格的表头单元格颜色
            pdfTableHeaderCell.setBackgroundColor(new Color(213, 141, 69));
            pdfTableHeaderCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            for(String tableHeaderInfo : normalTableContent[0]){
            	pdfTableHeaderCell.setPhrase(new Paragraph(tableHeaderInfo, f8));
            	pdfTable.addCell(pdfTableHeaderCell);
            }
            //创建一个表格的正文内容单元格
            PdfPCell pdfTableContentCell = new PdfPCell();
            pdfTableContentCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pdfTableContentCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            //表格内容行数的填充
            for(int i = 0;i < rowsNumber;i++){
	            for(String tableContentInfo : normalTableContent[1]){
	            	pdfTableContentCell.setPhrase(new Paragraph(tableContentInfo, f8));
	            	pdfTable.addCell(pdfTableContentCell);
	            }
            }
            pdfDocument.add(pdfTable);
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
	 * 创建一份表格行内容超长的PDF文件
	 * 产生的问题：如果表格行内容足够多时，容易产生Java程序内存溢出问题
	 * 解决办法：采用PDF文件流式写入方式。即表格内容每写入某个数字的行数时，其内容一方面写满物理文件，另一方面释放内存中存留的内容。
	 * @param fullFilePath
	 * @return
	 */
	public boolean createRowTooMuchTable(String fullFilePath,int rowsNumber,int submitAmount){
        Document pdfDocument = new Document();
        try {
        	//构建一个PDF文档输出流程
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	//设置中文字体和字体样式
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font f8 = new Font(bfChinese, 8, Font.NORMAL);
            //打开PDF文件流
        	pdfDocument.open();
            //创建一个N列的表格控件
            PdfPTable pdfTable = new PdfPTable(normalTableContent[0].length);
            //设置表格占PDF文档100%宽度
            pdfTable.setWidthPercentage(100);
            //水平方向表格控件左对齐
            pdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            //创建一个表格的表头单元格
            PdfPCell pdfTableHeaderCell = new PdfPCell();
            //设置表格的表头单元格颜色
            pdfTableHeaderCell.setBackgroundColor(new Color(213, 141, 69));
            pdfTableHeaderCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            for(String tableHeaderInfo : normalTableContent[0]){
            	pdfTableHeaderCell.setPhrase(new Paragraph(tableHeaderInfo, f8));
            	pdfTable.addCell(pdfTableHeaderCell);
            }
            //创建一个表格的正文内容单元格
            PdfPCell pdfTableContentCell = new PdfPCell();
            pdfTableContentCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pdfTableContentCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            //表格内容行数的填充
            for(int i = 1;i < rowsNumber;i++){
	            for(String tableContentInfo : normalTableContent[1]){
	            	pdfTableContentCell.setPhrase(new Paragraph(tableContentInfo, f8));
	            	pdfTable.addCell(pdfTableContentCell);
	            }
	            //表格内容每写满某个数字的行数时，其内容一方面写入物理文件，另一方面释放内存中存留的内容。
	            if((i%submitAmount)==0){
	            	pdfDocument.add(pdfTable);
	            	pdfTable.deleteBodyRows();
                }else if(i==rowsNumber){
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
	 * 创建一份表格列信息超长的PDF文件
	 * 产生的问题：如果表格行列信息足够多时，默认的A4纸格式大小文档版面把列信息压缩得不好看
	 * 解决办法：采用A3，A2，A1,A0等宽度更宽的页面板式
	 * @param fullFilePath
	 * @return
	 */
	public boolean createColumnTooMuchTable(String fullFilePath,int rowsNumber){
        Document pdfDocument = new Document(PageSize.A2,50,50,50,50);
        try {
        	//构建一个PDF文档输出流程
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	//设置中文字体和字体样式
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font f8 = new Font(bfChinese, 8, Font.NORMAL);
            //打开PDF文件流
        	pdfDocument.open();
            //创建一个N列的表格控件
            PdfPTable pdfTable = new PdfPTable(columnTooMuchTableContent[0].length);
            //设置表格占PDF文档100%宽度
            pdfTable.setWidthPercentage(100);
            //水平方向表格控件左对齐
            pdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            //创建一个表格的表头单元格
            PdfPCell pdfTableHeaderCell = new PdfPCell();
            //设置表格的表头单元格颜色
            pdfTableHeaderCell.setBackgroundColor(new Color(213, 141, 69));
            pdfTableHeaderCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            for(String tableHeaderInfo : columnTooMuchTableContent[0]){
            	pdfTableHeaderCell.setPhrase(new Paragraph(tableHeaderInfo, f8));
            	pdfTable.addCell(pdfTableHeaderCell);
            }
            //创建一个表格的正文内容单元格
            PdfPCell pdfTableContentCell = new PdfPCell();
            pdfTableContentCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pdfTableContentCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            //表格内容行数的填充
            for(int i = 0;i < rowsNumber;i++){
	            for(String tableContentInfo : columnTooMuchTableContent[1]){
	            	pdfTableContentCell.setPhrase(new Paragraph(tableContentInfo, f8));
	            	pdfTable.addCell(pdfTableContentCell);
	            }
            }
            pdfDocument.add(pdfTable);
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
	 * 创建一份表格列信息超长的PDF文件
	 * 产生的问题：如果表格行列信息足够多时，默认的A4纸格式大小文档版面把列信息压缩得不好看；
	 * 产生的问题：如果表格行内容足够多时，容易产生Java程序内存溢出问题
	 * 解决办法：采用A3，A2，A1,A0等宽度更宽的页面板式；
	 * 解决办法：采用PDF文件流式写入方式。即表格内容每写入某个数字的行数时，其内容一方面写满物理文件，另一方面释放内存中存留的内容。
	 * @param fullFilePath
	 * @return
	 */
	public boolean createRowsAndColumnTooMuchTable(String fullFilePath,int rowsNumber,int submitAmount){
		Document pdfDocument = new Document(PageSize.A2,50,50,50,50);
        try {
        	//构建一个PDF文档输出流程
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	//设置中文字体和字体样式
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font f8 = new Font(bfChinese, 8, Font.NORMAL);
            //打开PDF文件流
        	pdfDocument.open();
            //创建一个N列的表格控件
            PdfPTable pdfTable = new PdfPTable(columnTooMuchTableContent[0].length);
            //设置表格占PDF文档100%宽度
            pdfTable.setWidthPercentage(100);
            //水平方向表格控件左对齐
            pdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            //创建一个表格的表头单元格
            PdfPCell pdfTableHeaderCell = new PdfPCell();
            //设置表格的表头单元格颜色
            pdfTableHeaderCell.setBackgroundColor(new Color(213, 141, 69));
            pdfTableHeaderCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            for(String tableHeaderInfo : columnTooMuchTableContent[0]){
            	pdfTableHeaderCell.setPhrase(new Paragraph(tableHeaderInfo, f8));
            	pdfTable.addCell(pdfTableHeaderCell);
            }
            //创建一个表格的正文内容单元格
            PdfPCell pdfTableContentCell = new PdfPCell();
            pdfTableContentCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pdfTableContentCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            //表格内容行数的填充
            for(int i = 0;i < rowsNumber;i++){
	            for(String tableContentInfo : columnTooMuchTableContent[1]){
	            	pdfTableContentCell.setPhrase(new Paragraph(tableContentInfo, f8));
	            	pdfTable.addCell(pdfTableContentCell);
	            }
	            //表格内容每写满某个数字的行数时，其内容一方面写入物理文件，另一方面释放内存中存留的内容。
	            if((i%submitAmount)==0){
	            	pdfDocument.add(pdfTable);
	            	pdfTable.deleteBodyRows();
                }else if(i==rowsNumber){
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
	 * @param args
	 */
	public static void main(String[] args) {
		PDFCreate2Table pdfCreate2Table = new PDFCreate2Table();
		long startTime1 = System.currentTimeMillis();
		boolean result1 = pdfCreate2Table.createNormalTable("D:/temp/pdftest/21表格正常内容文档.pdf", 500);
		long endTime1 = System.currentTimeMillis();
		System.out.println("表格正常内容的pdf文档创建结果：" + result1+"。总计花费时间:"+((endTime1-startTime1)/1000)+"秒");
		System.out.println("--------------------------------------------------------------------------");
		long startTime2 = System.currentTimeMillis();
		boolean result2 = pdfCreate2Table.createRowTooMuchTable("D:/temp/pdftest/22表格行信息超长文档.pdf", 500000,2000);
		long endTime2 = System.currentTimeMillis();
		System.out.println("表格行信息超长的pdf文档创建结果：" + result2+"。总计花费时间:"+((endTime2-startTime2)/1000)+"秒");
		System.out.println("--------------------------------------------------------------------------");
		long startTime3 = System.currentTimeMillis();
		boolean result3 = pdfCreate2Table.createColumnTooMuchTable("D:/temp/pdftest/23表格列信息超长文档.pdf", 5000);
		long endTime3 = System.currentTimeMillis();
		System.out.println("表格行信息超长的pdf文档创建结果：" + result3+"。总计花费时间:"+((endTime3-startTime3)/1000)+"秒");
		System.out.println("--------------------------------------------------------------------------");
		long startTime4 = System.currentTimeMillis();
		boolean result4 = pdfCreate2Table.createRowTooMuchTable("D:/temp/pdftest/24表格行列都信息超长文档.pdf", 200000,2000);
		long endTime4 = System.currentTimeMillis();
		System.out.println("表格行列信息都超长的pdf文档创建结果：" + result4+"。总计花费时间:"+((endTime4-startTime4)/1000)+"秒");
		
	}

}
