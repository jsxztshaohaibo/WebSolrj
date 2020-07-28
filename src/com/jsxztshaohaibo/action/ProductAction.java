package com.jsxztshaohaibo.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsxztshaohaibo.service.IProductService;
import com.jsxztshaohaibo.vo.ProductVo;
import com.jsxztshaohaibo.vo.ResultVO;

@Controller
@RequestMapping("/product")
public class ProductAction {

	@Autowired
	private IProductService productService;
	@RequestMapping(value="list.action")
	public String list(String key,String catalog,String price ,String sort ,Model model){
		
		List<ProductVo> productVos = productService.selectProductListByConditons(key, catalog, price, sort);
		
		model.addAttribute("productVos", productVos);
		model.addAttribute("key", key);
		model.addAttribute("catalog", catalog);
		model.addAttribute("price", price);
		model.addAttribute("sort", sort);
		
		return "solrj";
	}
	
	
	
	@RequestMapping(value="/test.action",method={RequestMethod.POST,RequestMethod.POST})
	@ResponseBody
	public JSONObject test(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		String name =req.getParameter("name");
		System.out.println(name);
		JSONObject json  = new JSONObject();
		List<ResultVO> l = new ArrayList<ResultVO>();
		if(name!=null && name.toString().length()>0){
			json.put("flag","OK");
			JSONArray arr = new JSONArray();
			for(int i=0;i<=10;i++){
				arr.add("测试信息"+i+"@#$"+i);
			}
			json.put("data",arr);
		}else{
			json.put("flag","error");
			json.put("data",l);
		}
		return json;
	}
	
	
	
	public IProductService getProductService() {
		return productService;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
}
