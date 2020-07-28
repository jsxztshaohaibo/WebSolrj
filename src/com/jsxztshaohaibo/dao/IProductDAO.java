package com.jsxztshaohaibo.dao;

import java.util.List;
import java.util.Map;

import com.jsxztshaohaibo.vo.ProductVo;

public interface IProductDAO {
	
	public List<ProductVo> selectProductListByConditons(String key,String catalog,String price ,String sort);
	public List  queryDetails(Map<String, Object> paramsMap);
}
