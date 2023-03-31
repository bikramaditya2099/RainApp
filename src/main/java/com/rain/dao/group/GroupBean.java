package com.rain.dao.group;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.rain.dao.application.ApplicationBean;

@Component
@Entity
@Table(name = "group_table_t")
public class GroupBean {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String groupId;
	@ManyToOne
	private ApplicationBean bean;
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public ApplicationBean getBean() {
		return bean;
	}
	public void setBean(ApplicationBean bean) {
		this.bean = bean;
	}
	
	
	
	
	
	
}
