package com.test.designmode.facotry.type2;


public interface ISmsChannelService{
	//发送短信
	void send(String phoneNo,String content);
	
	
	//增加 getChannelType() 方法，这一步很关键。
	String getChannelType();
}
