package com.test.designmode.TemplateMethod;




public abstract class Abstract冰箱装大象 {
	
	/**
	 * 封装了所有子类共同遵守的算法步骤
	 */
	public  final void  装大象模板方法(){
		if(钩子方法()){
			前戏();
		}else{
			System.out.println("不要前戏.......");
		}
		打开冰箱门();
		装入大象();
		关上冰箱门();
		结尾();
	}
	
	
	
	/**
     * 基本方法，前戏是一个共同的行为，所以声明为private,无需向子类开放
     */
	private final void  前戏(){
		System.out.println("前戏开始。。。");
	}
	/**
     * 抽象的基本方法 
     * 在算法步骤中并不知道具体实现是什么样子的，所以做成了抽象方法，
     * 并且由于我们需要在子类中可见，便于复写而提供具体的实现所以将
     * 权限设置为protected
     */
	protected  abstract void 打开冰箱门();
	protected  abstract void 装入大象();
	protected  abstract void 关上冰箱门();
	
	
	
	private final void  结尾(){
		System.out.println("装大象完毕。。。");
	}
	
	/**
	 * 钩子方法，默认返回true，挂钩子的地方都默认执行
	 * 子类中自己决定要不要执行（重写该方法，改变返回值就行）
	 * @return
	 */
	protected  boolean  钩子方法(){
		return true;
	}
	
	
	
}
