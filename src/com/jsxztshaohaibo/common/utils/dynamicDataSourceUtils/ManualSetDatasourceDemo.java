package com.jsxztshaohaibo.common.utils.dynamicDataSourceUtils;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 手动设置数据源
 * @author Administrator
 *
 */
public class ManualSetDatasourceDemo {
	
	 public static void main(String[] args) {
	        //初始化ApplicationContext
	        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-core.xml");
	        
	        Object mySqlMapper = applicationContext.getBean(Object.class);

	        Object OracleMapper = applicationContext.getBean(Object.class);
	        
/**********************************设置数据源为MySql *********************/
	        DynamicDataSource.setDataSourceKey(DataSourceEnum.MYSQL.getName());
/**********************************设置数据源为MySql *********************/
	        //mySqlMapper.getList();
	        
	        
 /**********************************设置数据源为ORACLE*********************/
	        DynamicDataSource.setDataSourceKey(DataSourceEnum.ORACLE.getName());
/**********************************设置数据源为ORACLE*********************/
	       // OracleMapper.getList();
	 }	 
}
