package com.jsxztshaohaibo.classloader;

public class StaticClass {
	public static int i;
	
	static{
		System.out.println("static 代码块被执行");
		System.out.println("i="  +i );
	}
	
	public void instanceMethod(){
		System.out.println("instanceMethod");
	}
	
}

 