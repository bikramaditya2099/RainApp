package com.rain.dao.group;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "subgroup_table_t")
public class SubGroupBean {
	
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String groupId;
	private String refGrp;
	@OneToOne(cascade=CascadeType.ALL)
	private GroupBean bean;
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
	public String getRefGrp() {
		return refGrp;
	}
	public void setRefGrp(String refGrp) {
		this.refGrp = refGrp;
	}
	public GroupBean getBean() {
		return bean;
	}
	public void setBean(GroupBean bean) {
		this.bean = bean;
	}
	
	

}
