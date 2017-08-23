package org.spring.springboot.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.catalina.authenticator.Constants;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

public class ForceLogoutFilter extends AccessControlFilter{

	
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {  
        Session session = getSubject(request, response).getSession(false);  
        if(session == null) {  
            return true;  
        }  
        return session.getAttribute("sessionlogout") == null;  
    }  
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {  
        try {  
        	System.out.println("强制退出， 过滤器");
            getSubject(request, response).logout();//强制退出  
        } catch (Exception e) {/*ignore exception*/}  
        String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=1";  
        WebUtils.issueRedirect(request, response, loginUrl);  
        return false;  
    }  
}
