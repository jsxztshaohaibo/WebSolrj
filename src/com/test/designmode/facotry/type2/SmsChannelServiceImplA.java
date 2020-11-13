package com.test.designmode.facotry.type2;

import org.springframework.stereotype.Component;

@Component
public class SmsChannelServiceImplA implements ISmsChannelService {
	
	
	public void send(String phoneNo, String content) {
		System.out.println("通过短信渠道A发送短信");
	}
	
	
	//关键：增加 getChannelType() 实现
		public String getChannelType() {
			return "CHANNEL_A";
		}

}
