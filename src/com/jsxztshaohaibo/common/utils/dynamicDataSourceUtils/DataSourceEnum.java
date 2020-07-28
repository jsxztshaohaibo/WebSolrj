package com.jsxztshaohaibo.common.utils.dynamicDataSourceUtils;

public enum DataSourceEnum {
	
	ORACLE("oracle",true),
	MYSQL("mySql",false);
	
    private final String name;// 数据源名称
    private final boolean isDefault; // 是否是默认数据源
 
    private DataSourceEnum(String name, boolean isDefault) {
        this.name = name;
        this.isDefault = isDefault;
    }
    
    public String getName() {
		return name;
	}
	public boolean isDefault() {
		return isDefault;
	}
	@Override
	public String toString() {
		
		return " Enum DataSourceEnum [name=" + name + ", isDefault=" + isDefault + "]";
	}

}
