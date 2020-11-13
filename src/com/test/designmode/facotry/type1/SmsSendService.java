package com.test.designmode.facotry.type1;



public class SmsSendService{
	
	
	private SmsChannelFactory smsChannelFactory;
	
	private Config config;
	
	
	
	public SmsSendService(){
		smsChannelFactory=new SmsChannelFactory();
	}
	
	public void send(String phoneNo,String content){
		//从配置文件中读取 短信渠道 或者      从数据库里查询获取
		String channelType=config.getChannelType();
		//获取渠道类型对应的服务类
		ISmsChannelService channelService=smsChannelFactory.buildService(channelType);
		//发送短信
		channelService.send(phoneNo,content);
	}

}
