package com.rain.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rain.RainController;
import com.rain.beans.SubscriptionRequestBean;
import com.rain.dao.subscription.Payment;

@Component
public class SubscriptionRequestConverter {

	private static Logger log = LoggerFactory.getLogger(RainController.class);
	public static Payment convert(SubscriptionRequestBean bean)
	{
		log.debug("Inside convert method ");
		if(bean.getPaymentMode()==null)
			bean.setPaymentMode("-");
		else if(bean.getPaymentMode().length()<=0)
			bean.setPaymentMode("-");
		Payment payment=new Payment();
		payment.setEmail(bean.getEmail());
		payment.setFirstName(bean.getFirstName());
		payment.setLastName(bean.getLastName());
		payment.setPaymentMode(bean.getPaymentMode());
		log.debug("Exiting convert method ");
		return payment;
	}
}
