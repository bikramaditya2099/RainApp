package com.rain.dao.subscription;

import java.util.List;

import org.springframework.stereotype.Component;

import com.rain.utils.RainException;

@Component
public interface SubscriptionDao {

	public String subscribe(Payment payment,int subScribeId) throws RainException;
	public SubscriptionBean getSubscriptionById(int id);
	public List<SubscriptionBean> getSubscriptionList();
	public void invokeDefaultSubscriptionValues();
	public SubscriptionBean getSubscriptionByEmail(String emailId);
}
