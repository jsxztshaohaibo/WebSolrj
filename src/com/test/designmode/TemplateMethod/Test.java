package com.test.designmode.TemplateMethod;

public class Test {
	
	public static void main(String[] args) {
		Abstract冰箱装大象 张飞版 =  new  张飞版冰箱装大象();
		
		张飞版.装大象模板方法();
		System.out.println("=======换人=============");
		
		Abstract冰箱装大象 诸葛亮版 =  new  诸葛亮版冰箱装大象();
		
		诸葛亮版.装大象模板方法();
	}

}
