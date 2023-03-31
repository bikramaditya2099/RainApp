package com.rain.beans;

import org.springframework.stereotype.Component;

@Component
public class SubscriptionRequestBean {
	
	private String paymentMode;
	private String firstName;
	private String lastName;
	private String email;
	private int subscriptionType;
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getSubscriptionType() {
		return subscriptionType;
	}
	public void setSubscriptionType(int subscriptionType) {
		this.subscriptionType = subscriptionType;
	}
	
	
	

}
