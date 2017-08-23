package org.spring.springboot.service.impl;

import java.util.List;
import java.util.Map;

import org.spring.springboot.dao.RoleMapper;
import org.spring.springboot.dao.UserMapper;
import org.spring.springboot.domain.ShiroUser;
import org.spring.springboot.service.RoleService;
import org.spring.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleMapper roleMapper;
	

	@Override
	public List<String> getUserRoles(Map<String, Object> userinfo) {
		
		try {
			return roleMapper.getUserRolesById(userinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
