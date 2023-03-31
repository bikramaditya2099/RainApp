package com.rain.dao.application;

import com.rain.dao.group.GroupBean;
import com.rain.dao.login.LoginBean;
import com.rain.dao.role.RoleBean;
import com.rain.dao.user.UserBean;

public class ApplicationContainer {

	private String paymentId;
	private ApplicationBean applicationBean;
	private RoleBean bean;
	private LoginBean loginBean;
	private UserBean userBean;
	private GroupBean grBean;
	
	
	
	public GroupBean getGrBean() {
		return grBean;
	}
	public void setGrBean(GroupBean grBean) {
		this.grBean = grBean;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public ApplicationBean getApplicationBean() {
		return applicationBean;
	}
	public void setApplicationBean(ApplicationBean applicationBean) {
		this.applicationBean = applicationBean;
	}
	public RoleBean getBean() {
		return bean;
	}
	public void setBean(RoleBean bean) {
		this.bean = bean;
	}
	public LoginBean getLoginBean() {
		return loginBean;
	}
	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
	public UserBean getUserBean() {
		return userBean;
	}
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
	
	
}
