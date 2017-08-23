package org.spring.springboot.domain;


/**
 * @author  Carlyle
 * @date 	Jul 24, 2017 5:22:52 PM
 * @description
 * 
 */
public class ShiroRoleUser {
	private int id ; 
	private Integer roleid ;
	private Integer userid ;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the roleid
	 */
	public Integer getRoleid() {
		return roleid;
	}
	/**
	 * @param roleid the roleid to set
	 */
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	/**
	 * @return the userid
	 */
	public Integer getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	
	
}
