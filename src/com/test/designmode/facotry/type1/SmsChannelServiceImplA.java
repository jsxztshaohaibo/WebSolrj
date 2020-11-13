package com.test.designmode.facotry.type1;


public class SmsChannelServiceImplA implements ISmsChannelService {
	
	
	public void send(String phoneNo, String content) {
		System.out.println("通过短信渠道A发送短信");
	}
	

}
