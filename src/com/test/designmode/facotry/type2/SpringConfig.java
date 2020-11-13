package com.test.designmode.facotry.type2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



//@Configuration
public class SpringConfig {
	
	@Bean
	public SmsSendService smsSendService(){
		return new SmsSendService();
	} 
	@Bean
	public Config config(){
		return new Config();
	} 
	
	@Bean
	public SmsChannelFactory smsChannelFactory(){
		return new SmsChannelFactory();
	} 
	
	
	@Bean
	public SmsChannelServiceImplA smsChannelServiceImplA(){
		return new SmsChannelServiceImplA();
	} 
	@Bean
	public SmsChannelServiceImplB smsChannelServiceImplB(){
		return new SmsChannelServiceImplB();
	} 
	
	@Bean
	public  List<ISmsChannelService>  serviceList(){
		List<ISmsChannelService> list = new ArrayList<ISmsChannelService>();
		list.add(smsChannelServiceImplA());
		list.add(smsChannelServiceImplB());
		return list;
	} 
	
}
