package com.rain.dao.user;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.rain.dao.application.ApplicationBean;
import com.rain.dao.group.GroupBean;
import com.rain.dao.login.LoginBean;
import com.rain.dao.role.RoleBean;
@Entity
@Table(name = "user_table_t")
public class UserBean {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String firstName;
	private String lastName;
	private String gender;
	private Date dob;
	private String email;
	private long phone;
	@ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private RoleBean roleBean;
	@ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private ApplicationBean bean;
	@OneToOne(cascade = CascadeType.ALL)
	private LoginBean loginBean;
	@ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	 @JoinTable(
	            name="user_tab_group_tab",
	            joinColumns = @JoinColumn( name="user_id"),
	            inverseJoinColumns = {@JoinColumn(name = "grp_id")}
	        )
	private List<GroupBean> grpBeans;
	
	
	public List<GroupBean> getGrpBeans() {
		return grpBeans;
	}
	public void setGrpBeans(List<GroupBean> grpBeans) {
		this.grpBeans = grpBeans;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public RoleBean getRoleBean() {
		return roleBean;
	}
	public void setRoleBean(RoleBean roleBean) {
		this.roleBean = roleBean;
	}
	public ApplicationBean getBean() {
		return bean;
	}
	public void setBean(ApplicationBean bean) {
		this.bean = bean;
	}
	public LoginBean getLoginBean() {
		return loginBean;
	}
	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
	
	

}
