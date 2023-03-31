package com.rain.dao.group;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Group {

	private int id;
	private String name;
	private String groupId;
	private List<Group> group;
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
	public List<Group> getGroup() {
		return group;
	}
	public void setGroup(List<Group> group) {
		this.group = group;
	}
	
	
	
	
	
}
