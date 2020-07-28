package com.jsxztshaohaibo.common.utils.dynamicDataSourceUtils;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**<pre>
 * 数据源切换工具类
 * 1.继承spring 的 AbstractRoutingDataSource 抽象类
 * 2.实现  determineCurrentLookupKey()方法
 * 3.需要定义一个 ThreadLocal 变量
 * 	  private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();
 * 4.需要给ThreadLocal 变量复默认值
 * @author Administrator
 *</pre>
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	//定义一个线程本地变量，每次需要切换到时候，重新设置数据库的key 值，则在这次线程操作过程中， 用的都是这个数据源
	private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();
	
	
	/**<pre>
	 * (模板方法的设计思想)
	 * 重写父类的抽象方法， 返回一个 数据源的 key 值，
	 * 供父类中的  DataSource  determineTargetDataSource()方法调用
	 * protected DataSource determineTargetDataSource() {
	        Assert.notNull(this.resolvedDataSources, "DataSource router not initialized");
	        <b>Object lookupKey = determineCurrentLookupKey();</b>
	        DataSource dataSource = this.resolvedDataSources.get(lookupKey);
	        if (dataSource == null && (this.lenientFallback || lookupKey == null)) {
	            dataSource = this.resolvedDefaultDataSource;
	        }
	        if (dataSource == null) {
	            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
	        }
	        return dataSource;
	    }
	 * </pre>
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		String dataSource = getDataSource();
		return dataSource;
	}
	
	
	
	
	/**
     * 设置数据源
     * @param dataSource
     */
	public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }
	/**
     * 获取数据源
     * @return
     */
	private  static String getDataSource() {
		String dataSource = dataSourceKey.get();
        // 如果没有指定数据源，使用默认数据源
        if (null == dataSource) {
            setDataSourceKey( DataSourceEnum.ORACLE.getName() );
        }
        return dataSourceKey.get();
	}

}

