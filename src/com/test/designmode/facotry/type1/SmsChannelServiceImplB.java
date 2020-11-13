package com.test.designmode.facotry.type1;

public class SmsChannelServiceImplB implements ISmsChannelService {
	
	public void send(String phoneNo, String content) {
		System.out.println("通过短信渠道B发送短信");
	}
	
	

}
