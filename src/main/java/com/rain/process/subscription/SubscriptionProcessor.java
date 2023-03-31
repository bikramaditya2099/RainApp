package com.rain.process.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rain.beans.SubscriptionRequestBean;
import com.rain.converter.SubscriptionRequestConverter;
import com.rain.dao.subscription.Payment;
import com.rain.dao.subscription.SubscriptionDao;
import com.rain.dao.subscription.SubscriptionDaoImpl;
import com.rain.utils.RainException;
import com.rain.utils.RainExceptionEnum;

@Component
public class SubscriptionProcessor {

	
	@Autowired
	private SubscriptionRequestConverter subscriptionRequestConverter;
	
	@Autowired
	private SubscriptionDao dao;
	
	public void processSubscription(SubscriptionRequestBean subscriptionRequestBean) throws RainException
	{
		Payment payment=subscriptionRequestConverter.convert(subscriptionRequestBean);
		
	
		
		String response=dao.subscribe(payment, subscriptionRequestBean.getSubscriptionType());
		
		if(response!=null)
			throw new RainException(RainExceptionEnum.SUCCESSFULL_SUBSCRIPTION,response);
		else
			throw new RainException(RainExceptionEnum.INVALID_REQUEST,"SOMETHING WENT WRONG !!");
	}
}
