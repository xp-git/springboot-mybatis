package org.spring.springboot.service;

import java.util.List;
import java.util.Map;

import org.spring.springboot.domain.ShiroUser;

public interface  UserService {

	
	
	public ShiroUser checkUserByUsername(String account);

	public List<String> findPermissionByUserInfo(Map<String, Object> userInfo);

}
