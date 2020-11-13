package com.test.designmode.facotry.type2;

import org.springframework.stereotype.Component;

@Component
public class SmsChannelServiceImplB implements ISmsChannelService {
	
	public void send(String phoneNo, String content) {
		System.out.println("通过短信渠道B发送短信");
	}
	
	
	//关键：增加 getChannelType() 实现
	public String getChannelType() {
		return "CHANNEL_B";
	}

}
