package com.jsxztshaohaibo;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;


/**
 * 在纯纯的servlet中获取spring 管理的bean的方法<br/>
 * <b>
 * ServletContext sc = config.getServletContext(); <br/>
 * XmlWebApplicationContext cxt = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);<br/>
 *    
 * </b>
 * @author Administrator
 *
 */
public class TestServlet extends HttpServlet {
	
	SolrServer solrServer1;
	
	private static final long serialVersionUID = 1L;
       
  
    public TestServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			solrServer1.ping();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		ServletContext sc = config.getServletContext(); 
		XmlWebApplicationContext cxt = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);
	        
        if(cxt != null && cxt.getBean("solrServer1") != null && solrServer1 == null){
        	solrServer1= cxt.getBean("solrServer1" ,SolrServer.class);
        }
	}

	

}