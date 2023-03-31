package com.rain.dao.login;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.rain.dao.application.ApplicationBean;

@Component
@Entity
@Table(name = "login_table_t")
public class LoginBean {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String userName;
	private String password;
	@Column(columnDefinition="TEXT")
	private String sso;
	private String ssoAutoUpdate;
	@OneToOne(cascade=CascadeType.ALL)
	private ApplicationBean applicationBean;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSso() {
		return sso;
	}
	public void setSso(String sso) {
		this.sso = sso;
	}
	public String getSsoAutoUpdate() {
		return ssoAutoUpdate;
	}
	public void setSsoAutoUpdate(String ssoAutoUpdate) {
		this.ssoAutoUpdate = ssoAutoUpdate;
	}
	public ApplicationBean getApplicationBean() {
		return applicationBean;
	}
	public void setApplicationBean(ApplicationBean applicationBean) {
		this.applicationBean = applicationBean;
	}
	
	
}
