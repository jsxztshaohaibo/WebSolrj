package com.test.callback;

import java.util.Map;

public class CallBackImplA implements ICallBack {

	
	public void invokeServiceB(){
		ServiceB b = new ServiceB();
		b.doSomething(this);
	}
	
	
	@Override
	public void toCallBack(Map<String, Object> params) {
		System.out.println("CallBackImplA   toCallBack()....");
	}

}
