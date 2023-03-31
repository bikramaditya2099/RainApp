package com.rain.dao.subscription;

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
@Table(name = "payment_table_t")
public class Payment {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String paymentMode;
	private String firstName;
	private String lastName;
	private String paymentId;
	private String email;
	@OneToOne(cascade = CascadeType.ALL)
	private SubscriptionBean subscriptionBean;
	
	
	
	
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
	public SubscriptionBean getSubscriptionBean() {
		return subscriptionBean;
	}
	public void setSubscriptionBean(SubscriptionBean subscriptionBean) {
		this.subscriptionBean = subscriptionBean;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
