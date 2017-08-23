package org.spring.springboot.domain;

import java.util.Date;

public class ShiroPermission {
	private int id ; 
	private String perName ;
	private String perUrl;
	private String perKey;
	private String description ;
	private String enable ;
	private Date createDate ;
	private Date modifyDate ;
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
	 * @return the perName
	 */
	public String getPerName() {
		return perName;
	}
	/**
	 * @param perName the perName to set
	 */
	public void setPerName(String perName) {
		this.perName = perName;
	}
	/**
	 * @return the perUrl
	 */
	public String getPerUrl() {
		return perUrl;
	}
	/**
	 * @param perUrl the perUrl to set
	 */
	public void setPerUrl(String perUrl) {
		this.perUrl = perUrl;
	}
	/**
	 * @return the perKey
	 */
	public String getPerKey() {
		return perKey;
	}
	/**
	 * @param perKey the perKey to set
	 */
	public void setPerKey(String perKey) {
		this.perKey = perKey;
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
	/**
	 * @return the enable
	 */
	public String getEnable() {
		return enable;
	}
	/**
	 * @param enable the enable to set
	 */
	public void setEnable(String enable) {
		this.enable = enable;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the modifyDate
	 */
	public Date getModifyDate() {
		return modifyDate;
	}
	/**
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
}
