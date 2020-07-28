package com.jsxztshaohaibo.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsxztshaohaibo.dao.IBaseDAO;
import com.jsxztshaohaibo.dao.IProductDAO;
import com.jsxztshaohaibo.utils.SolrUtil;
import com.jsxztshaohaibo.vo.ProductVo;

@Repository
public class ProductDAOImpl implements IProductDAO {
	@Autowired //autowired 默认的是使用byType方式注入 
	private SolrServer solrServer1;
	
	@Resource //JDK 自带的 resource 是byType方式注入
	private SolrServer solrServerssss;

	@Autowired
	private IBaseDAO baseDAO;

	@Override
	public List<ProductVo> selectProductListByConditons(String key,String catalog,String price ,String sort){
		
		List<ProductVo> productVos = SolrUtil.select(solrServerssss, key, catalog, price, sort);
		return productVos;
	}

	
	
	@Override
	public List  queryDetails(Map<String, Object> paramsMap) {
		String sql ="select temp.c1,temp.c2,temp.c3,temp.c4,temp.c6 ,temp.c7,  "
				+ "			  GROUP_CONCAT(temp.c5 separator ';')   as c5"
				+ "	 from "
				+ "	( "
				+ "		SELECT t1.mac as c1, t1.calorie as c2,t1.step as c3,t1.heartrate as c4,"
				+ "			   CONCAT(date_format(t2.timestamp, '%Y-%m-%d %T'),'  ',t3.value) as c5,  "
				+ "			   date_format(t1.timestamp , '%Y-%m-%d %T') as c6 ,"
				+ "			    t1.position   ,  t4.value as c7   "
				+ "     from  braceletdata t1   "
				+ "     left JOIN  entryandexit t2 on t1.mac = t2.mac   "
				+ "     left join  sys_status   t3 on t2.status = t3.code"
				+ "		left join  sys_mac      t4 on t1.position = t4.code "
				+ "     order by t1.timestamp desc,t2.timestamp desc  "
				+ "  ) temp group by temp.c1,temp.c2,temp.c3,temp.c4,temp.c6,temp.c7  "
				+ "  order by temp.c6 desc ";
		System.out.println("=======sql======="+sql);
		List queryForList = baseDAO.getJdbcTemplate().queryForList(sql);
		return queryForList;
	}
	
	
	
	public SolrServer getSolrServer1() {
		return solrServer1;
	}

	public void setSolrServer1(SolrServer solrServer1) {
		this.solrServer1 = solrServer1;
	}

	
}
