package com.rain.dao.role;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "role_table_t")
public class RoleBean {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String role;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	public RoleBean(){}
	public RoleBean(int id, String role) {
		this.id = id;
		this.role = role;
	}
	
	
	public static List<RoleBean> setDefaultRoles()
	{
		List<RoleBean> beans=new ArrayList<RoleBean>();
		beans.add(new RoleBean(1, "ADMIN"));
		beans.add(new RoleBean(2,"END_USER"));
		return beans;
	}
	
	
	
}
