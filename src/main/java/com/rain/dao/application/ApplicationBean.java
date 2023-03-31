package com.rain.dao.application;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.rain.dao.login.LoginBean;
import com.rain.dao.subscription.Payment;
import com.rain.dao.user.UserBean;

@Component
@Entity
@Table(name = "application_table_t")
public class ApplicationBean {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String appName;
	private String appId;
	private Date createdOn;
	private String owner;
	private String active;
	private int leftUsers;
	@OneToOne(cascade = CascadeType.ALL)
	private Payment payment;
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private List<LoginBean> loginBeans;

	
	
	
	public List<LoginBean> getLoginBeans() {
		return loginBeans;
	}
	public void setLoginBeans(List<LoginBean> loginBeans) {
		this.loginBeans = loginBeans;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public int getLeftUsers() {
		return leftUsers;
	}
	public void setLeftUsers(int leftUsers) {
		this.leftUsers = leftUsers;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	
}
