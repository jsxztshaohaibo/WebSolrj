package com.jsxztshaohaibo.classloader;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class InnerClassLoader {

	public static void main(String[] args) throws Exception {
		//getSystemEnv();
		//getSystemProperties();
		//Class clz = StaticClass.class;
		Class.forName("com.jsxztshaohaibo.classloader.StaticClass",true,StaticClass.class.getClassLoader());
	}
	
	
		
	/**
	 * 获取  操作系统OS  的环境变量
	 */
	private static void getSystemEnv() {
		Map<String, String> envs = System.getenv();
		Set<Entry<String, String>> entrySet = envs.entrySet();
		Iterator<Entry<String, String>> iterator = entrySet.iterator();
		while(iterator.hasNext()){
			Entry<String, String> next = iterator.next();
			System.out.println( next.getKey()+  "    "+next.getValue());
		}
	}
	/**
	 * 获取Java 运行环境的 相关属性
	 */
	private static void getSystemProperties() {
		Properties properties = System.getProperties();
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		Iterator<Entry<Object, Object>> iterator = entrySet.iterator();
		while(iterator.hasNext()){
			Entry<Object, Object> next = iterator.next();
			System.out.println( next.getKey()+  "    "+next.getValue());
		}
		System.out.println("-----");
		ClassLoader classLoader = InnerClassLoader.class.getClassLoader();
		System.out.println("ClassLoader1:    "+classLoader.getClass().getName());
		
		/*ClassLoader classLoader2 = classLoader.getClass().getClassLoader();//没有classloader 了，是bootstrap classloader，
		System.out.println("ClassLoader2:    "+classLoader2.getClass().getName());*/
		
		ClassLoader classLoader2_P = classLoader.getParent();//虽然打印加载不出来SystemClassLoader 的累加器，但是可以打印出其父加载器
		System.out.println("ClassLoader2:    "+classLoader2_P.getClass().getName());
		
		/*ClassLoader classLoader3 = classLoader2.getClass().getClassLoader();
		System.out.println("ClassLoader3:    "+classLoader3);*/
	}

}
