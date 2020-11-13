package com.test.callback;

public class ServiceB {
	
	public void doSomething(ICallBack cb){
		System.out.println("ServcieB  doSomething()被调用，调用完会，ServcieB会再调用  ICallBack 的toCallBack()方法");
		
		System.out.println(".");
		try {
			Thread.sleep(1000);
		
		System.out.println("..");
		Thread.sleep(1000);
		System.out.println("...");
		Thread.sleep(1000);
		cb.toCallBack(null);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
