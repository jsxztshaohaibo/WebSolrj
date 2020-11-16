package cn; 
/*import org.apache.log4j.Logger;

*//***
 * 计算一次动作    从接收到请求到返回相应     使用的时间  的过滤器
 * @author Administrator
 *
 *//*
public class MoniterFilter implements Filter {
	
	
	Logger logger = Logger.getLogger(MoniterFilter.class);
	
	
	@Override 
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		long start = System.currentTimeMillis(); 
		HttpServletRequest httpRequest = (HttpServletRequest) request; 
		HttpServletResponse httpResponse = (HttpServletResponse) response; 
		String uri = httpRequest.getRequestURI(); 
		String params = getQueryString(httpRequest); 
		try { 
			chain.doFilter(httpRequest, httpResponse); 
		} finally { 
				long cost = System.currentTimeMillis() - start; 
				logger.info("access url [{}{}], cost time [{}] ms )", uri, params, cost); 
		}	
	private String getQueryString(HttpServletRequest req) {
		StringBuilder buffer = new StringBuilder("?");
		Enumeration emParams = req.getParameterNames();
		try {
			while (emParams.hasMoreElements()) {
				String sParam = emParams.nextElement();
				String sValues = req.getParameter(sParam);
				buffer.append(sParam).append("=").append(sValues).append("&");
			}
			return buffer.substring(0, buffer.length() - 1);
		} catch (Exception e) {
			logger.error("get post arguments error", buffer.toString());
		}
		return "";
	}
}*/