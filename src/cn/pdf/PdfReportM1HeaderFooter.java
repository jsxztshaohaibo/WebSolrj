package cn.pdf;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
    

public class PdfReportM1HeaderFooter extends PdfPageEventHelper {  
    
    /** 
     * ҳü 
     */  
    public String header = "";  
    
    /** 
     * �ĵ������С��ҳ��ҳü��ú��ı���Сһ�� 
     */  
    public int presentFontSize = 12;  
    
    /** 
     * �ĵ�ҳ���С�����ǰ�洫�룬����Ĭ��ΪA4ֽ�� 
     */  
    public Rectangle pageSize = PageSize.A4;  
    
    // ģ��  
    public PdfTemplate total;  
    
    // �����������  
    public BaseFont bf = null;  
    
    // ���û����������ɵ��������һ������������������  
    public Font fontDetail = null;  
    
    /** 
     * 
     * Creates a new instance of PdfReportM1HeaderFooter �޲ι��췽��. 
     * 
     */  
    public PdfReportM1HeaderFooter() {  
    
    }  
    
    /** 
     * 
     * Creates a new instance of PdfReportM1HeaderFooter ���췽��. 
     * 
     * @param yeMei 
     *            ҳü�ַ��� 
     * @param presentFontSize 
     *            �����������С 
     * @param pageSize 
     *            ҳ���ĵ���С��A4��A5��A6��ת��ת��Rectangle���� 
     */  
    public PdfReportM1HeaderFooter(String yeMei, int presentFontSize, Rectangle pageSize) {  
        this.header = yeMei;  
        this.presentFontSize = presentFontSize;  
        this.pageSize = pageSize;  
    }  
    
    public void setHeader(String header) {  
        this.header = header;  
    }  
    
    public void setPresentFontSize(int presentFontSize) {  
        this.presentFontSize = presentFontSize;  
    }  
    
    /** 
     * 
     * TODO �ĵ���ʱ����ģ�� 
     * 
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document) 
     */  
    public void onOpenDocument(PdfWriter writer, Document document) {  
        total = writer.getDirectContent().createTemplate(50, 50);// �� ҳ �ľ��εĳ����  
        //Rectangle rectangle = new Rectangle(arg0, arg1, arg2, arg3);
        //total.setBoundingBox();
    }  
    
    /** 
     * 
     * TODO �ر�ÿҳ��ʱ��д��ҳü��д��'�ڼ�ҳ��'�⼸���֡� 
     * 
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document) 
     */  
    public void onEndPage(PdfWriter writer, Document document) {  
    
        try {  
            if (bf == null) {  
                bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);  
            }  
            if (fontDetail == null) {  
                fontDetail = new Font(bf, presentFontSize, Font.NORMAL);// ����������  
            }  
        } catch (DocumentException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    
        // 1.д��ҳü  
        ColumnText.showTextAligned(writer.getDirectContent(), 
        							Element.ALIGN_LEFT, 
        							new Phrase(header, fontDetail), 
        							document.left(),
        							document.top() + 20, 
        							0);  
           
        // 2.д��ǰ�벿�ֵ� �� Xҳ/��  
        int pageS = writer.getPageNumber();  
        String foot1 = "�� " + pageS + " ҳ /��";  
        Phrase footer = new Phrase(foot1, fontDetail);  
    
        // 3.����ǰ�벿�ֵ�foot1�ĳ��ȣ�����ö�λ���һ���ֵ�'Yҳ'�����ֵ�x�����꣬���峤��ҲҪ�����ȥ = len  
        float len = bf.getWidthPoint(foot1, presentFontSize);  
        // 4.�õ���ǰ��PdfContentByte  
        PdfContentByte cb = writer.getDirectContent();  
         
        //System.out.println("document.left():"+document.left());
        // 5.д��ҳ��1��x�����(��margin+��margin + right() -left()- len)/2.0F �ٸ�ƫ��20F�ʺ������Ӿ����ܣ��������ۿ���ȥ��̫ƫ���� 
        // y����ǵױ߽�-20,����������ص������������˾Ͳ���ҳ���ˣ�ע��Y���Ǵ��������ۼӵģ����Ϸ���Topֵ�Ǵ���Bottom�ü��ٿ���ġ�  
       /* //����ҳд���ұߣ�ż��ҳ�������
        if(pageS%2==0){//ż��
        	ColumnText.showTextAligned(cb, 
					Element.ALIGN_CENTER, 
					footer, 
					(document.left()+document.leftMargin()+document.rightMargin()+20F), 
					document.bottom() - 20, 
					0);
        }else{//����
        	ColumnText.showTextAligned(cb, 
					Element.ALIGN_CENTER, 
					footer, 
					(document.right()-document.rightMargin()-len -20F), 
					document.bottom() - 20, 
					0);
        }*/
        ColumnText.showTextAligned(cb, 
        							Element.ALIGN_CENTER, 
        							footer, 
        							(document.rightMargin() + document.right() + document.leftMargin() - document.left() - len) / 2.0F + 20F, 
        							document.bottom() - 20, 
        							0);
    
        // 6.д��ҳ��2��ģ�壨����ҳ�ŵ�Yҳ�����֣���ӵ��ĵ��У�����ģ��ĺ�Y��,X=(�ұ߽�-��߽� - ǰ�벿�ֵ�lenֵ)/2.0F + len �� y ���֮ǰ�ı���һ�£��ױ߽�-20  
        cb.addTemplate(total, 
        				(document.rightMargin() + document.right() + document.leftMargin() - document.left()) / 2.0F + 20F,
        				document.bottom() - 20
        			); // ����ģ����ʾ��λ��  
    
    }  
    
    /** 
     * 
     * TODO �ر��ĵ�ʱ���滻ģ�壬�������ҳüҳ����� 
     * 
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document) 
     */  
    public void onCloseDocument(PdfWriter writer, Document document) {  
        // 7.���һ���ˣ����ǹر��ĵ���ʱ�򣬽�ģ���滻��ʵ�ʵ� Y ֵ,���ˣ�page x of y ������ϣ��������ݸ����ĵ�size��  
        total.beginText();  
        total.setFontAndSize(bf, presentFontSize);// ���ɵ�ģ������塢��ɫ  
        String foot2 = " " + (writer.getPageNumber() - 1) + " ҳ";  
        total.showText(foot2);// ģ����ʾ������  
        total.endText();  
        total.closePath();  
    }  
} 