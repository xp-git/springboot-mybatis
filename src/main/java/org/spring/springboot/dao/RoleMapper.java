package org.spring.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.spring.springboot.domain.ShiroUser;

public interface RoleMapper {

	@Select("select * from shiro_user where username = #{account} ")
	ShiroUser checkUserByUserName(String account );

	@Select("select sr.roleName from shiro_role sr "
			+ "left join shiro_role_user sru on sru.roleid = sr.id "
			+ "left join shiro_user su on su.id = sru.userid "
			+ "where 1=1 and  su.id = #{userId} ")
	List<String> getUserRolesById(Map<String, Object> userinfo);
}
