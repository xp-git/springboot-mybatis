package org.spring.springboot.shiro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.spring.springboot.domain.ShiroUser;
import org.spring.springboot.service.RoleService;
import org.spring.springboot.service.UserService;
import org.spring.springboot.util.COMMON;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author  Carlyle
 * @date 	Jul 24, 2017 4:38:46 PM
 * @description AuthorizingRealm
 * 
 */
public class MyShiroRealm extends AuthorizingRealm{

	private Logger log_ = Logger.getLogger(MyShiroRealm.class.getName());
	@Autowired
	private UserService userService ;
	@Autowired
	private RoleService	 roleService ;
	/* (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("==权限判断==doGetAuthenticationInfo==");
		Subject subject = SecurityUtils.getSubject();
		String key = "AUTHORIZATION_INFO";
		System.out.println("加载权限======；======");
		Session session = subject.getSession(false);
		if(COMMON.isEmpty(session)){
			session = subject.getSession();
		}
        //SimpleAuthorizationInfo info 
		SimpleAuthorizationInfo authorizationInfo = (SimpleAuthorizationInfo) session.getAttribute(key);
		if(!subject.isAuthenticated()||COMMON.isEmpty(authorizationInfo)){
			String username = (String) principals.getPrimaryPrincipal();
			log_.info("username:"+username);
			ShiroUser user = userService.checkUserByUsername(username);
			if(!COMMON.isEmpty(user)){
				Map<String,Object> userInfo = new HashMap<> ();
				Integer userId = user.getId();
				log_.info("userId:"+userId);
				userInfo.put("userId", userId);
				userInfo.put("isWork", "Y");
				List<String> roles = null;
				try {
					roles = roleService.getUserRoles(userInfo);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				for(int i =0 ; i < roles.size() ; i++){
					System.out.println("角色信息："+roles.get(i));
				}
				if(!COMMON.isEmpty(roles)){
					//添加该用户对应的角色信息            	SimpleAuthorizationInfo info 
					authorizationInfo = new SimpleAuthorizationInfo();
					authorizationInfo.addRoles(roles);
					List<String> permissions = userService.findPermissionByUserInfo(userInfo);
					for(String str : permissions){
						System.out.println("权限："+str);
					}
					authorizationInfo.addStringPermissions(permissions);
					return authorizationInfo;
				}
			}else{
				log_.info("用户不存在...");
				return null;
			}
		}
		return authorizationInfo;
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 *  用户登录的时候回调用这个方法，判断当前登录的用户有没有被锁定
	 * 
	 */
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		System.out.println("==是否锁定==doGetAuthenticationInfo==");
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		ShiroUser user = null;
		try {
			user = userService.checkUserByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(COMMON.isEmpty(user)){
			throw new UnknownAccountException();
		}else{
			String isLock = user.getIsLock();
			if(!COMMON.isEmpty(isLock)&&"Y".equals(isLock)){
				//账户被锁定
				throw new LockedAccountException();
			}
			//获取随机盐
			String salt = user.getSalt();
			username = user.getUsername();
			String pwd = user.getPassword();
			log_.info("account:"+username+"\t pwd:"+pwd+"\t salt:"+salt);
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,pwd,
					new MySimpleByteSource(salt.getBytes()),getClass().getName()); 
			return info;
		}
		
	}

}
