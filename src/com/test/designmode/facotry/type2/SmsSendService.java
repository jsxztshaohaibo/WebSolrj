package com.test.designmode.facotry.type2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SmsSendService {
	
	@Autowired
	private SmsChannelFactory smsChannelFactory;
	
	
	@Autowired
	private Config config;
	
	
	public void send(String phoneNo,String content){
		//从配置中读取短信渠道类型
		String channelType=config.getChannelType();
		
		//String channelType="";
		//构建渠道类型对应的服务类
		ISmsChannelService channelService=smsChannelFactory.buildService(channelType);
		//发送短信
		channelService.send(phoneNo,content);
	}
}
