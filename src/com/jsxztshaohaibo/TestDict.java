package com.jsxztshaohaibo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

/**
 * 约束条件，k\v 中不能带有 "="、 ";" 和 "\n"，如果有,需要输入时替换成其他字符 易冲突的字符；
 *该demo默认已经都转换完毕
 * @author Administrator
 *
 */
public class TestDict {
	@Test
	public void testName() throws Exception {
		//String s = "k1=v1;k2=v2;k3=v3\ndfsdfndfa\nk11=v11;k22=v22;k33=v33\nk111=";
		//String s = "k1=v1";
		String s = "k1";
		HashMap[][] load = this.load(s);
		String store = this.store(load);
		System.out.println("["+store+"]");
	}
	/**
	 * 把数组保存到字符串中
	 * @return
	 */
	public String store(HashMap[][] mArr ){
		if(mArr ==null || mArr.length<=0){
			return null;
		}
		StringBuilder res = new StringBuilder();
		int arr_size = mArr.length;
		for(int i =0;i< arr_size;i++){
			HashMap [] hm  =  mArr[i];//取每行数据
			int dict_size = hm.length;
			
			for(int j =0;j< dict_size;j++){
				HashMap hmTmp = hm[j];//取每列数据
				Set keySet = hmTmp.keySet();
				Iterator iterator = keySet.iterator();
				while(iterator.hasNext()){
					String key = (String)iterator.next();
					String value = hmTmp.get(key)==null ?"" :(String)hmTmp.get(key);
					res.append(key).append("=").append(value.replace("\n","\\n"));
				}
				if(j!=dict_size-1){
					res.append(";");
				}
			}
			if(i!=arr_size-1){
				res.append("\\n");
			}
		}
		
		return res.toString();
	}
	 /**
	  *把字符串中的内容读取为字典数组
	  * @param text
	  * @return
	  */
	public  HashMap[][] load( String text){
		if(text==null|| text.length()<=0){
			return null;
		}
		String [] strArrs = text.split("\n");//拆分成的数组元素集合
		int arr_size = strArrs.length;
		
		HashMap[][] mArr = new HashMap[arr_size][];
		
		for(int i =0;i<arr_size;i++){
			String arr = strArrs[i];//单个数组
			String [] dicts = arr.split(";");//拆分的字典元素集合
			
			int dicts_size = dicts.length;
			
			HashMap [] hm = new HashMap[dicts_size];
			for(int j =0;j<dicts_size;j++){
				String dict = dicts[j];//单个字典的key \value
				String [] kv = dict.split("=");//k v 数组
				if(kv.length<2){// 无 V
					String k = kv[0];
					HashMap hmTmp = new HashMap();
					hmTmp.put(k, null);
					
					hm[j] = hmTmp;
				}else{
					String k = kv[0];
					String v = kv[1];
					HashMap hmTmp = new HashMap();
					hmTmp.put(k, v);
					
					hm[j] = hmTmp;
				}
			}
			mArr[i] = hm ;
		}
		return mArr;
	}
}
