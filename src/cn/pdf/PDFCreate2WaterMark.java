package cn.pdf;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

public class PDFCreate2WaterMark {
	
	/**
	 * 根据图片文件路径构建一个图片对象
	 * @param waterMarkFullFilePath
	 * @param xPosition
	 * @param yPosition
	 * @return
	 * @throws BadElementException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private Image getWaterMarkImage(String waterMarkFullFilePath,float xPosition,float yPosition)throws BadElementException, MalformedURLException, IOException{
    	Image waterMarkImage = Image.getInstance(waterMarkFullFilePath);
    	waterMarkImage.setAbsolutePosition(xPosition, yPosition);//坐标
    	waterMarkImage.setRotation(-20);//旋转 弧度
    	waterMarkImage.setRotationDegrees(-45);//旋转 角度
    	waterMarkImage.scalePercent(100);//依照比例缩放
        return waterMarkImage;
    }
    
	/**
	 * 对一个图片对象设置展示位置等信息，该对象重复利用，减少PDF文件大小
	 * @param waterMarkImage
	 * @param xPosition
	 * @param yPosition
	 * @return
	 */
    private Image getSingletonWaterMarkImage(Image waterMarkImage,float xPosition,float yPosition){
    	waterMarkImage.setAbsolutePosition(xPosition, yPosition);//坐标
    	waterMarkImage.setRotation(-20);//旋转 弧度
    	waterMarkImage.setRotationDegrees(-45);//旋转 角度
    	waterMarkImage.scalePercent(100);//依照比例缩放
        return waterMarkImage;
    }
	
    /**
     * 对于已存在的PDF文件增加图片水印
     * 图片水印按照两列三行模式，并旋转45度正斜形式添加水印图片
     * @param pdfInputFile
     * @param pdfOutputFile
     * @param waterMarkFullFilePath
     */
	public void addPictureWaterMark(String pdfInputFile,String pdfOutputFile,String waterMarkFullFilePath) {
		PdfStamper pdfStamper = null;
		PdfReader reader = null;
        try {
        	//构造需要添加水印的PDF文件对象
            reader = new PdfReader(pdfInputFile);
            int pdfFileTotalPages = reader.getNumberOfPages() + 1;
            //创建添加水印后的PDF文件对象
            OutputStream pdfOutputStream = new FileOutputStream(pdfOutputFile);
            pdfStamper = new PdfStamper(reader,pdfOutputStream);
            PdfContentByte underPdfContent = null;
            float pageWidth = 0;
            float pageHeight = 0;
            for (int i = 1; i < pdfFileTotalPages; i++) {
            	pageWidth = reader.getPageSize(i).getWidth();//获取pdf文档页面宽度
                pageHeight = reader.getPageSize(i).getHeight();//获取pdf文档页面高度
                underPdfContent = pdfStamper.getUnderContent(i);
                //添加水印图片，文档正文内容采用横向三列，竖向两列模式增加图片水印
                underPdfContent.addImage(getWaterMarkImage(waterMarkFullFilePath,pageWidth*0.2f,pageHeight*0.1f));
                underPdfContent.addImage(getWaterMarkImage(waterMarkFullFilePath,pageWidth*0.2f,pageHeight*0.4f));
                underPdfContent.addImage(getWaterMarkImage(waterMarkFullFilePath,pageWidth*0.2f,pageHeight*0.7f));
                underPdfContent.addImage(getWaterMarkImage(waterMarkFullFilePath,pageWidth*0.6f,pageHeight*0.1f));
                underPdfContent.addImage(getWaterMarkImage(waterMarkFullFilePath,pageWidth*0.6f,pageHeight*0.4f));
                underPdfContent.addImage(getWaterMarkImage(waterMarkFullFilePath,pageWidth*0.6f,pageHeight*0.7f));
                PdfGState gs = new PdfGState();
                gs.setFillOpacity(0.2f);//设置透明度为0.2
                underPdfContent.setGState(gs);
            }
        }catch(DocumentException de){
			de.printStackTrace();
			System.err.println("pdf add watermark documnet opt: " + de.getMessage());
		}catch(IOException ioe){
			ioe.printStackTrace();
			System.err.println("pdf add watermark iostream opt: " + ioe.getMessage());
		}finally{
        	if(reader!=null){
        		reader.close();
            }
        	if(pdfStamper!=null){
        		try{
        			pdfStamper.close();
        		}catch(DocumentException de){
        			de.printStackTrace();
        			System.err.println("pdf add watermark close stamper: " + de.getMessage());
        		}catch(IOException ioe){
        			ioe.printStackTrace();
        			System.err.println("pdf add watermark close stream: " + ioe.getMessage());
        		}
            }
        }
    }
	
	/**
     * 对于已存在的PDF文件增加图片水印，水印图片以单例模式添加，避免极大的增加PDF文件大小
     * 图片水印按照两列三行模式，并旋转45度正斜形式添加水印图片
     * @param pdfInputFile
     * @param pdfOutputFile
     * @param waterMarkFullFilePath
     */
	public void addSingletonPictureWaterMark(String pdfInputFile,String pdfOutputFile,String waterMarkFullFilePath) {
		PdfStamper pdfStamper = null;
		PdfReader reader = null;
        try {
        	//构造需要添加水印的PDF文件对象
            reader = new PdfReader(pdfInputFile);
            int pdfFileTotalPages = reader.getNumberOfPages() + 1;
            //创建添加水印后的PDF文件对象
            OutputStream pdfOutputStream = new FileOutputStream(pdfOutputFile);
            pdfStamper = new PdfStamper(reader,pdfOutputStream);
            PdfContentByte underPdfContent = null;
            float pageWidth = 0;
            float pageHeight = 0;
            //仅设置一个图片实例对象，整个PDF文档只应用一个图片对象，极大减少因为增加图片水印导致PDF文档大小增加
            Image waterMarkImage = Image.getInstance(waterMarkFullFilePath);
            for (int i = 1; i < pdfFileTotalPages; i++) {
            	pageWidth = reader.getPageSize(i).getWidth();//获取pdf文档页面宽度
                pageHeight = reader.getPageSize(i).getHeight();//获取pdf文档页面高度
                underPdfContent = pdfStamper.getUnderContent(i);
                //添加水印图片，文档正文内容采用横向三列，竖向两列模式增加图片水印
                underPdfContent.addImage(getSingletonWaterMarkImage(waterMarkImage,pageWidth*0.2f,pageHeight*0.1f));
                underPdfContent.addImage(getSingletonWaterMarkImage(waterMarkImage,pageWidth*0.2f,pageHeight*0.4f));
                underPdfContent.addImage(getSingletonWaterMarkImage(waterMarkImage,pageWidth*0.2f,pageHeight*0.7f));
                underPdfContent.addImage(getSingletonWaterMarkImage(waterMarkImage,pageWidth*0.6f,pageHeight*0.1f));
                underPdfContent.addImage(getSingletonWaterMarkImage(waterMarkImage,pageWidth*0.6f,pageHeight*0.4f));
                underPdfContent.addImage(getSingletonWaterMarkImage(waterMarkImage,pageWidth*0.6f,pageHeight*0.7f));
                PdfGState gs = new PdfGState();
                gs.setFillOpacity(0.2f);//设置透明度为0.2
                underPdfContent.setGState(gs);
            }
        }catch(DocumentException de){
			de.printStackTrace();
			System.err.println("pdf add watermark documnet opt: " + de.getMessage());
		}catch(IOException ioe){
			ioe.printStackTrace();
			System.err.println("pdf add watermark iostream opt: " + ioe.getMessage());
		}finally{
        	if(reader!=null){
        		reader.close();
            }
        	if(pdfStamper!=null){
        		try{
        			pdfStamper.close();
        		}catch(DocumentException de){
        			de.printStackTrace();
        			System.err.println("pdf add watermark close stamper: " + de.getMessage());
        		}catch(IOException ioe){
        			ioe.printStackTrace();
        			System.err.println("pdf add watermark close stream: " + ioe.getMessage());
        		}
            }
        }
    }
    
	/**
	 * 对于已存在的PDF文件增加文字水印
     * 文字水印按照两列三行模式，并旋转45度正斜形式添加文字水印
	 * @param pdfInputFile
	 * @param pdfOutputFile
	 * @param waterMarkText
	 */
    private void addTextWaterMark(String pdfInputFile,String pdfOutputFile,String waterMarkText) {
    	PdfStamper pdfStamper = null;
		PdfReader reader = null;
        try {
        	//构造需要添加水印的PDF文件对象
            reader = new PdfReader(pdfInputFile);
            int pdfFileTotalPages = reader.getNumberOfPages() + 1;
            //创建添加水印后的PDF文件对象
            OutputStream pdfOutputStream = new FileOutputStream(pdfOutputFile);
            pdfStamper = new PdfStamper(reader,pdfOutputStream);
            PdfContentByte underPdfContent = null;
            float pageWidth = 0;
            float pageHeight = 0;
            for (int i = 1; i < pdfFileTotalPages; i++) {
            	pageWidth = reader.getPageSize(i).getWidth();
                pageHeight = reader.getPageSize(i).getHeight();
                underPdfContent = pdfStamper.getUnderContent(i);
                
                BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
                underPdfContent.beginText();
                underPdfContent.setColorFill(Color.CYAN);
                underPdfContent.setFontAndSize(base, 20);
                // 设置水印文字字体倾斜 开始
                underPdfContent.showTextAligned(Element.ALIGN_LEFT, waterMarkText,pageWidth*0.2f,pageHeight*0.15f, 45);
                underPdfContent.showTextAligned(Element.ALIGN_LEFT, waterMarkText,pageWidth*0.2f,pageHeight*0.45f, 45);
                underPdfContent.showTextAligned(Element.ALIGN_LEFT, waterMarkText,pageWidth*0.2f,pageHeight*0.75f, 45);
                underPdfContent.showTextAligned(Element.ALIGN_LEFT, waterMarkText,pageWidth*0.6f,pageHeight*0.15f, 45);
                underPdfContent.showTextAligned(Element.ALIGN_LEFT, waterMarkText,pageWidth*0.6f,pageHeight*0.45f, 45);
                underPdfContent.showTextAligned(Element.ALIGN_LEFT, waterMarkText,pageWidth*0.6f,pageHeight*0.75f, 45);
                // 字体设置结束
                underPdfContent.endText();
                PdfGState gs = new PdfGState();
                gs.setFillOpacity(0.2f);// 设置透明度为0.2
                underPdfContent.setGState(gs);
            }
        }catch(DocumentException de){
			de.printStackTrace();
			System.err.println("pdf add watermark documnet opt: " + de.getMessage());
		}catch(IOException ioe){
			ioe.printStackTrace();
			System.err.println("pdf add watermark iostream opt: " + ioe.getMessage());
		}finally{
        	if(reader!=null){
        		reader.close();
            }
        	if(pdfStamper!=null){
        		try{
        			pdfStamper.close();
        		}catch(DocumentException de){
        			de.printStackTrace();
        			System.err.println("pdf add watermark close stamper: " + de.getMessage());
        		}catch(IOException ioe){
        			ioe.printStackTrace();
        			System.err.println("pdf add watermark close stream: " + ioe.getMessage());
        		}
            }
        }
    }
    
    /**
	 * 创建一份普通表格的PDF文件时同时创建文字水印
	 * @param fullFilePath
	 * @return
	 */
	public boolean createNormalTableWithTextWaterMark(String fullFilePath,int rowsNumber,String waterMarkText){
		String[][] normalTableContent = new String[][]{
				{"序号","姓名","年龄","职业","籍贯","学历","单位名称","联系电话","联系地址","备注"},
				{"1","许果","31","软件工程师","浙江杭州","大学本科","浙江水果大王信息技术有限公司","18905710571","浙江省杭州市西湖区三墩镇三墩街188号","他是一名优秀的IT工程师，日常爱好旅游，运动"}
		};
        Document pdfDocument = new Document();
        try {
        	//构建一个PDF文档输出流程
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	//通过PDF页面事件模式添加文字水印功能
        	pdfWriter.setPageEvent(new TextWaterMarkPdfPageEvent(waterMarkText));
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
	 * 创建一份普通表格的PDF文件时同时创建图片水印
	 * @param fullFilePath
	 * @return
	 */
	public boolean createNormalTableWithPictureWaterMark(String fullFilePath,int rowsNumber,String waterMarkFullFilePath){
		String[][] normalTableContent = new String[][]{
				{"序号","姓名","年龄","职业","籍贯","学历","单位名称","联系电话","联系地址","备注"},
				{"1","许果","31","软件工程师","浙江杭州","大学本科","浙江水果大王信息技术有限公司","18905710571","浙江省杭州市西湖区三墩镇三墩街188号","他是一名优秀的IT工程师，日常爱好旅游，运动"}
		};
        Document pdfDocument = new Document();
        try {
        	//构建一个PDF文档输出流程
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	//通过PDF页面事件模式添加图片水印功能
        	pdfWriter.setPageEvent(new PictureWaterMarkPdfPageEvent(waterMarkFullFilePath));
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
	 * 创建一份普通表格的PDF文件时同时创建图片水印
	 * @param fullFilePath
	 * @return
	 */
	public boolean createNormalTableWithHeadAndFootInfo(String fullFilePath,int rowsNumber){
		String[][] normalTableContent = new String[][]{
				{"序号","姓名","年龄","职业","籍贯","学历","单位名称","联系电话","联系地址","备注"},
				{"1","许果","31","软件工程师","浙江杭州","大学本科","浙江水果大王信息技术有限公司","18905710571","浙江省杭州市西湖区三墩镇三墩街188号","他是一名优秀的IT工程师，日常爱好旅游，运动"}
		};
        Document pdfDocument = new Document();
        try {
        	//构建一个PDF文档输出流程
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	//通过PDF页面事件模式添加页头和页脚信息功能
        	pdfWriter.setPageEvent(new HeadFootInfoPdfPageEvent());
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
	 * @param args
	 */
	public static void main(String[] args) {
		PDFCreate2Table pdfCreate2Table = new PDFCreate2Table();
		String sourcePDFFile = "D:/temp/pdftest/31需要增加水印的原文档.pdf";
		boolean result1 = pdfCreate2Table.createNormalTable(sourcePDFFile, 500);
		PDFCreate2WaterMark pdfWaterMark = new PDFCreate2WaterMark();
		String waterMarkFullFilePath = "D:/temp/pdftest/login_logo.gif";//水印图片
		if(result1){
			pdfWaterMark.addPictureWaterMark(sourcePDFFile,"D:/temp/pdftest/32增加图片水印大文档.pdf",waterMarkFullFilePath);
			pdfWaterMark.addSingletonPictureWaterMark(sourcePDFFile,"D:/temp/pdftest/33增加图片水印小文档.pdf",waterMarkFullFilePath);
			pdfWaterMark.addTextWaterMark(sourcePDFFile,"D:/temp/pdftest/34增加文字水印文档.pdf","杭州水果大王信息技术");
		}
		pdfWaterMark.createNormalTableWithTextWaterMark("D:/temp/pdftest/35文档生成时同时生成文字水印.pdf",1500,"杭州水果大王信息技术");
		pdfWaterMark.createNormalTableWithPictureWaterMark("D:/temp/pdftest/36文档生成时同时生成图片水印.pdf",1500,waterMarkFullFilePath);
		pdfWaterMark.createNormalTableWithHeadAndFootInfo("D:/temp/pdftest/37文档生成时同时生成页头和页脚信息.pdf",1500);
	}
	
	/**
	 * 为PDF分页时创建添加文本水印的事件信息
	 */
	class TextWaterMarkPdfPageEvent extends PdfPageEventHelper{
		
		private String waterMarkText;
		
		public TextWaterMarkPdfPageEvent(String waterMarkText){
			this.waterMarkText = waterMarkText;
		}
		
		public void onEndPage(PdfWriter writer, Document document) {
	        try{
		        float pageWidth = document.right()+document.left();//获取pdf内容正文页面宽度
		        float pageHeight = document.top()+document.bottom();//获取pdf内容正文页面高度
		        //设置水印字体格式
		        BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
		        Font waterMarkFont = new Font(base, 20, Font.BOLD, Color.CYAN);
		        PdfContentByte waterMarkPdfContent = writer.getDirectContentUnder();
		        Phrase phrase = new Phrase(waterMarkText, waterMarkFont);
		        ColumnText.showTextAligned(waterMarkPdfContent,Element.ALIGN_CENTER,phrase,  
	                    pageWidth*0.25f,pageHeight*0.2f,45);
		        ColumnText.showTextAligned(waterMarkPdfContent,Element.ALIGN_CENTER,phrase,  
	                    pageWidth*0.25f,pageHeight*0.5f,45);
		        ColumnText.showTextAligned(waterMarkPdfContent,Element.ALIGN_CENTER,phrase,  
	                    pageWidth*0.25f,pageHeight*0.8f,45);
		        ColumnText.showTextAligned(waterMarkPdfContent,Element.ALIGN_CENTER,phrase,  
	                    pageWidth*0.65f,pageHeight*0.2f,45);
		        ColumnText.showTextAligned(waterMarkPdfContent,Element.ALIGN_CENTER,phrase,  
	                    pageWidth*0.65f,pageHeight*0.5f,45);
		        ColumnText.showTextAligned(waterMarkPdfContent,Element.ALIGN_CENTER,phrase,  
	                    pageWidth*0.65f,pageHeight*0.8f,45);
	        }catch(DocumentException de) {
	            de.printStackTrace();
	            System.err.println("pdf watermark font: " + de.getMessage());
	        }catch(IOException de) {
	            de.printStackTrace();
	            System.err.println("pdf watermark font: " + de.getMessage());
	        }
	    }
	}
	
	/**
	 * 为PDF分页时创建添加图片水印的事件信息
	 */
	class PictureWaterMarkPdfPageEvent extends PdfPageEventHelper{
		
		private String waterMarkFullFilePath;
		private Image waterMarkImage;
		
		public PictureWaterMarkPdfPageEvent(String waterMarkFullFilePath){
			this.waterMarkFullFilePath = waterMarkFullFilePath;
		}
		
		public void onEndPage(PdfWriter writer, Document document) {
	        try{
		        float pageWidth = document.right()+document.left();//获取pdf内容正文页面宽度
		        float pageHeight = document.top()+document.bottom();//获取pdf内容正文页面高度
		        PdfContentByte waterMarkPdfContent = writer.getDirectContentUnder();
	            //仅设置一个图片实例对象，整个PDF文档只应用一个图片对象，极大减少因为增加图片水印导致PDF文档大小增加
		        if(waterMarkImage == null){
		        	waterMarkImage = Image.getInstance(waterMarkFullFilePath);
		        }
                //添加水印图片，文档正文内容采用横向三列，竖向两列模式增加图片水印
                waterMarkPdfContent.addImage(getSingletonWaterMarkImage(waterMarkImage,pageWidth*0.2f,pageHeight*0.1f));
                waterMarkPdfContent.addImage(getSingletonWaterMarkImage(waterMarkImage,pageWidth*0.2f,pageHeight*0.4f));
                waterMarkPdfContent.addImage(getSingletonWaterMarkImage(waterMarkImage,pageWidth*0.2f,pageHeight*0.7f));
                waterMarkPdfContent.addImage(getSingletonWaterMarkImage(waterMarkImage,pageWidth*0.6f,pageHeight*0.1f));
                waterMarkPdfContent.addImage(getSingletonWaterMarkImage(waterMarkImage,pageWidth*0.6f,pageHeight*0.4f));
                waterMarkPdfContent.addImage(getSingletonWaterMarkImage(waterMarkImage,pageWidth*0.6f,pageHeight*0.7f));
                PdfGState gs = new PdfGState();
                gs.setFillOpacity(0.2f);//设置透明度为0.2
                waterMarkPdfContent.setGState(gs);
	        }catch(DocumentException de) {
	            de.printStackTrace();
	            System.err.println("pdf watermark font: " + de.getMessage());
	        }catch(IOException de) {
	            de.printStackTrace();
	            System.err.println("pdf watermark font: " + de.getMessage());
	        }
	    }
	}
	
	/**
	 * 为PDF分页时创建添加header和footer信息的事件信息
	 */
	class HeadFootInfoPdfPageEvent extends PdfPageEventHelper{
		
		public HeadFootInfoPdfPageEvent(){
		}
		
		public void onEndPage(PdfWriter writer, Document document) {
	        try{
	        	PdfContentByte headAndFootPdfContent = writer.getDirectContent();
	        	headAndFootPdfContent.saveState();
	        	headAndFootPdfContent.beginText();
	        	BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
    	        headAndFootPdfContent.setFontAndSize(bfChinese, 10);
    	        //文档页头信息设置
    	        float x = document.top(-20);
    	        //页头信息左面
    	        headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_LEFT,
    	                           "数据安全产品",
    	                           document.left(), x, 0);
    	        //页头信息中间
    	        headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_CENTER,
    	                            "第"+writer.getPageNumber()+ "页",
    	                           (document.right() + document.left())/2,
    	                           x, 0);
    	        //页头信息右面
    	        headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_RIGHT,
    	                           "杭州水果大王信息技术有限公司",
    	                           document.right(), x, 0);
    	        //文档页脚信息设置
    	        float y = document.bottom(-20);
    	        //页脚信息左面
    	        headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_LEFT,
    	                           "--",
    	                           document.left(), y, 0);
    	        //页脚信息中间
    	        headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_CENTER,
    	                            "-",
    	                           (document.right() + document.left())/2,
    	                           y, 0);
    	        //页脚信息右面
    	        headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_RIGHT,
    	                           "--",
    	                           document.right(), y, 0);
    	        headAndFootPdfContent.endText();
    	        headAndFootPdfContent.restoreState();
	        }catch(DocumentException de) {
	            de.printStackTrace();
	            System.err.println("pdf watermark font: " + de.getMessage());
	        }catch(IOException de) {
	            de.printStackTrace();
	            System.err.println("pdf watermark font: " + de.getMessage());
	        }
	    }
	}
}