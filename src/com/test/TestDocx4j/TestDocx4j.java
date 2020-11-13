package com.test.TestDocx4j; 


import java.math.BigInteger;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Body;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.STDocGrid;
import org.docx4j.wml.STPageOrientation;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Tr;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDocx4j {
	private  WordprocessingMLPackage  wordMLPackage;
    private  ObjectFactory factory;
    
	@Before
    public void init() throws Exception{
		System.out.println("start..."); 
		wordMLPackage = WordprocessingMLPackage.createPackage();
	    factory = Context.getWmlObjectFactory();
    }
    @After
	public  void end() throws Exception{
		wordMLPackage.save(new java.io.File("F:/bolds.docx") ); 
		System.out.println("Done."); 
	}
	@Test
	public  void main()throws Exception { 
		System.out.println( "begin..."); 
		//创建文档处理对象 

		//插入文字方法-1(快捷方法,忽略详细属性) 
		wordMLPackage.getMainDocumentPart().addParagraphOfText("Hello Word!");  
		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title", "Hello Word!");  
		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle"," a subtitle!"); 
		

		//插入文字方法-2(对象构造法,可以操作任何属性) 
		/*org.docx4j.wml.ObjectFactory factory = new org.docx4j.wml.ObjectFactory();//文档子对象工厂 
		org.docx4j.wml.P p = factory.createP();//创建段落P 
		//创建文本段R内容 
		org.docx4j.wml.R run = factory.createR();//创建文本段R 
		org.docx4j.wml.Text t = factory.createText();//创建文本段内容Text 
		t.setValue("text"); 
		run.getRunContent().add(t);//Text添加到R 
		//设置文本段R属性,Optionally, set pPr/rPr@w:b 
		org.docx4j.wml.RPr rpr = factory.createRPr(); 
		org.docx4j.wml.BooleanDefaultTrue b = new org.docx4j.wml.BooleanDefaultTrue();//创建带缺省值的boolen属性对象 
		b.setVal(true); 
		rpr.setB(b); 
		run.setRPr(rpr);//设置文本段R属性 

		p.getParagraphContent().add(run);//R添加到P 

		// 创建默认的段落属性,并加入到段落对象中去 
		org.docx4j.wml.PPr ppr = factory.createPPr(); 
		org.docx4j.wml.ParaRPr paraRpr = factory.createParaRPr(); 
		ppr.setRPr(paraRpr); 
		p.setPPr( ppr );//段落属性PPr添加到P 

		//将P段落添加到文档里 
		wordMLPackage.getMainDocumentPart()..addObject(p); */


		//动态插入打印页面及分栏设置,这时一个A3幅面,页面分2栏的设置,试卷页面 
		//factory = new org.docx4j.wml.ObjectFactory();//文档子对象工厂 
		org.docx4j.wml.SectPr sp= factory.createSectPr(); 
		org.docx4j.wml.SectPr.PgSz pgsz=factory.createSectPrPgSz();//<w:pgSz w:w="23814" w:h="16840" w:orient="landscape" w:code="8"/> 
		pgsz.setW(BigInteger.valueOf(23814L)); 
		pgsz.setH(BigInteger.valueOf(16840L)); 
		pgsz.setOrient(STPageOrientation.LANDSCAPE); 
		pgsz.setCode(BigInteger.valueOf(8L)); 
		sp.setPgSz(pgsz); 

		org.docx4j.wml.SectPr.PgMar pgmar= factory.createSectPrPgMar();//<w:pgMar w:top="1440" w:right="1440" w:bottom="1440" w:left="1440" w:header="720" w:footer="720" w:gutter="0"/> 
		pgmar.setTop(BigInteger.valueOf(1440)); 
		pgmar.setRight(BigInteger.valueOf(1440)); 
		pgmar.setBottom(BigInteger.valueOf(1440)); 
		pgmar.setLeft(BigInteger.valueOf(1440)); 
		pgmar.setHeader(BigInteger.valueOf(720)); 
		pgmar.setFooter(BigInteger.valueOf(720)); 
		sp.setPgMar(pgmar); 

		org.docx4j.wml.CTColumns cols=factory.createCTColumns();//<w:cols w:num="2" w:space="425"/> 
		cols.setNum(BigInteger.valueOf(2)); 
		cols.setSpace(BigInteger.valueOf(425)); 
		sp.setCols(cols); 
		
		
		
		org.docx4j.wml.CTDocGrid grd=factory.createCTDocGrid();//<w:docGrid w:linePitch="360"/> 
		grd.setLinePitch(BigInteger.valueOf(360)); 
		sp.setDocGrid(grd); 

		wordMLPackage.getMainDocumentPart().addObject(sp); 



/*

		// 插入文字方法-3(更加简便快捷的插入内容方法,可以操作任何属性,但必须熟悉ooxml文档格式) 
		//自定义标签转化的时候,必须加xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\"语句 
		String str = "<w:p xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" ><w:r><w:rPr><w:b /></w:rPr><w:t>Bold, just at w:r level</w:t></w:r></w:p>"; 
		wordMLPackage.getMainDocumentPart()..addObject(org.docx4j.XmlUtils.unmarshalString(str)); 

		//自定义标签转化的时候,必须加xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\"语句 
		String str1 = "<w:sectPr xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" w:rsidR=\"00F10179\" w:rsidRPr=\"00CB557A\" w:rsidSect=\"001337D5\"><w:pgSz w:w=\"23814\" w:h=\"16840\" w:orient=\"landscape\" w:code=\"8\"/><w:pgMar w:top=\"1440\" w:right=\"1440\" w:bottom=\"1440\" w:left=\"1440\" w:header=\"720\" w:footer=\"720\" w:gutter=\"0\"/><w:cols w:num=\"2\" w:space=\"425\"/><w:docGrid w:linePitch=\"360\"/></w:sectPr>"; 
		wordMLPackage.getMainDocumentPart()..addObject(org.docx4j.XmlUtils.unmarshalString(str1) ); */

		// Now save it 
	} 
	
	
    @Test
    public void print(){
		System.out.println(System.getProperty("user.dir"));   
    }
   
   
   @Test
   public  void table(){
	   Tbl table = factory.createTbl();
	   Tr tableRow1 = factory.createTr();
	   Tr tableRow2 = factory.createTr();
	   Tr tableRow3 = factory.createTr();
	   Tr tableRow4 = factory.createTr();
	   Tr tableRow5 = factory.createTr();
	   Tr tableRow6 = factory.createTr();
	   Tr tableRow7 = factory.createTr();
	   Tr tableRow8 = factory.createTr();
	   Tr tableRow9 = factory.createTr();
	   Tr tableRow10 = factory.createTr();
	   
	   Tr tableRow11= factory.createTr();
	   Tr tableRow21= factory.createTr();
	   Tr tableRow31= factory.createTr();
	   Tr tableRow41= factory.createTr();
	   Tr tableRow51= factory.createTr();
	   Tr tableRow61= factory.createTr();
	   Tr tableRow71= factory.createTr();
	   Tr tableRow81= factory.createTr();
	   Tr tableRow91= factory.createTr();
	   Tr tableRow101 = factory.createTr();
	   
	   Tr tableRow111= factory.createTr();
	   Tr tableRow211= factory.createTr();
	   Tr tableRow311= factory.createTr();
	   Tr tableRow411= factory.createTr();
	   Tr tableRow511= factory.createTr();
	   Tr tableRow611= factory.createTr();
	   Tr tableRow711= factory.createTr();
	   Tr tableRow811= factory.createTr();
	   Tr tableRow911= factory.createTr();
	   Tr tableRow1011 = factory.createTr();
	 
	   addTableCell(tableRow1, "Field 1Field 1Field 1Field 1Field 1Field 1Field 1Field 1Field 1Field 1");
	   addTableCell(tableRow2, "Field 2Field 2Field 2Field 2Field 2Field 2Field 2Field 2Field 2Field 2");
	   addTableCell(tableRow3, "Field 3Field 3Field 3Field 3Field 3Field 3Field 3Field 3Field 3Field 3");
	   addTableCell(tableRow4, "Field 4Field 4Field 4Field 4Field 4Field 4Field 4Field 4Field 4Field 4");
	   addTableCell(tableRow5, "Field 5Field 5Field 5Field 5Field 5Field 5Field 5Field 5Field 5Field 5");
	   addTableCell(tableRow6, "Field 6Field 6Field 6Field 6Field 6Field 6Field 6Field 6Field 6Field 6");
	   addTableCell(tableRow7, "Field 7Field 7Field 7Field 7Field 7Field 7Field 7Field 7Field 7Field 7");
	   addTableCell(tableRow8, "Field 8Field 8Field 8Field 8Field 8Field 8Field 8Field 8Field 8Field 8");
	   addTableCell(tableRow9, "Field 9Field 9Field 9Field 9Field 9Field 9Field 9Field 9Field 9Field 9");
	   addTableCell(tableRow10, "Field 10Field 10Field 10Field 10Field 10Field 10Field 10Field 10Field 10");
	   
	   addTableCell(tableRow11, "tableRow11tableRow11tableRow11tableRow11tableRow11tableRow11tableRow11tableRow11tableRow11tableRow11tableRow11tableRow11tableRow11tableRow11tableRow11tableRow11tableRow11tableRow11tableRow11tableRow11tableRow11");
	   addTableCell(tableRow21, "tableRow21tableRow21tableRow21tableRow21tableRow21tableRow21tableRow21tableRow21tableRow21tableRow21tableRow21tableRow21tableRow21tableRow21tableRow21tableRow21tableRow21tableRow21tableRow21tableRow21tableRow21");
	   addTableCell(tableRow31, "tableRow31tableRow31tableRow31tableRow31tableRow31tableRow31tableRow31tableRow31tableRow31tableRow31tableRow31tableRow31tableRow31tableRow31tableRow31tableRow31tableRow31tableRow31tableRow31tableRow31tableRow31");
	   addTableCell(tableRow41, "tableRow41tableRow41tableRow41tableRow41tableRow41tableRow41tableRow41tableRow41tableRow41tableRow41tableRow41tableRow41tableRow41tableRow41tableRow41tableRow41tableRow41tableRow41tableRow41tableRow41tableRow41");
	   addTableCell(tableRow51, "tableRow51tableRow51tableRow51tableRow51tableRow51tableRow51tableRow51tableRow51tableRow51tableRow51tableRow51tableRow51tableRow51tableRow51tableRow51tableRow51tableRow51tableRow51tableRow51tableRow51tableRow51");
	   addTableCell(tableRow61, "tableRow61tableRow61tableRow61tableRow61tableRow61tableRow61tableRow61tableRow61tableRow61tableRow61tableRow61tableRow61tableRow61tableRow61tableRow61tableRow61tableRow61tableRow61tableRow61tableRow61tableRow61");
	   addTableCell(tableRow71, "tableRow71tableRow71tableRow71tableRow71tableRow71tableRow71tableRow71tableRow71tableRow71tableRow71tableRow71tableRow71tableRow71tableRow71tableRow71tableRow71tableRow71tableRow71tableRow71tableRow71tableRow71");
	   addTableCell(tableRow81, "tableRow81tableRow81tableRow81tableRow81tableRow81tableRow81tableRow81tableRow81tableRow81tableRow81tableRow81tableRow81tableRow81tableRow81tableRow81tableRow81tableRow81tableRow81tableRow81tableRow81tableRow81");
	   addTableCell(tableRow91, "tableRow91tableRow91tableRow91tableRow91tableRow91tableRow91tableRow91tableRow91tableRow91tableRow91tableRow91tableRow91tableRow91tableRow91tableRow91tableRow91tableRow91tableRow91tableRow91tableRow91tableRow91");
	   addTableCell(tableRow101, "Field 10Field 10Field 10Field 10Field 10Field 10Field 10Field 10Field 10");
	   addTableCell(tableRow111, "tableRow111tableRow111tableRow111tableRow111tableRow111tableRow111tableRow111tableRow111tableRow111tableRow111tableRow111tableRow111tableRow111tableRow111tableRow111");
	   addTableCell(tableRow211, "tableRow211tableRow211tableRow211tableRow211tableRow211tableRow211tableRow211tableRow211tableRow211tableRow211tableRow211tableRow211tableRow211tableRow211tableRow211");
	   addTableCell(tableRow311, "tableRow311tableRow311tableRow311tableRow311tableRow311tableRow311tableRow311tableRow311tableRow311tableRow311tableRow311tableRow311tableRow311tableRow311tableRow311");
	   addTableCell(tableRow411, "tableRow411tableRow411tableRow411tableRow411tableRow411tableRow411tableRow411tableRow411tableRow411tableRow411tableRow411tableRow411tableRow411tableRow411tableRow411");
	   addTableCell(tableRow511, "tableRow511tableRow511tableRow511tableRow511tableRow511tableRow511tableRow511tableRow511tableRow511tableRow511tableRow511tableRow511tableRow511tableRow511tableRow511");
	   addTableCell(tableRow611, "tableRow611tableRow611tableRow611tableRow611tableRow611tableRow611tableRow611tableRow611tableRow611tableRow611tableRow611tableRow611tableRow611tableRow611tableRow611");
	   addTableCell(tableRow711, "tableRow711tableRow711tableRow711tableRow711tableRow711tableRow711tableRow711tableRow711tableRow711tableRow711tableRow711tableRow711tableRow711tableRow711tableRow711");
	   addTableCell(tableRow811, "tableRow811tableRow811tableRow811tableRow811tableRow811tableRow811tableRow811tableRow811tableRow811tableRow811tableRow811tableRow811tableRow811tableRow811tableRow811");
	   addTableCell(tableRow911, "tableRow911tableRow911tableRow911tableRow911tableRow911tableRow911tableRow911tableRow911tableRow911tableRow911tableRow911tableRow911tableRow911tableRow911tableRow911");
	   addTableCell(tableRow1011, "Field 10Field 10Field 10Field 10Field 10Field 10Field 10Field 10Field 10");
	 
	   table.getContent().add(tableRow1 );
	   table.getContent().add(tableRow2 );
	   table.getContent().add(tableRow3 );
	   table.getContent().add(tableRow4 );
	   table.getContent().add(tableRow5 );
	   table.getContent().add(tableRow6 );
	   table.getContent().add(tableRow7 );
	   table.getContent().add(tableRow8 );
	   table.getContent().add(tableRow9 );
	   table.getContent().add(tableRow10);
	   table.getContent().add(tableRow11);
	   table.getContent().add(tableRow21);
	   table.getContent().add(tableRow31);
	   table.getContent().add(tableRow41);
	   table.getContent().add(tableRow51);
	   table.getContent().add(tableRow61);
	   table.getContent().add(tableRow71);
	   table.getContent().add(tableRow81);
	   table.getContent().add(tableRow91);
	   table.getContent().add(tableRow101);
	   table.getContent().add(tableRow111);
	   table.getContent().add(tableRow211);
	   table.getContent().add(tableRow311);
	   table.getContent().add(tableRow411);
	   table.getContent().add(tableRow511);
	   table.getContent().add(tableRow611);
	   table.getContent().add(tableRow711);
	   table.getContent().add(tableRow811);
	   table.getContent().add(tableRow911);
	   table.getContent().add(tableRow1011);
	   
	   
	 //=======================分栏开始=====================================//
		/*<w:sectPr w:rsidR="004D54FE" w:rsidSect="004D54FE">
			<w:pgSz w:w="11906" w:h="16838" />
			<w:pgMar w:top="1440" w:right="1800" w:bottom="1440" w:left="1800" w:header="851" w:footer="992" w:gutter="0" />
			<w:cols w:num="2" w:space="425" />
			<w:docGrid w:type="lines" w:linePitch="312" />
		</w:sectPr>*/
	    org.docx4j.wml.SectPr sectPr= factory.createSectPr(); 
		
	    org.docx4j.wml.SectPr.PgSz pgsz=factory.createSectPrPgSz();//<w:pgSz w:w="11906" w:h="16838" />
		pgsz.setW(BigInteger.valueOf(11906L)); 
		pgsz.setH(BigInteger.valueOf(16838L)); 
		//pgsz.setOrient(STPageOrientation.LANDSCAPE); 
		//pgsz.setCode(BigInteger.valueOf(8L)); 
		sectPr.setPgSz(pgsz); 

		org.docx4j.wml.SectPr.PgMar pgmar= factory.createSectPrPgMar();//<w:pgMar w:top="1440" w:right="1800" w:bottom="1440" w:left="1800" w:header="851" w:footer="992" w:gutter="0" /> 
		pgmar.setTop(BigInteger.valueOf(1440)); 
		pgmar.setRight(BigInteger.valueOf(1440)); 
		pgmar.setBottom(BigInteger.valueOf(1440)); 
		pgmar.setLeft(BigInteger.valueOf(1440)); 
		pgmar.setHeader(BigInteger.valueOf(720)); 
		pgmar.setFooter(BigInteger.valueOf(720)); 
		
		sectPr.setPgMar(pgmar); 

		org.docx4j.wml.CTColumns cols=factory.createCTColumns();//<w:cols w:num="2" w:space="425" />
		cols.setNum(BigInteger.valueOf(2)); 
		cols.setSpace(BigInteger.valueOf(425)); 
		
		sectPr.setCols(cols); 
		
		
		org.docx4j.wml.CTDocGrid grd=factory.createCTDocGrid();//<w:docGrid w:type="lines" w:linePitch="312" />
		grd.setLinePitch(BigInteger.valueOf(312)); 
		grd.setType(STDocGrid.LINES);
		
		sectPr.setDocGrid(grd);
	   
		
		Body body = factory.createBody();
		body.setSectPr(sectPr);
		wordMLPackage.getMainDocumentPart().addObject(body);
		//=======================分栏结束=====================================//
	   
	    wordMLPackage.getMainDocumentPart().addObject(table);
	    
	}
	 
   private  void addTableCell(Tr tableRow, String content) {
	    Tc tableCell = factory.createTc();
	    tableCell.getContent().add(wordMLPackage.getMainDocumentPart().createParagraphOfText(content));
	    tableRow.getContent().add(tableCell);
  }
   
   
}
