package com.test.designmode.TemplateMethod;


public  class 张飞版冰箱装大象  extends Abstract冰箱装大象{
	@Override
	protected void 打开冰箱门() {
		System.out.println("我是张飞，我叫喊着粗鲁的把冰箱门打开");
		
	}

	@Override
	protected void 装入大象() {
		// TODO Auto-generated method stub
		System.out.println("我是张飞，我叫喊着粗鲁的把大象放进去");
		
	}

	@Override
	protected void 关上冰箱门() {
		// TODO Auto-generated method stub
		System.out.println("我是张飞，我叫喊着粗鲁的把冰箱门关上");
	}

	/* (non-Javadoc)
	 * @see com.test.designmode.TemplateMethod.Abstract冰箱装大象#钩子方法()
	 * 
	 * 张飞比较急躁，不要前戏直接就开始装
	 */
	@Override
	protected boolean 钩子方法() {
		return false;
	}
	
}
