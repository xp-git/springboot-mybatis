package org.spring.springboot.domain;


public class ShiroRolePermission {
	private int id ; 
	private Integer permissionid ;
	private Integer roleid ;
	
	private String description ;

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
	 * @return the permissionid
	 */
	public Integer getPermissionid() {
		return permissionid;
	}

	/**
	 * @param permissionid the permissionid to set
	 */
	public void setPermissionid(Integer permissionid) {
		this.permissionid = permissionid;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
