/**
 * 
 */
package com.entities;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;


/**
 * @author anandu
 *
 */
@Entity
@Table (name = "user")
@JsonRootName("user")
public class User {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	@JsonProperty("_id")
	private int id;
	@Column(name = "name")
	@JsonProperty("name")
	private String name;
	@Column(name = "email")
	@JsonProperty("email")
	private String email;
	@Column(name = "bio")
	@JsonProperty("bio")
	private String bio;
	@Column(name = "avatarUrl")
	@JsonProperty("avatarUrl")
	private String avatarUrl;
	@Column(name = "creationDate")
	@JsonProperty("creationDate")
	private Date creationDate;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @return the bio
	 */
	public String getBio() {
		return bio;
	}
	/**
	 * @return the avatorUrl
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @param bio the bio to set
	 */
	public void setBio(String bio) {
		this.bio = bio;
	}
	/**
	 * @param avatorUrl the avatorUrl to set
	 */
	public void setAvatarUrl(String avatorUrl) {
		this.avatarUrl = avatorUrl;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
