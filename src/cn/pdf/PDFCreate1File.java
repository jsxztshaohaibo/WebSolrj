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
	 * ����һ��PDF�ĵ�
	 * @param fullFilePath
	 */
	public boolean createPDFFile(String fullFilePath){
        Document pdfDocument = new Document();
        try {
            //����һ��PDF�ĵ��������
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	//�������������������ʽ
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font f2 = new Font(bfChinese, 2, Font.NORMAL);
            Font f6 = new Font(bfChinese, 6, Font.NORMAL);
            Font f10 = new Font(bfChinese, 10, Font.NORMAL);
            Font f12 = new Font(bfChinese, 12, Font.BOLD);
            //��PDF�ļ���
        	pdfDocument.open();
            //����PDF�ļ���������
            pdfDocument.add(new Paragraph("�й�����Ա�ܱ�", f12)); 
            //����
            pdfDocument.add(new Paragraph(" ",f6)); 
            //����
            pdfDocument.add(new Paragraph("�й�����Ա����ʱ����鱨��", f10)); 
            //����
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
            //�ر�PDF�ĵ�����OutputStream�ļ������Ҳ����PDF�ĵ����رշ����ڲ��ر�
        	if(pdfDocument!=null){
        		pdfDocument.close();
        	}
        }        
    }
	
	/**
	 * ����һ��PDF�ĵ����ұ�ע���ߵ���Ϣ
	 * @param fullFilePath
	 */
	public boolean createPDFFileWithCreatorInfo(String fullFilePath){
        Document pdfDocument = new Document();
        try {
            //����һ��PDF�ĵ��������
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	//PDF�汾(Ĭ��1.4)
        	pdfWriter.setPdfVersion(PdfWriter.PDF_VERSION_1_4);
            //�ĵ�����
            pdfDocument.addTitle("ˮ��������Ϣ�������޹�˾���ݰ�ȫ��Ʒ");
            pdfDocument.addAuthor("����ˮ��������Ϣ�������޹�˾");
            pdfDocument.addSubject("�ļ���������Ϣ��ȫ�ܿ�");
            pdfDocument.addKeywords("�ļ�����,��Ϣ��ȫ");//�ĵ��ؼ�����Ϣ
            pdfDocument.addCreator("ˮ�������ļ�ȡ��ϵͳ");//Ӧ�ó�������
        	//�������������������ʽ
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font f2 = new Font(bfChinese, 2, Font.NORMAL);
            Font f6 = new Font(bfChinese, 6, Font.NORMAL);
            Font f10 = new Font(bfChinese, 10, Font.NORMAL);
            Font f12 = new Font(bfChinese, 12, Font.BOLD);
            //��PDF�ļ���
        	pdfDocument.open();
            //����PDF�ļ���������
            pdfDocument.add(new Paragraph("�й�����Ա�ܱ�", f12)); 
            //����
            pdfDocument.add(new Paragraph(" ",f6)); 
            //����
            pdfDocument.add(new Paragraph("�й�����Ա����ʱ����鱨��", f10)); 
            //����
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
            //�ر�PDF�ĵ�����OutputStream�ļ������Ҳ����PDF�ĵ����رշ����ڲ��ر�
        	if(pdfDocument!=null){
        		pdfDocument.close();
        	}
        }        
    }
	
	/**
	 * ����һ��PDF�ĵ������Ѹ��ĵ����ó�ֻ��Ȩ��
	 * @param fullFilePath
	 */
	public boolean createReadOnlyPDFFile(String fullFilePath){
        Document pdfDocument = new Document();
        try {
            //����һ��PDF�ĵ��������
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	pdfWriter.setEncryption(null, null,PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
        	//�������������������ʽ
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font f2 = new Font(bfChinese, 2, Font.NORMAL);
            Font f6 = new Font(bfChinese, 6, Font.NORMAL);
            Font f10 = new Font(bfChinese, 10, Font.NORMAL);
            Font f12 = new Font(bfChinese, 12, Font.BOLD);
            //��PDF�ļ���
        	pdfDocument.open();
            //����PDF�ļ���������
            pdfDocument.add(new Paragraph("�й�����Ա�ܱ�", f12)); 
            //����
            pdfDocument.add(new Paragraph(" ",f6)); 
            //����
            pdfDocument.add(new Paragraph("�й�����Ա����ʱ����鱨��", f10)); 
            //����
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
            //�ر�PDF�ĵ�����OutputStream�ļ������Ҳ����PDF�ĵ����رշ����ڲ��ر�
        	if(pdfDocument!=null){
        		pdfDocument.close();
        	}
        }        
    }
	
	/**
	 * ����һ��PDF�ĵ������Ѹ��ĵ����ó�ֻ��Ȩ�ޣ��ұ�ע�ĵ�������Ϣ
	 * @param fullFilePath
	 */
	public boolean createReadOnlyPDFFileWithCreatorInfo(String fullFilePath){
        Document pdfDocument = new Document();
        try {
            //����һ��PDF�ĵ��������
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	//PDF�汾(Ĭ��1.4)
        	pdfWriter.setPdfVersion(PdfWriter.PDF_VERSION_1_4);
            //�ĵ�����
            pdfDocument.addTitle("ˮ��������Ϣ�������޹�˾���ݰ�ȫ��Ʒ");
            pdfDocument.addAuthor("����ˮ��������Ϣ�������޹�˾");
            pdfDocument.addSubject("�ļ���������Ϣ��ȫ�ܿ�");
            pdfDocument.addKeywords("�ļ�����,��Ϣ��ȫ");//�ĵ��ؼ�����Ϣ
            pdfDocument.addCreator("ˮ�������ļ�ȡ��ϵͳ");//Ӧ�ó�������
        	pdfWriter.setEncryption(null, null,PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
        	//�������������������ʽ
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font f2 = new Font(bfChinese, 2, Font.NORMAL);
            Font f6 = new Font(bfChinese, 6, Font.NORMAL);
            Font f10 = new Font(bfChinese, 10, Font.NORMAL);
            Font f12 = new Font(bfChinese, 12, Font.BOLD);
            //��PDF�ļ���
        	pdfDocument.open();
            //����PDF�ļ���������
            pdfDocument.add(new Paragraph("�й�����Ա�ܱ�", f12)); 
            //����
            pdfDocument.add(new Paragraph(" ",f6)); 
            //����
            pdfDocument.add(new Paragraph("�й�����Ա����ʱ����鱨��", f10)); 
            //����
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
            //�ر�PDF�ĵ�����OutputStream�ļ������Ҳ����PDF�ĵ����رշ����ڲ��ر�
        	if(pdfDocument!=null){
        		pdfDocument.close();
        	}
        }        
    }
	
	/**
	 * ����һ��PDF�ĵ������Ѹ��ĵ����ó�ֻ��Ȩ�ޣ��ұ�ע�ĵ�������Ϣ������Ҫ����ָ��������ܴ򿪵��ļ�
	 * @param fullFilePath
	 */
	public boolean createReadOnlyPDFFileWithCreatorPassword(String fullFilePath,String openPassword,String creatorPassword){
        Document pdfDocument = new Document();
        try {
            //����һ��PDF�ĵ��������
        	OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
        	PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
        	//PDF�汾(Ĭ��1.4)
        	pdfWriter.setPdfVersion(PdfWriter.PDF_VERSION_1_4);
            //�ĵ�����
            pdfDocument.addTitle("ˮ��������Ϣ�������޹�˾���ݰ�ȫ��Ʒ");
            pdfDocument.addAuthor("����ˮ��������Ϣ�������޹�˾");
            pdfDocument.addSubject("�ļ���������Ϣ��ȫ�ܿ�");
            pdfDocument.addKeywords("�ļ�����,��Ϣ��ȫ");//�ĵ��ؼ�����Ϣ
            pdfDocument.addCreator("ˮ�������ļ�ȡ��ϵͳ");//Ӧ�ó�������
        	pdfWriter.setEncryption(openPassword.getBytes(), creatorPassword.getBytes(),PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
        	//�������������������ʽ
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font f2 = new Font(bfChinese, 2, Font.NORMAL);
            Font f6 = new Font(bfChinese, 6, Font.NORMAL);
            Font f10 = new Font(bfChinese, 10, Font.NORMAL);
            Font f12 = new Font(bfChinese, 12, Font.BOLD);
            //��PDF�ļ���
        	pdfDocument.open();
            //����PDF�ļ���������
            pdfDocument.add(new Paragraph("�й�����Ա�ܱ�", f12)); 
            //����
            pdfDocument.add(new Paragraph(" ",f6)); 
            //����
            pdfDocument.add(new Paragraph("�й�����Ա����ʱ����鱨��", f10)); 
            //����
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
            //�ر�PDF�ĵ�����OutputStream�ļ������Ҳ����PDF�ĵ����رշ����ڲ��ر�
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
		boolean result1 = pdfCreate1File.createPDFFile("D:/temp/pdftest/11helloword���.pdf");
		long endTime1 = System.currentTimeMillis();
		System.out.println("pdf�ĵ����������" + result1+"���ܼƻ���ʱ��:"+((endTime1-startTime1)/1000)+"��");
		System.out.println("--------------------------------------------------------------------------");
		long startTime2 = System.currentTimeMillis();
		boolean result2 = pdfCreate1File.createPDFFileWithCreatorInfo("D:/temp/pdftest/12���ݰ�ȫ�ܿز�Ʒ��Ƥ��.pdf");
		long endTime2 = System.currentTimeMillis();
		System.out.println("���ݰ�ȫ�ܿز�Ʒ��Ƥ���pdf�ĵ����������" + result2+"���ܼƻ���ʱ��:"+((endTime2-startTime2)/1000)+"��");
		System.out.println("--------------------------------------------------------------------------");
		long startTime3 = System.currentTimeMillis();
		boolean result3 = pdfCreate1File.createReadOnlyPDFFile("D:/temp/pdftest/13ֻ��Ȩ��PDF�ĵ�.pdf");
		long endTime3 = System.currentTimeMillis();
		System.out.println("ֻ��Ȩ�޵�pdf�ĵ����������" + result3+"���ܼƻ���ʱ��:"+((endTime3-startTime3)/1000)+"��");
		System.out.println("--------------------------------------------------------------------------");
		long startTime4 = System.currentTimeMillis();
		boolean result4 = pdfCreate1File.createReadOnlyPDFFileWithCreatorInfo("D:/temp/pdftest/14ֻ��Ȩ�޲���ע������Ϣ�ĵ�.pdf");
		long endTime4 = System.currentTimeMillis();
		System.out.println("ֻ��Ȩ�޲���ע������Ϣ��pdf�ĵ����������" + result4+"���ܼƻ���ʱ��:"+((endTime4-startTime4)/1000)+"��");
		System.out.println("--------------------------------------------------------------------------");
		long startTime5 = System.currentTimeMillis();
		boolean result5 = pdfCreate1File.createReadOnlyPDFFileWithCreatorPassword("D:/temp/pdftest/15ֻ��Ȩ�޲���ע������Ϣ�����ĵ�.pdf","123456","xuguo123");
		long endTime5 = System.currentTimeMillis();
		System.out.println("ֻ��Ȩ�޲���ע������Ϣ�ļ���pdf�ĵ����������" + result5+"���ܼƻ���ʱ��:"+((endTime5-startTime5)/1000)+"��");
	}
}