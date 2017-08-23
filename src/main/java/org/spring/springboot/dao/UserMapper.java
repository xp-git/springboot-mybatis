package org.spring.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.spring.springboot.domain.ShiroUser;

public interface UserMapper {

	@Select("select * from shiro_user where username = #{account} ")
	ShiroUser checkUserByUserName(String account );

	@Select("SELECT sp.perUrl FROM shiro_permission sp  "
			+ "LEFT JOIN  shiro_role_permission srp ON sp.id = srp.permissionid    "
			+ "INNER JOIN (SELECT  sr.id AS id ,su.id AS userid  FROM shiro_role sr  "
			+ "LEFT JOIN shiro_role_user sru ON sru.roleid = sr.id "
			+ "LEFT JOIN shiro_user su ON su.id = sru.userid  AND su.isLock = 'N'  "
			+ ") role ON role.id = srp.roleid "
			+ "WHERE 1=1 AND role.userid = #{userId} ")
	List<String> findPermissionByUserInfo(Map<String, Object> userInfo);
}
