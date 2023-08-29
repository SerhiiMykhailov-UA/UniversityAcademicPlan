package org.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public User() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String nickName;
	@Column
	private String userTipe;
	@Column
	private String password;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String param) {
		this.nickName = param;
	}

	public String getUserTipe() {
		return userTipe;
	}

	public void setUserTipe(String param) {
		this.userTipe = param;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String param) {
		this.password = param;
	}

}