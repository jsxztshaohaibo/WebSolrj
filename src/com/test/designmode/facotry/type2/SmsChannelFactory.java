package com.test.designmode.facotry.type2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class SmsChannelFactory {
	
	private Map<String,ISmsChannelService> serviceMap;
	
	/**通过spring将所有ISmsChannelService 接口的实现类注入到serviceList 中**/
	@Autowired
	private List<ISmsChannelService> serviceList;

	/**通过 @PostConstruct 注解，在 SmsChannelFactory 实例化后，来初始化 serviceMap */
	@PostConstruct
	private void init(){
		if(CollectionUtils.isEmpty(serviceList)){
			return ;
		}
		serviceMap=new HashMap<String, ISmsChannelService>(serviceList.size());
		//将 serviceList 转换为 serviceMap
		for (ISmsChannelService channelService : serviceList) {
			
			String channelType=channelService.getChannelType();
			//重复性校验，避免不同实现类的 getChannelType() 方法返回同一个值。
			if(serviceMap.get(channelType)!=null){
				throw new RuntimeException("同一个短信渠道只能有一个实现类");
			}
			/***渠道类型为 key , 对应的服务类为  value ：
			与“优化代码1”中的通过手工设置“CHANNEL_A"、"CHANNEL_B"相比，
			这种方式更加自动化，后续在增加“CHANNEL_C"无需再改此处代码*/
			serviceMap.put(channelType,channelService);
		}
	}

	//根据渠道类型获取对应短信渠道的Service
	public ISmsChannelService buildService(String channelType){
		return serviceMap.get(channelType);
	}
}
