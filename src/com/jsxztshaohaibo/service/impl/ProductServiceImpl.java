package com.jsxztshaohaibo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsxztshaohaibo.dao.IProductDAO;
import com.jsxztshaohaibo.service.IProductService;
import com.jsxztshaohaibo.vo.ProductVo;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private IProductDAO productDAO;

	public List<ProductVo> selectProductListByConditons(String key,String catalog,String price ,String sort){
		return productDAO.selectProductListByConditons(key, catalog, price, sort);
	}
	
	
	public IProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(IProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
}
