package org.spring.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.spring.springboot.domain.ShiroUser;
import org.spring.springboot.service.RoleService;
import org.spring.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


//@SpringBootApplication
@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	private Logger log_ = Logger.getLogger(LoginController.class.getName());
	@RequestMapping("/index")
	public String index(){
		System.out.println("访问首页");
		return "index";
		
	}
	@RequestMapping("/test")
	public String test(ModelMap modelMap){
		System.out.println("test 测试");
		modelMap.addAttribute("hello","好的");
		
		Map<String,Object> userInfo = new HashMap<> ();
		Integer userId = 1;
		log_.info("userId:"+userId+"===="+roleService);
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
		return "test";
		
	}
	
	
	@RequestMapping("/login")
	@ResponseBody
	public String login(String account ,String password ){
		Subject subject = SecurityUtils.getSubject();
		log_.info("account:"+account+"\t password:"+password);
		String msg = "";
		if(!account.equals(subject.getPrincipal())||!subject.isAuthenticated()){
			UsernamePasswordToken token = new UsernamePasswordToken(account, password);
			try {
				subject.login(token);
				Session session = subject.getSession(false);
				ShiroUser user = userService.checkUserByUsername(account);
				if(user != null){
					System.out.println("用户信息："+user.getEmail());
				}
				session.setAttribute("user", user);
				msg = "ok";
			} catch (UnknownAccountException  uae) {
				//用户名不存在
				msg = "用户名不存在!";
			}catch (IncorrectCredentialsException  ice) {
				//密码错误
				msg = "用户名或密码错误!";
			}catch (LockedAccountException lae) { 
				//账户被锁定
				msg = "账户被锁定";
			}catch(ExcessiveAttemptsException eae){
				//登录失败次数超过系统最大次数,请稍后重试
				msg = "登录失败次数超过系统最大次数,请稍后重试!";
			}catch (Exception e) {
				//出现其他异常
				msg = "登录异常";
			}
			log_.info("msg:"+msg);
		}
		
		
		return msg;
		
	}
	@RequestMapping("/logout")
	public String logout(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		log_.info("退出成功，已销毁用户信息，需要重新登录");
		return "logout";
	}
}
