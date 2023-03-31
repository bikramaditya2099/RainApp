package com.rain.dao.application;

import org.springframework.stereotype.Component;

import com.rain.dao.subscription.Payment;
import com.rain.utils.RainException;

@Component
public interface ApplicationDao {

	public Payment getPaymentByPaymentId(String paymentId) throws RainException;
	public ApplicationBean getApplicationByPaymentId(String paymentId) throws RainException;
	public String registerApplication(ApplicationBean applicationBean,String paymentId) throws RainException;
	public String registerApplication(ApplicationContainer applicationContainer) throws RainException;
	//po7Wo+vRN9oT9prxc8kI9L3a/CvR+tYET6t/zvwa0Oc=
	public ApplicationBean getApplicationByAppId(String appId);
	
}
