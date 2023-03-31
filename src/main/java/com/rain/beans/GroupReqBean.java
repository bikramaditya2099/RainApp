package com.rain.beans;

import org.springframework.stereotype.Component;

@Component
public class GroupReqBean {

	private String groupName;
	private String grpRef;
	
	

	public String getGrpRef() {
		return grpRef;
	}

	public void setGrpRef(String grpRef) {
		this.grpRef = grpRef;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}
