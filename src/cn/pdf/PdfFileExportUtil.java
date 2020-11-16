package cn.pdf;

import java.awt.Color;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class PdfFileExportUtil {
	
	private static Font pdf8Font = null;
	private static Font pdf20Font = null;
	
	/**
	 * ����PDF��������Ϣ
	 * @param pdfDocument
	 */
	public static Document setCreatorInfo(Document pdfDocument){
		if(pdfDocument==null){
			return null;
		}
		//�ĵ�����
        pdfDocument.addTitle("ˮ��������Ϣ�������޹�˾���ݰ�ȫ��Ʒ");
        pdfDocument.addAuthor("����ˮ��������Ϣ�������޹�˾");
        pdfDocument.addSubject("�ļ���������Ϣ��ȫ�ܿ�");
        pdfDocument.addKeywords("�ļ�����,��Ϣ��ȫ");//�ĵ��ؼ�����Ϣ
        pdfDocument.addCreator("ˮ�������ļ�ȡ��ϵͳ");//Ӧ�ó�������
        return pdfDocument;
    }
	
	
	/**
	 * ��ȡ�����ַ�������8�����壬������������ݵ������ʽ
	 * @param fullFilePath
	 */
	public static Font getChinese8Font()throws DocumentException,IOException{
		if(pdf8Font==null){
    		//�������������������ʽ
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            pdf8Font = new Font(bfChinese, 8, Font.NORMAL);
    	}
    	return pdf8Font;      
    }
	
	/**
	 * ��ȡ�����ַ�������8�����壬����������ˮӡ��Ϣ
	 * @param fullFilePath
	 */
	public static Font getChinese20Font()throws DocumentException,IOException{
		if(pdf20Font==null){
    		//�������������������ʽ
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            pdf20Font = new Font(bfChinese, 20, Font.BOLD, Color.CYAN);
    	}
    	return pdf20Font;      
    }
	
	/**
	 * ���ó�ֻ��Ȩ��
	 * @param pdfWriter
	 */
	public static PdfWriter setReadOnlyPDFFile(PdfWriter pdfWriter)throws DocumentException{
		pdfWriter.setEncryption(null, null,PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
		return pdfWriter;
    }
	
	/**
	 * ���һ��ͼƬ�����չʾλ�úͽǶ���Ϣ
	 * @param waterMarkImage
	 * @param xPosition
	 * @param yPosition
	 * @return
	 */
	public static Image getWaterMarkImage(Image waterMarkImage,float xPosition,float yPosition){
    	waterMarkImage.setAbsolutePosition(xPosition, yPosition);//����
    	waterMarkImage.setRotation(-20);//��ת ����
    	waterMarkImage.setRotationDegrees(-45);//��ת �Ƕ�
    	waterMarkImage.scalePercent(100);//���ձ�������
        return waterMarkImage;
    }
	
	/**
	 * ΪPDF��ҳʱ��������ı�ˮӡ���¼���Ϣ
	 */
	class TextWaterMarkPdfPageEvent extends PdfPageEventHelper{
		
		private String waterMarkText;
		
		public TextWaterMarkPdfPageEvent(String waterMarkText){
			this.waterMarkText = waterMarkText;
		}
		
		public void onEndPage(PdfWriter writer, Document document) {
	        try{
		        float pageWidth = document.right()+document.left();//��ȡpdf��������ҳ����
		        float pageHeight = document.top()+document.bottom();//��ȡpdf��������ҳ��߶�
		        //����ˮӡ�����ʽ
		        Font waterMarkFont = PdfFileExportUtil.getChinese20Font();
		        PdfContentByte waterMarkPdfContent = writer.getDirectContentUnder();
		        Phrase phrase = new Phrase(waterMarkText, waterMarkFont);
		        ColumnText.showTextAligned(waterMarkPdfContent,Element.ALIGN_CENTER,phrase,  pageWidth*0.25f,pageHeight*0.2f,45);
		        ColumnText.showTextAligned(waterMarkPdfContent,Element.ALIGN_CENTER,phrase,  pageWidth*0.25f,pageHeight*0.5f,45);
		        ColumnText.showTextAligned(waterMarkPdfContent,Element.ALIGN_CENTER,phrase,  pageWidth*0.25f,pageHeight*0.8f,45);
		        ColumnText.showTextAligned(waterMarkPdfContent,Element.ALIGN_CENTER,phrase,  pageWidth*0.65f,pageHeight*0.2f,45);
		        ColumnText.showTextAligned(waterMarkPdfContent,Element.ALIGN_CENTER,phrase,  pageWidth*0.65f,pageHeight*0.5f,45);
		        ColumnText.showTextAligned(waterMarkPdfContent,Element.ALIGN_CENTER,phrase,  pageWidth*0.65f,pageHeight*0.8f,45);
	        }catch(DocumentException de) {
	            de.printStackTrace();
	            System.err.println("pdf watermark text: " + de.getMessage());
	        }catch(IOException de) {
	            de.printStackTrace();
	            System.err.println("pdf watermark text: " + de.getMessage());
	        }
	    }
	}
	
	/**
	 * ΪPDF��ҳʱ�������ͼƬˮӡ���¼���Ϣ
	 */
	class PictureWaterMarkPdfPageEvent extends PdfPageEventHelper{
		
		private String waterMarkFullFilePath;
		private Image waterMarkImage;
		
		public PictureWaterMarkPdfPageEvent(String waterMarkFullFilePath){
			this.waterMarkFullFilePath = waterMarkFullFilePath;
		}
		
		public void onEndPage(PdfWriter writer, Document document) {
	        try{
		        float pageWidth = document.right()+document.left();//��ȡpdf��������ҳ����
		        float pageHeight = document.top()+document.bottom();//��ȡpdf��������ҳ��߶�
		        PdfContentByte waterMarkPdfContent = writer.getDirectContentUnder();
	            //������һ��ͼƬʵ����������PDF�ĵ�ֻӦ��һ��ͼƬ���󣬼��������Ϊ����ͼƬˮӡ����PDF�ĵ���С����
		        if(waterMarkImage == null){
		        	waterMarkImage = Image.getInstance(waterMarkFullFilePath);
		        }
                //���ˮӡͼƬ���ĵ��������ݲ��ú������У���������ģʽ����ͼƬˮӡ
                waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage,pageWidth*0.2f,pageHeight*0.1f));
                waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage,pageWidth*0.2f,pageHeight*0.4f));
                waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage,pageWidth*0.2f,pageHeight*0.7f));
                waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage,pageWidth*0.6f,pageHeight*0.1f));
                waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage,pageWidth*0.6f,pageHeight*0.4f));
                waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage,pageWidth*0.6f,pageHeight*0.7f));
                PdfGState gs = new PdfGState();
                gs.setFillOpacity(0.2f);//����͸����Ϊ0.2
                waterMarkPdfContent.setGState(gs);
	        }catch(DocumentException de) {
	            de.printStackTrace();
	            System.err.println("pdf watermark image: " + de.getMessage());
	        }catch(IOException de) {
	            de.printStackTrace();
	            System.err.println("pdf watermark image: " + de.getMessage());
	        }
	    }
	}
	
	/**
	 * ΪPDF��ҳʱ�������header��footer��Ϣ���¼���Ϣ
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
    	        //�ĵ�ҳͷ��Ϣ����
    	        float x = document.top(-20);
    	        //ҳͷ��Ϣ����
    	        headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_LEFT,"���ݰ�ȫ��Ʒ",document.left(), x, 0);
    	        //ҳͷ��Ϣ�м�
    	        headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_CENTER,"��"+writer.getPageNumber()+ "ҳ",(document.right() + document.left())/2, x, 0);
    	        //ҳͷ��Ϣ����
    	        headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_RIGHT,"����ˮ��������Ϣ�������޹�˾",document.right(), x, 0);
    	        
    	        //�ĵ�ҳ����Ϣ����
    	        float y = document.bottom(-20);
    	        //ҳ����Ϣ����
    	        headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_LEFT,  "--",document.left(), y, 0);
    	        //ҳ����Ϣ�м�
    	        headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_CENTER,"-",(document.right() + document.left())/2,y, 0);
    	        //ҳ����Ϣ����
    	        headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_RIGHT, "--",document.right(), y, 0);
    	        
    	        
    	        headAndFootPdfContent.endText();
    	        headAndFootPdfContent.restoreState();
	        }catch(DocumentException de) {
	            de.printStackTrace();
	            System.err.println("pdf headAndFoot : " + de.getMessage());
	        }catch(IOException de) {
	            de.printStackTrace();
	            System.err.println("pdf headAndFoot : " + de.getMessage());
	        }
	    }
	}
	
	
	/**=================================================**/
	/**
	 * ΪPDF��ҳʱ�������header��footer��Ϣ���¼���Ϣ
	 */
	class NewHeadFootInfoPdfPageEvent extends PdfPageEventHelper{
		
		public NewHeadFootInfoPdfPageEvent(){
		}
		
		 public void onEndPage(PdfWriter writer, Document document) {
	        HeaderFooter header = new HeaderFooter(new Phrase("This is a header without a page number"), false);
	        header.setAlignment(HeaderFooter.ALIGN_RIGHT);
			header.setBorder(Rectangle.BOTTOM);
			header.setBorderColor(new Color(23, 141, 169));
			document.setHeader(header);

			HeaderFooter footer = new HeaderFooter(new Phrase("This is page: "), true);
			footer.setAlignment(HeaderFooter.ALIGN_RIGHT);
			footer.setBorder(Rectangle.TOP);
			footer.setBorderColor(new Color(23, 141, 169));
			document.setFooter(footer);
	    } 
		
		/*public void onStartPage(PdfWriter writer, Document document) {
			HeaderFooter header = new HeaderFooter(new Phrase("This is a header without a page number"), false);
			header.setAlignment(HeaderFooter.ALIGN_RIGHT);
			header.setBorder(Rectangle.BOTTOM);
			header.setBorderColor(new Color(23, 141, 169));
			document.setHeader(header);
			
			HeaderFooter footer = new HeaderFooter(new Phrase("This is page: "), true);
			footer.setAlignment(HeaderFooter.ALIGN_RIGHT);
			footer.setBorder(Rectangle.TOP);
			footer.setBorderColor(new Color(23, 141, 169));
			document.setFooter(footer);
		}*/
	}
}