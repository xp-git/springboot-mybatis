package org.spring.springboot.service.impl;

import java.util.List;
import java.util.Map;

import org.spring.springboot.dao.UserMapper;
import org.spring.springboot.domain.ShiroUser;
import org.spring.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	
	@Override
	public ShiroUser checkUserByUsername(String account) {
		
		return userMapper.checkUserByUserName(account);
	}

	@Override
	public List<String> findPermissionByUserInfo(Map<String, Object> userInfo) {
		
		return userMapper.findPermissionByUserInfo(userInfo);
	}

}
