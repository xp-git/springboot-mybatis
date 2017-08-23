package org.spring.springboot.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.spring.springboot.util.COMMON;


public class ResourceCheckFilter extends AccessControlFilter{

	private String unanUrl = "";
	
	public String getUnanUrl() {
		return unanUrl;
	}

	public void setUnanUrl(String unanUrl) {
		this.unanUrl = unanUrl;
	}
	public ResourceCheckFilter(String unanUrl) {
		this.unanUrl = unanUrl;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest req,
			ServletResponse res, Object arg2) throws Exception {
		//获取当前登录用户
		System.out.println("登录");
		Subject subject = getSubject(req, res);
		String url = getPathWithinApplication(req);
		return subject.isPermitted(url);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest req, ServletResponse res)
			throws Exception {
		HttpServletResponse response = (HttpServletResponse)res;
		HttpServletRequest request = (HttpServletRequest)req;
		String url = COMMON.isEmpty(request.getContextPath())?unanUrl:request.getContextPath()+unanUrl;
		response.sendRedirect(url);
		return false;
	}

}
