package com.rain.dao.subscription;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component

@Entity
@Table(name = "subscription_master_t")
public class SubscriptionBean {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String type;
	private int totalUser;
	private int price;
	
	
	public SubscriptionBean(){}
	
	
	
	public SubscriptionBean(int id, String type, int totalUser, int price) {
		this.id = id;
		this.type = type;
		this.totalUser = totalUser;
		this.price = price;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTotalUser() {
		return totalUser;
	}
	public void setTotalUser(int totalUser) {
		this.totalUser = totalUser;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
	
}
