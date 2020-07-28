package com.jsxztshaohaibo.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import com.jsxztshaohaibo.vo.ProductVo;
import com.sun.net.ssl.internal.ssl.Provider;

public class SolrUtil {
		
	@Test
	public void add(){
		try {
			String baseURL = "http://localhost:8080/solr";
			//String baseURL = "http://localhost:8080/solr/collection2";//更换core核
			
			SolrServer solrServer = new HttpSolrServer(baseURL);
			
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", "25");
			doc.addField("product_name", "郝海波");
			doc.addField("product_name","陶瓷 1");
			doc.addField("product_name","陶瓷 2");
			/*doc.addField("product_catalog_name", "家居");*/
			doc.addField("product_catalog_name", "家居1");
			
			//doc.addField("xxxxxxxxx", "XXXXXXXXX");//不能识别这个域
			
			solrServer.add(doc);//添加文档
			solrServer.commit();//提交写入
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void delete(){
		try {
			String baseURL = "http://localhost:8085/solr";
			//String baseURL = "http://localhost:8080/solr/collection2";//更换core核
			
			SolrServer solrServer = new HttpSolrServer(baseURL);
			/*solrServer.deleteByQuery("*:*");
			solrServer.commit();//提交写入*/
			
			solrServer.deleteByQuery("id:25");//1000ms后自动提交
			solrServer.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void update(){
		try {
			String baseURL = "http://localhost:8080/solr";
			//String baseURL = "http://localhost:8080/solr/collection2";//更换core核
			
			SolrServer solrServer = new HttpSolrServer(baseURL);
			
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", "id1");
			doc.addField("name", "郝海波XXX");
			doc.addField("title", "lucene");
			//doc.addField("xxxxxxxxx", "XXXXXXXXX");//不能识别这个域
			
			solrServer.add(doc);//添加文档
			
			solrServer.commit();//提交写入
			
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void query(){
		try {
			String baseURL = "http://localhost:8080/solr";
			
			SolrServer solrServer = new HttpSolrServer(baseURL);
			
			SolrQuery param = new SolrQuery();//查询条件
			//param.setQuery("*:*");
			//param.setQuery("product_catalog_name:家居");
			//param.set("q","product_catalog_name:家居");//同上效果
			
			//查询 “陶瓷”
			//param.set("q","product_name:陶瓷 AND  product_catalog_name:家居 AND product_price:[* TO 50]");////正确写法
			param.set("q","product_name:陶瓷 ");//同上效果
			/**
			 * 下面这种设置多个查询条件的，只有最下面一行设置的条件会生效，所以多个查询条件不是能这样写
			 */
			/*param.set("q","roduct_catalog_name:家居 "); 
			param.set("q","product_price:[* TO 50]"); */
			
			//过滤条件 product_catalog_name=家居,product_price:50
			/**
			 * 下面这种设置多个过滤条件的，只有最下面一行设置的条件会生效，所以多个查询条件不是能这样写
			 */
//			param.set("fq", "product_catalog_name:家居");
//			param.set("fq", "product_price:[* TO 50]"); 
			
			//param.set("fq", "product_catalog_name:家居 AND product_price:[* TO 50]"); //正确写法
			//param.addFilterQuery("product_catalog_name:家居 AND product_price:[* TO 50]");
			param.setFilterQueries("product_catalog_name:家居 AND product_price:[* TO 50]");//正确写法
			
			//按价格排序
			//param.set("sort", "product_price desc");
			param.setSort("product_price", ORDER.desc);
			//param.setSortField("product_price", ORDER.desc);
			
			/*param.addSort("product_price", ORDER.desc);
			param.addSortField("product_price", ORDER.desc);*/
			
			//分页   开始行，每行条数
			param.setStart(0);
			param.setRows(2);
			
			//指定默认域
			param.set("df","product_price");
			
			//只查询指定域
			param.set("fl","id,product_name,product_catalog_name,product_price");
			
			//高亮
			//打开高亮开关
			param.setHighlight(true);
			//设置高亮域
			param.addHighlightField("product_name");
			param.addHighlightField("product_catalog_name");
			
			//String[] highlightFields = param.getHighlightFields();//获取设置高亮的域的集合
			//设置前后缀
			param.setHighlightSimplePre("<span style='color:red;font-weight:bolder'>");
			param.setHighlightSimplePost("</span>");
			
			
			QueryResponse response = solrServer.query(param );
			/**
			 * response 的结构："responseHeader":{}, "response": {},"highlighting": {}
			 * {
				  "responseHeader": {
				    "status": 0,
				    "QTime": 3,
				    "params": {
				      "q": "product_name:陶瓷 AND product_catalog_name:家居",
				      "hl": "true",
				      "hl.simple.post": "</em>",
				      "indent": "true",
				      "hl.fl": "product_name,product_catalog_name",
				      "rows": "2",
				      "wt": "json",
				      "hl.simple.pre": "<em>",
				      "_": "1533273589607"
				    }
				  },
				  "response": {
				    "numFound": 5,
				    "start": 0,
				    "docs": [
				      {
				        "product_catalog_name": "家居",
				        "product_price": 77509.7,
				        "product_name": "贝汉美（BHM） 陶瓷花瓶",
				        "id": "43",
				        "product_picture": "43.jpg",
				        "_version_": 1607675321253888000
				      },
				      {
				        "product_catalog_name": "家居",
				        "product_price": 45,
				        "product_name": "墨斗鱼陶瓷花瓶哑光白色8007",
				        "id": "25",
				        "product_picture": "25.jpg",
				        "_version_": 1607675321205653500
				      }
				    ]
				  },
				  "highlighting": {
				    "25": {
				      "product_name": [
				        "墨斗鱼<em>陶瓷</em>花瓶哑光白色8007"
				      ],
				      "product_catalog_name": [
				        "<em>家居</em>"
				      ]
				    },
				    "43": {
				      "product_name": [
				        "贝汉美（BHM） <em>陶瓷</em>花瓶"
				      ],
				      "product_catalog_name": [
				        "<em>家居</em>"
				      ]
				    }
				  }
				}
			 */
			
			
			/*NamedList<Object> header = response.getHeader();
			 * "responseHeader": {
				    "status": 0,
				    "QTime": 3,
				    "params": {
				      "q": "product_name:陶瓷 AND product_catalog_name:家居",
				      "hl": "true",
				      "hl.simple.post": "</em>",
				      "indent": "true",
				      "hl.fl": "product_name,product_catalog_name",
				      "rows": "2",
				      "wt": "json",
				      "hl.simple.pre": "<em>",
				      "_": "1533273589607"
				    }
				  },
			 * 
			System.out.println(header);*/
			
			Map<String, Map<String, List<String>>> highlightingMap = response.getHighlighting();
			/*"highlighting": {
			    "25": {
			      "product_name": [
			        "墨斗鱼<em>陶瓷</em>花瓶哑光白色8007"
			      ],
			      "product_catalog_name": [
			        "<em>家居</em>"
			      ]
			    },
			    "43": {
			      "product_name": [
			        "贝汉美（BHM） <em>陶瓷</em>花瓶"
			      ],
			      "product_catalog_name": [
			        "<em>家居</em>"
			      ]
			    }
			  }*/
			
			SolrDocumentList docs = response.getResults();//文 档结果集
			/*
			 * "response": {
				    "numFound": 5,
				    "start": 0,
				    "docs": [
				      {
				        "product_catalog_name": "家居",
				        "product_price": 77509.7,
				        "product_name": "贝汉美（BHM） 陶瓷花瓶",
				        "id": "43",
				        "product_picture": "43.jpg",
				        "_version_": 1607675321253888000
				      },
				      {
				        "product_catalog_name": "家居",
				        "product_price": 45,
				        "product_name": "墨斗鱼陶瓷花瓶哑光白色8007",
				        "id": "25",
				        "product_picture": "25.jpg",
				        "_version_": 1607675321205653500
				      }
				    ]
				  },
			 */
			long numFound = docs.getNumFound();//返回的总结果数
			System.out.println("返回的总结果数："+numFound);
			/*documnet:{
		        "product_catalog_name": "花瓶花艺",
		        "product_price": 12,
		        "product_name": "米子家居 ins北欧小清新透明玻璃花瓶可插花绿植装饰品摆件餐桌办公桌装饰两件",
		        "id": "2",
		        "product_picture": "2.jpg",
		        "_version_": 1607675321179439000
		    }*/
			for (SolrDocument solrDocument : docs) {
				//System.out.println(solrDocument.get("product_catalog_name"));
				System.out.println("------------------"+solrDocument.getFieldValue("id")+"------------------");
				System.out.println(solrDocument.getFieldValue("product_name"));
				System.out.println(solrDocument.getFieldValue("product_catalog_name"));
				System.out.println(solrDocument.getFieldValue("product_price"));
				System.out.println(solrDocument.getFieldValue("product_picture"));
				System.out.println(solrDocument.getFieldValue("product_description"));
				
				
				//String[] highlightFields = param.getHighlightFields();//获取设置高亮的域的集合
				Map<String, List<String>> map = highlightingMap.get(solrDocument.getFieldValue("id"));
				/*map的格式
				 * "25": {
			      "product_name": [
			        "墨斗鱼<em>陶瓷</em>花瓶哑光白色8007"
			      ],
			      "product_catalog_name": [
			        "<em>家居</em>"
			      ]
			    }
				 */ 
				Set<Entry<String, List<String>>> entrySet = map.entrySet();
				Iterator<Entry<String, List<String>>> iterator = entrySet.iterator();
				while(iterator.hasNext()){
					Entry<String, List<String>> entry = iterator.next();
					String key_field = entry.getKey();					
					List<String> value = entry.getValue();
					for (String string : value) {
						System.out.println("高亮的域：["+key_field +"] ;   对应的值:["+string+"]");
						System.out.println("----------------------");
					}
				}
			}
			
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("返回的总结果数："+numFound);
			
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询信息列表
	 * @param solrServer
	 * @param key
	 * @param catalog
	 * @param price
	 * @param sort
	 * @return
	 */
	public static List<ProductVo> select(SolrServer solrServer ,String key,String catalog,String price ,String sort){
		try {
			SolrQuery param = new SolrQuery();//查询条件
			param.set("q",key);//同上效果
			String filterQueryCatalog="";
			if(catalog !=null && catalog.length()>0){
				filterQueryCatalog="product_catalog_name:"+catalog;
			}
			String filterQueryPrice="";
			if(price!=null && price.length()>0){
				String[] strArr = price.split("-");
				filterQueryPrice="product_price:["+strArr[0]+" TO "+strArr[1]+"]";
			}
			param.addFilterQuery(filterQueryCatalog,filterQueryPrice);
			if(sort!=null && sort.length()>0){
				if("1".equals(sort)){
					param.setSort("product_price", ORDER.desc);
				}else{
					param.setSort("product_price", ORDER.asc);
				}
			}
			param.setStart(0);
			param.setRows(20);
			
			//指定默认域
			param.set("df","product_keywords");
			
			//只查询指定域
			param.set("fl","id,product_name,product_catalog_name,product_price,product_picture,product_description");
			
			//高亮
			//打开高亮开关
			param.setHighlight(true);
			//设置高亮域
			param.addHighlightField("product_name");
			param.addHighlightField("product_catalog_name");
			
			//String[] highlightFields = param.getHighlightFields();//获取设置高亮的域的集合
			//设置前后缀
			param.setHighlightSimplePre("<span style='color:red;font-weight:bolder'>");
			param.setHighlightSimplePost("</span>");
			
			QueryResponse response = solrServer.query(param );
			
			
			//String[] highlightFields = param.getHighlightFields();//获取设置高亮的域的集合
			//获取高亮显示的结果集
			Map<String, Map<String, List<String>>> highlightingMap = response.getHighlighting();
			SolrDocumentList docs = response.getResults();//文 档结果集
			long numFound = docs.getNumFound();//返回的总结果数
			System.out.println("返回的总结果数："+numFound);
			List<ProductVo> returnList = new ArrayList<ProductVo>();
			
			for (SolrDocument solrDocument : docs) {

				ProductVo pvo = new ProductVo();
				pvo.setPid((String)solrDocument.getFieldValue("id"));
				//pvo.setName((String)solrDocument.getFieldValue("product_name"));
				pvo.setPrice((float)solrDocument.getFieldValue("product_price"));
				pvo.setCatalog_name((String)solrDocument.getFieldValue("product_catalog_name"));
				pvo.setDescription((String)solrDocument.getFieldValue("product_description"));
				pvo.setPicture((String)solrDocument.getFieldValue("product_picture"));
				
				//根据ID获取对应的Map信息
				Map<String, List<String>> map = highlightingMap.get(solrDocument.getFieldValue("id"));
				/*map的格式
				 * "25": {
			      "product_name": [
			        "墨斗鱼<em>陶瓷</em>花瓶哑光白色8007"
			      ],
			      "product_catalog_name": [
			        "<em>家居</em>"
			      ]
			    }
				 */ 
				List<String> l = map.get("product_name");//获取高亮的域的集合
				//因为查询的是product_keys 域 ，有可能product_name 域中没有陶瓷，但是product_description 中有，所以要做一下判断
				if(l!=null && l.size()>0){
					pvo.setName(l.get(0));//使用高亮结果集中的域的信息
				}else{
					pvo.setName((String)solrDocument.getFieldValue("product_name"));
					String fv =(String)solrDocument.getFieldValue("id");
					System.out.println(fv);
				}
				
				returnList.add(pvo);
			}
			
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("返回的总结果数："+numFound);
			return returnList;
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
