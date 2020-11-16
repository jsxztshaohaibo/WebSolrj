package cn.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class PDFCreate1File {
	
	
	/**
	 * 创建一份PDF文档
	 * @param fullFilePath
	 */
	public boolean createPDFFile(String fullFilePath){
        Document pdfDocument = new Document();
        try {
            //构建一个PDF文档输出流程
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	//设置中文字体和字体样式
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font f2 = new Font(bfChinese, 2, Font.NORMAL);
            Font f6 = new Font(bfChinese, 6, Font.NORMAL);
            Font f10 = new Font(bfChinese, 10, Font.NORMAL);
            Font f12 = new Font(bfChinese, 12, Font.BOLD);
            //打开PDF文件流
        	pdfDocument.open();
            //设置PDF文件正文内容
            pdfDocument.add(new Paragraph("中国程序员周报", f12)); 
            //换行
            pdfDocument.add(new Paragraph(" ",f6)); 
            //换行
            pdfDocument.add(new Paragraph("中国程序员工作时间调查报告", f10)); 
            //换行
            pdfDocument.add(new Paragraph(" ", f2));
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
	 * 创建一份PDF文档，且标注作者等信息
	 * @param fullFilePath
	 */
	public boolean createPDFFileWithCreatorInfo(String fullFilePath){
        Document pdfDocument = new Document();
        try {
            //构建一个PDF文档输出流程
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	//PDF版本(默认1.4)
        	pdfWriter.setPdfVersion(PdfWriter.PDF_VERSION_1_4);
            //文档属性
            pdfDocument.addTitle("水果大王信息技术有限公司数据安全产品");
            pdfDocument.addAuthor("杭州水果大王信息技术有限公司");
            pdfDocument.addSubject("文件导出的信息安全管控");
            pdfDocument.addKeywords("文件导出,信息安全");//文档关键字信息
            pdfDocument.addCreator("水果大王文件取数系统");//应用程序名称
        	//设置中文字体和字体样式
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font f2 = new Font(bfChinese, 2, Font.NORMAL);
            Font f6 = new Font(bfChinese, 6, Font.NORMAL);
            Font f10 = new Font(bfChinese, 10, Font.NORMAL);
            Font f12 = new Font(bfChinese, 12, Font.BOLD);
            //打开PDF文件流
        	pdfDocument.open();
            //设置PDF文件正文内容
            pdfDocument.add(new Paragraph("中国程序员周报", f12)); 
            //换行
            pdfDocument.add(new Paragraph(" ",f6)); 
            //换行
            pdfDocument.add(new Paragraph("中国程序员工作时间调查报告", f10)); 
            //换行
            pdfDocument.add(new Paragraph(" ", f2));
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
	 * 创建一份PDF文档，并把该文档设置成只读权限
	 * @param fullFilePath
	 */
	public boolean createReadOnlyPDFFile(String fullFilePath){
        Document pdfDocument = new Document();
        try {
            //构建一个PDF文档输出流程
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	pdfWriter.setEncryption(null, null,PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
        	//设置中文字体和字体样式
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font f2 = new Font(bfChinese, 2, Font.NORMAL);
            Font f6 = new Font(bfChinese, 6, Font.NORMAL);
            Font f10 = new Font(bfChinese, 10, Font.NORMAL);
            Font f12 = new Font(bfChinese, 12, Font.BOLD);
            //打开PDF文件流
        	pdfDocument.open();
            //设置PDF文件正文内容
            pdfDocument.add(new Paragraph("中国程序员周报", f12)); 
            //换行
            pdfDocument.add(new Paragraph(" ",f6)); 
            //换行
            pdfDocument.add(new Paragraph("中国程序员工作时间调查报告", f10)); 
            //换行
            pdfDocument.add(new Paragraph(" ", f2));
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
	 * 创建一份PDF文档，并把该文档设置成只读权限，且标注文档作者信息
	 * @param fullFilePath
	 */
	public boolean createReadOnlyPDFFileWithCreatorInfo(String fullFilePath){
        Document pdfDocument = new Document();
        try {
            //构建一个PDF文档输出流程
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	//PDF版本(默认1.4)
        	pdfWriter.setPdfVersion(PdfWriter.PDF_VERSION_1_4);
            //文档属性
            pdfDocument.addTitle("水果大王信息技术有限公司数据安全产品");
            pdfDocument.addAuthor("杭州水果大王信息技术有限公司");
            pdfDocument.addSubject("文件导出的信息安全管控");
            pdfDocument.addKeywords("文件导出,信息安全");//文档关键字信息
            pdfDocument.addCreator("水果大王文件取数系统");//应用程序名称
        	pdfWriter.setEncryption(null, null,PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
        	//设置中文字体和字体样式
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font f2 = new Font(bfChinese, 2, Font.NORMAL);
            Font f6 = new Font(bfChinese, 6, Font.NORMAL);
            Font f10 = new Font(bfChinese, 10, Font.NORMAL);
            Font f12 = new Font(bfChinese, 12, Font.BOLD);
            //打开PDF文件流
        	pdfDocument.open();
            //设置PDF文件正文内容
            pdfDocument.add(new Paragraph("中国程序员周报", f12)); 
            //换行
            pdfDocument.add(new Paragraph(" ",f6)); 
            //换行
            pdfDocument.add(new Paragraph("中国程序员工作时间调查报告", f10)); 
            //换行
            pdfDocument.add(new Paragraph(" ", f2));
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
	 * 创建一份PDF文档，并把该文档设置成只读权限，且标注文档作者信息，并需要作者指定密码才能打开的文件
	 * @param fullFilePath
	 */
	public boolean createReadOnlyPDFFileWithCreatorPassword(String fullFilePath,String openPassword,String creatorPassword){
        Document pdfDocument = new Document();
        try {
            //构建一个PDF文档输出流程
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	//PDF版本(默认1.4)
        	pdfWriter.setPdfVersion(PdfWriter.PDF_VERSION_1_4);
            //文档属性
            pdfDocument.addTitle("水果大王信息技术有限公司数据安全产品");
            pdfDocument.addAuthor("杭州水果大王信息技术有限公司");
            pdfDocument.addSubject("文件导出的信息安全管控");
            pdfDocument.addKeywords("文件导出,信息安全");//文档关键字信息
            pdfDocument.addCreator("水果大王文件取数系统");//应用程序名称
        	pdfWriter.setEncryption(openPassword.getBytes(), creatorPassword.getBytes(),PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
        	//设置中文字体和字体样式
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font f2 = new Font(bfChinese, 2, Font.NORMAL);
            Font f6 = new Font(bfChinese, 6, Font.NORMAL);
            Font f10 = new Font(bfChinese, 10, Font.NORMAL);
            Font f12 = new Font(bfChinese, 12, Font.BOLD);
            //打开PDF文件流
        	pdfDocument.open();
            //设置PDF文件正文内容
            pdfDocument.add(new Paragraph("中国程序员周报", f12)); 
            //换行
            pdfDocument.add(new Paragraph(" ",f6)); 
            //换行
            pdfDocument.add(new Paragraph("中国程序员工作时间调查报告", f10)); 
            //换行
            pdfDocument.add(new Paragraph(" ", f2));
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
		PDFCreate1File pdfCreate1File = new PDFCreate1File();
		long startTime1 = System.currentTimeMillis();
		boolean result1 = pdfCreate1File.createPDFFile("D:/temp/pdftest/11helloword你好.pdf");
		long endTime1 = System.currentTimeMillis();
		System.out.println("pdf文档创建结果：" + result1+"。总计花费时间:"+((endTime1-startTime1)/1000)+"秒");
		System.out.println("--------------------------------------------------------------------------");
		long startTime2 = System.currentTimeMillis();
		boolean result2 = pdfCreate1File.createPDFFileWithCreatorInfo("D:/temp/pdftest/12数据安全管控产品白皮书.pdf");
		long endTime2 = System.currentTimeMillis();
		System.out.println("数据安全管控产品白皮书的pdf文档创建结果：" + result2+"。总计花费时间:"+((endTime2-startTime2)/1000)+"秒");
		System.out.println("--------------------------------------------------------------------------");
		long startTime3 = System.currentTimeMillis();
		boolean result3 = pdfCreate1File.createReadOnlyPDFFile("D:/temp/pdftest/13只读权限PDF文档.pdf");
		long endTime3 = System.currentTimeMillis();
		System.out.println("只读权限的pdf文档创建结果：" + result3+"。总计花费时间:"+((endTime3-startTime3)/1000)+"秒");
		System.out.println("--------------------------------------------------------------------------");
		long startTime4 = System.currentTimeMillis();
		boolean result4 = pdfCreate1File.createReadOnlyPDFFileWithCreatorInfo("D:/temp/pdftest/14只读权限并标注作者信息文档.pdf");
		long endTime4 = System.currentTimeMillis();
		System.out.println("只读权限并标注作者信息的pdf文档创建结果：" + result4+"。总计花费时间:"+((endTime4-startTime4)/1000)+"秒");
		System.out.println("--------------------------------------------------------------------------");
		long startTime5 = System.currentTimeMillis();
		boolean result5 = pdfCreate1File.createReadOnlyPDFFileWithCreatorPassword("D:/temp/pdftest/15只读权限并标注作者信息加密文档.pdf","123456","xuguo123");
		long endTime5 = System.currentTimeMillis();
		System.out.println("只读权限并标注作者信息的加密pdf文档创建结果：" + result5+"。总计花费时间:"+((endTime5-startTime5)/1000)+"秒");
	}
}