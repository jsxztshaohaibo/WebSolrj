package com.test.designmode.facotry.type2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
public class Config {
	
	
	@Value("${SmsChannelType}")
	//@Value("CHANNEL_B")
	private String channelType;
	
	
	/**
	 * @return the channelType
	 */
	public String getChannelType() {
		return channelType;
	}
	/**
	 * @param channelType the channelType to set
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	
}
