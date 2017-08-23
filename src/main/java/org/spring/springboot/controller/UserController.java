package org.spring.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

	
	
	@RequestMapping("/admin/view")
	public String adminView(){
		
		return "adminPage";
	}
	@RequestMapping("/admin/addUser")
	public String addUser(){
		
		return "adminPage";                 
	}
	@RequestMapping("/admin/deleteUser")
	public String deleteUser(){
		
		return "adminPage";
	}      
	@RequestMapping("/userView")
	public String userView(){
		
		return "user";
	}      
	@RequestMapping("/userPage")
	public String userPage(){
		
		return "userPage";
	}      
	@RequestMapping("unauthorizedUrl")
	public String unauthorizedUrl(){
		
		return "unauthorizedUrl";
	}
}
