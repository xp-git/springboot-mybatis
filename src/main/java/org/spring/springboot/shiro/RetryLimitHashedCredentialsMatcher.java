package org.spring.springboot.shiro;


import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

/**
 * 自定义密码校验,在本方法中设置最大出错次数
 * */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher{
	
	private int errorTimes ;
	private String hashAlgorithmName ;
	private int hashIterations ;

	private Cache<String, AtomicInteger> passwordRetryCache;
	
	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager,
			int errorTimes,String hashAlgorithmName,int hashIterations) {  
		passwordRetryCache = cacheManager.getCache("passwordRetryCache"); 
		this.errorTimes = errorTimes;
		System.out.println("errorTimes ==="+errorTimes);
		this.hashAlgorithmName = hashAlgorithmName;
		this.hashIterations = hashIterations ;
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
	
	public boolean doCredentialsMatch(AuthenticationToken token,AuthenticationInfo info){
		String username = (String) token.getPrincipal();  
		 AtomicInteger retryCount = passwordRetryCache.get(username);  
		 if(retryCount == null){
			 retryCount = new AtomicInteger(0); 
			 System.out.println("errorTimes ==="+retryCount+"===="+username+"===");
			 passwordRetryCache.put(username, retryCount);
		 }
		 //重复输错密码5次时需要等十分钟后重试
		 int total = 0 ;
		 if(errorTimes == 0){
			 total = 5;
		 }else{
			 total = errorTimes;
		 }
		 System.out.println("errorTimes222 ==="+retryCount+"===="+username+"===");
		 if (retryCount.incrementAndGet() > total) {  
			 throw new ExcessiveAttemptsException();  
		 }
		 boolean matches = super.doCredentialsMatch(token, info);  
		 if(matches){
			 passwordRetryCache.remove(username);
		 }
		 return matches;
	}
	
}
