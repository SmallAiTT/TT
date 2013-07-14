package com.tt.pvl.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.tt.pub.map.TCache;

public class ViewFilter implements Filter {
	private static Log log = Logs.getLog(ViewFilter.class);


	
	private static List<String> passUrlList = new ArrayList<String>();
	private static String basePath;
	private static boolean fire = false;
	static{
		passUrlList.add("Logout.jsp");
		passUrlList.add("Login.jsp");
	}

	/** 退出的界面 */
	private String logoutPage = "Logout.jsp";

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		if(!fire){
			chain.doFilter(req, resp);
			return;
		}
		if(!passUrlList.contains(logoutPage)) passUrlList.add(logoutPage);
		HttpServletRequest hreq = (HttpServletRequest) req;
		HttpServletResponse hresp = (HttpServletResponse) resp;
		if(Strings.isBlank(basePath)) initBasePath(hreq);
		StringBuffer url = hreq.getRequestURL();
		String action = url.substring(basePath.length());
		if(passUrlList.contains(action)) {
			chain.doFilter(req, resp);
		}else if(url.length() == basePath.length() 
				|| url.length() == basePath.length() - 1){
			chain.doFilter(req, resp);
		}else if (TCache.me().getLoginInfo() == null) {
			log.debug("Please Login!");
			hresp.sendRedirect(basePath + logoutPage);
		}else{
			chain.doFilter(req, resp);
		}
	}
	
	private void initBasePath(HttpServletRequest hreq){
		String path = hreq.getContextPath();
		basePath = hreq.getScheme() + "://"
				+ hreq.getServerName() + ":" + hreq.getServerPort()
				+ path + "/";
	}

	public void init(FilterConfig config) throws ServletException {
		//add  by zheng.xiaojun 2012-05-21
		String page = config.getInitParameter("logoutPage");
		if(!Strings.isBlank(page)){
			logoutPage = page;
		}
		String fireStr = config.getInitParameter("fire");
		fire = "true".equals(fireStr);
	}

}
