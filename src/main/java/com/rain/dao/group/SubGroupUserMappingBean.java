package com.rain.dao.group;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "sgp_u_m_table_t")
public class SubGroupUserMappingBean {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long subGroupId;
	private long userId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSubGroupId() {
		return subGroupId;
	}
	public void setSubGroupId(long subGroupId) {
		this.subGroupId = subGroupId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}

	
	
	
}
