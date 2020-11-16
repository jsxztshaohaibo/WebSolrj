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
	 * �����ݿ��е������ݲ���PDF�ļ���ʽ�洢������Ϣ���ܳ���100��
	 * �ĵ�����ֻ��Ȩ�ޣ������ĵ�������Ϣ
	 * ���ĵ�ҳͷ���ù�˾��Ϣ��Ȩ��Ϣ
	 * ��ӹ�˾�����ֺ�ͼƬˮӡ��Ϣ
	 * @param fullFilePath
	 * @param tableContent
	 * @param rowsNumber
	 * @param submitAmount
	 * @return
	 */
	public boolean exportTableContent(String fullFilePath,String[][] tableContent,int rowsNumber,int submitAmount){
		Document pdfDocument = new Document(PageSize.A4,50,50,50,50);
        try {
        	//����һ��PDF�ĵ��������
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	
        	
        	//����������Ϣ
        	//PdfFileExportUtil.setCreatorInfo(pdfDocument);
        	//�����ļ�ֻ��Ȩ��
        	//PdfFileExportUtil.setReadOnlyPDFFile(pdfWriter);
        	
        	PdfFileExportUtil pdfFileExportUtil = new PdfFileExportUtil();
        	//ͨ��PDFҳ���¼�ģʽ�������ˮӡ����
        	//pdfWriter.setPageEvent(pdfFileExportUtil.new TextWaterMarkPdfPageEvent("����ˮ��������Ϣ����"));
        	
        	//ͨ��PDFҳ���¼�ģʽ���ͼƬˮӡ����
        	//String waterMarkFullFilePath = "F:/1.jpg";//ˮӡͼƬ
        	//pdfWriter.setPageEvent(pdfFileExportUtil.new PictureWaterMarkPdfPageEvent(waterMarkFullFilePath));
        	
        	//ͨ��PDFҳ���¼�ģʽ���ҳͷ��ҳ����Ϣ����
        	//pdfWriter.setPageEvent(pdfFileExportUtil.new HeadFootInfoPdfPageEvent());
        	//pdfWriter.setPageEvent(pdfFileExportUtil.new NewHeadFootInfoPdfPageEvent());
        	
        	this.setHeadAndFoot(pdfDocument);
        	
        	
        	//�������������������ʽ
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font f8 = new Font(bfChinese, 16, Font.NORMAL);
            //��PDF�ļ���
        	pdfDocument.open();
        	
            //����һ��N�еı��ؼ�
            PdfPTable pdfTable = new PdfPTable(tableContent[0].length);
            
            //���ñ��ռPDF�ĵ�100%���
            pdfTable.setWidthPercentage(100);
            //ˮƽ������ؼ������
            pdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            
            //����һ�����ı�ͷ��Ԫ��
            PdfPCell pdfTableHeaderCell = new PdfPCell();
            //���ñ��ı�ͷ��Ԫ����ɫ
            pdfTableHeaderCell.setBackgroundColor(new Color(213, 141, 69));
            pdfTableHeaderCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            
            for(String tableHeaderInfo : tableContent[0]){
            	pdfTableHeaderCell.setPhrase(new Paragraph(tableHeaderInfo, f8));
            	pdfTable.addCell(pdfTableHeaderCell);
            }
            //����һ�������������ݵ�Ԫ��
            PdfPCell pdfTableContentCell = new PdfPCell();
            pdfTableContentCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pdfTableContentCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            
            
            //����������������
            for(int i = 0;i < rowsNumber;i++){
	            for(String tableContentInfo : tableContent[1]){
	            	pdfTableContentCell.setPhrase(new Paragraph(tableContentInfo+"("+i+")", f8));
	            	pdfTable.addCell(pdfTableContentCell);
	            }
	            //�������ÿд��ĳ�����ֵ�����ʱ��������һ����д�������ļ�����һ�����ͷ��ڴ��д��������ݡ�
	            if((i%submitAmount)==0){
	            	pdfDocument.add(pdfTable);
	            	pdfTable.deleteBodyRows();
                }else if(i==rowsNumber){//i== (rowsNumber-1)
                	//���ȫ�������������û�ﵽĳ���������ƣ���ҲҪд�������ļ��С�
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
            //�ر�PDF�ĵ�����OutputStream�ļ������Ҳ����PDF�ĵ����رշ����ڲ��ر�
        	if(pdfDocument!=null){
        		pdfDocument.close();
        	}
        }        
    }
	/**
	 * ����ҳ���ҳüҳ��
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
										{"���","����"},
										{"1","���"}
								  };
		/*String[][] tableContent = new String[][]{
				{"���","����","����"},
				{"1","���"},
				{"2","��Ϻ"}
		};
		System.out.println(tableContent.length);
		for(int i = 0;i<tableContent.length;i++){
			for(int j =0;j<tableContent[i].length;j++){
				System.out.println("i="+i+" ; j="+j+"   :"+tableContent[i][j]);
			}
		}*/
		PdfFileExport pdfFileExport = new PdfFileExport();
		pdfFileExport.exportTableContent("F:/41����PDF�ĵ�.pdf", tableContent, 1000, 20);
	}
	
}
