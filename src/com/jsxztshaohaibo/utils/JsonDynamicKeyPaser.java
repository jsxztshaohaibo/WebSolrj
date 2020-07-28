package com.jsxztshaohaibo.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class JsonDynamicKeyPaser {
	public static void  dynamicKeyParser(String jsonStr){
		List<Object> l = new ArrayList<Object>();
		String type="breceletData";
		JSONObject jsonObj = JSONObject.fromObject(jsonStr); 
		Iterator keys = jsonObj.keys();
		while(keys.hasNext()){
			String key = (String)keys.next();
			Object object = jsonObj.get(key);
			if(object instanceof String){//判断  Key 是不是正确的数据
				if(! type.equals(object)){
					/*res.setType(String.valueOf(Constant.RESULT_TYPE_PARAMEOOR));
					res.setMsg("上送参数错误，期望是 ["+type+"] ，却得到了"+object);
					res.setTimestamp(System.currentTimeMillis());
					return res;*/
				}
			}else if(object instanceof JSONObject){
				JSONObject json = ((JSONObject)object) ;
				Object objVO = (Object)JSONObject.toBean(json, Object.class);
				
				l.add(objVO);
			}else if(object instanceof JSONArray){ 
				
				JSONArray json = (JSONArray)object;
				l = JSONArray.toList(json, Object.class);
				
			}
		}
	}
	
	
	public static void main(String[] args) {
		String jsonStr="{"
				+ "\"type\":\"breceletData\","
				+ "\"C2:EB:18:BD:18:AA\":"
						+ "["
						+ "{"
						+ "\"model\":\"HW330\","
						+ "\"node\":\"C2:EB:18:BD:18:AA\","
						+ "\"position\":\"CC:1B:E0:E0:97:B0\","
						+ "\"rssi\":\"-70\","
						+ "\"routerMac\":\"CC:1B:E0:E0:97:B0\","
						+ "\"step\":\"12586\","
						+ "\"heartrate\":\"0\","
						+ "\"battery\":\"0\","
						+ "\"calorie\":\"123\","
						+ "\"timestamp\":\"1451997924\""
						+ "}"
						+ "],"
				+ "\"C2:EB:18:BD:18:BB\":"
						+ "{"
						+ "\"model\":\"HW330\","
						+ "\"node\":\"C2:EB:18:BD:18:BB\","
						+ "\"position\":\"CC:1B:E0:E0:97:B0\","
						+ "\"rssi\":\"-70\","
						+ "\"routerMac\":\"CC:1 B:E0:E0:97:B0\","
						+ "\"step\":\"1 2586\","
						+ "\"heartrate\":\"0\","
						+ "\"battery\":\"0\","
						+ "\"calorie\":\"1 23\","
						+ "\"timestamp\":\"1508510388556\""
						+ "}"
			+ "}";
		JsonDynamicKeyPaser.dynamicKeyParser(jsonStr);
		
	}
}