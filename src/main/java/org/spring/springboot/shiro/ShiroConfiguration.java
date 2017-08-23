package org.spring.springboot.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * SHIRO核心配置信息
 * shiroFilter过滤器中新增拦截
 * */
/**
 * @author  Carlyle
 * @date 	Jul 24, 2017 6:10:01 PM
 * @description
 * 
 */
@Configuration
@ConfigurationProperties(ignoreUnknownFields = false,prefix = "shiro.login")
@PropertySource("classpath:config/shiro.properties")
public class ShiroConfiguration {
	private Logger log_ = Logger.getLogger(ShiroConfiguration.class.getName());
	
	private String successUrl ;
	private String loginUrl ;
	private String unauthorizedUrl ;
	private String cacheFilePath ;
	private int errorTimes ;
	private String hashAlgorithmName ;
	private int hashIterations ;
	
	public String getSuccessUrl() {
		return successUrl;
	}
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}
	public String getLoginUrl() {
		return loginUrl;
	}
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}
	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}
	public String getCacheFilePath() {
		return cacheFilePath;
	}
	public void setCacheFilePath(String cacheFilePath) {
		this.cacheFilePath = cacheFilePath;
	}
	public int getErrorTimes() {
		return errorTimes;
	}
	public void setErrorTimes(int errorTimes) {
		this.errorTimes = errorTimes;
	}
	public String getHashAlgorithmName() {
		return hashAlgorithmName;
	}
	public void setHashAlgorithmName(String hashAlgorithmName) {
		this.hashAlgorithmName = hashAlgorithmName;
	}
	public int getHashIterations() {
		return hashIterations;
	}
	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}
	/**
	 * http://www.cnblogs.com/ginponson/p/6217057.html
	 * 修复Spring Boot整合shiro出现UnavailableSecurityManagerException 问题
	 * 此处设置相当于在web.xml中增加filter
	 * */
	@Bean
	public FilterRegistrationBean delegatingFilterProxy(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		DelegatingFilterProxy proxy = new DelegatingFilterProxy();
		proxy.setTargetFilterLifecycle(true);
		proxy.setTargetBeanName("shiroFilter");
		filterRegistrationBean.setFilter(proxy);
		return filterRegistrationBean;
	}

	@Bean(name="resourceCheckFilter")
	public ResourceCheckFilter  resourceCheckFilter(){
		ResourceCheckFilter resourceCheckFilter = new ResourceCheckFilter(unauthorizedUrl);
		return resourceCheckFilter;
	}
	@Bean(name="forceLogoutFilter")
	public ForceLogoutFilter  forceLogoutFilter(){
		ForceLogoutFilter forceLogoutFilter = new ForceLogoutFilter();
		return forceLogoutFilter;
	}
	//	SHIRO核心拦截器配置
	@Bean(name="shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
		log_.info("successUrl:"+successUrl+"\t loginUrl:"+loginUrl+"\t unauthorizedUrl:"+unauthorizedUrl);
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		bean.setSuccessUrl(successUrl);
		bean.setLoginUrl(loginUrl);
		bean.setUnauthorizedUrl(unauthorizedUrl);
		//配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/admin/**", "authc,roles[admin]");
        filterChainDefinitionMap.put("/userPage", "authc,roles[user]");
        filterChainDefinitionMap.put("/testView", "authc,resourceCheckFilter");
        filterChainDefinitionMap.put("/guestView", "authc,resourceCheckFilter,roles[admin]");
        filterChainDefinitionMap.put("/test", "anon");
        filterChainDefinitionMap.put("/", "anon");
       
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/*", "forceLogoutFilter");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return bean ;
	}
	//配置SHIRO核心安全事务管理器
	@Bean(name="securityManager")
	public DefaultWebSecurityManager securityManager(@Qualifier("myShiroRealm") MyShiroRealm myShiroRealm){
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(myShiroRealm);
		defaultWebSecurityManager.setSessionManager(sessionManager());
		return defaultWebSecurityManager;
	}
	//配置自定义的权限登录器
	@Bean(name="myShiroRealm")
	public MyShiroRealm myShiroRealm(@Qualifier("credentialsMatcher") RetryLimitHashedCredentialsMatcher credentialsMatcher){
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(credentialsMatcher);
		return myShiroRealm;
	}
	//配置密码对比
	@Bean(name="credentialsMatcher")
	public RetryLimitHashedCredentialsMatcher credentialsMatcher(@Qualifier("cacheManager") EhCacheManager cacheManager){
		RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManager,errorTimes,hashAlgorithmName,hashIterations);
		return credentialsMatcher;
	}
	//配置缓存管理
	@Bean(name="cacheManager")
	public EhCacheManager cacheManager(){
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile(cacheFilePath);
		return cacheManager;
	}
	@Bean(name="sessionDao")
	public SessionDAO sessionDao(){
		
		ShiroSessionDao se = new ShiroSessionDao();
		//se.setSessionIdGenerator(sessionIdGenerator);
		return se;
		
	}
	 /**
     * shiro session的管理
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDao());
        return sessionManager;
    }
}
