package com.test.designmode.TemplateMethod;


public  class 诸葛亮版冰箱装大象  extends Abstract冰箱装大象 {

	@Override
	protected void 打开冰箱门() {
		System.out.println("我是诸葛亮，我温文儒雅的把冰箱门打开");
		
	}

	@Override
	protected void 装入大象() {
		// TODO Auto-generated method stub
		System.out.println("我是诸葛亮，我温文儒雅的把大象放进去");
		
	}

	@Override
	protected void 关上冰箱门() {
		// TODO Auto-generated method stub
		System.out.println("我是诸葛亮，我温文儒雅的把冰箱门关上");
		
	}
	
}
