package com.test.designmode.facotry.type1;

import java.util.HashMap;
import java.util.Map;

/***
 * 这个工厂类模式的缺点：
 * 如果我要增加一个短信渠道C，无需再次更改 SmsSendService 类。
	1.只需要增加一个类 SmsChannelServiceImplC 实现 SmsChannelService 接口，
	2.然后在工厂类 SmsChannelFactory 中增加一行初始化 SmsChannelServiceImplC 的代码即可。

 * @author Administrator
 *
 */
public class SmsChannelFactory {
	
	
	private Map<String,ISmsChannelService> serviceMap;

	//初始化工厂，将所有的短信渠道Service放入Map中
	public SmsChannelFactory(){
		//渠道类型为 key , 对应的服务类为value ：
		serviceMap=new HashMap<String, ISmsChannelService>(2);
		serviceMap.put("CHANNEL_A",new SmsChannelServiceImplA());
		serviceMap.put("CHANNEL_B",new SmsChannelServiceImplB());
	}

	//根据短信渠道类型获得对应渠道的Service
	public ISmsChannelService buildService(String channelType){
		return serviceMap.get(channelType);
	}
}
