package cn;import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;



public class Test_Map {
	Logger logger = Logger.getLogger(Test_Map.class);
	
	
	public Test_Map(){
		
	}
	/**
	 * @param args
	 */
	@Test
	public   void test(){
		try {
			Class.forName("Test_Map").newInstance();//只能调用无参的构造器，如果没有无参的构造方法，则会报错 java.lang.InstantiationException。
			//DriverManager.getConnection(url, user, password);
		    long s1 = System.currentTimeMillis();
			Map<String, String> map = new HashMap<String, String>(100);
			long s2 = System.currentTimeMillis();
			logger.info("初始化容量100，耗时 ： " + (s2 - s1));
			
			
			Class<?> mapType = map.getClass();
		    Method capacity = mapType.getDeclaredMethod("capacity");
		    capacity.setAccessible(true);
		    logger.info("capacity : " + capacity.invoke(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
