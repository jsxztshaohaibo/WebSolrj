package com.jsxztshaohaibo.service;

import java.util.List;

import com.jsxztshaohaibo.vo.ProductVo;

public interface IProductService {
	public List<ProductVo> selectProductListByConditons(String key,String catalog,String price ,String sort);
}
