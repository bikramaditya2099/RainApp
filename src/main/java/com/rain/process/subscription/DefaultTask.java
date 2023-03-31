package com.rain.process.subscription;

import com.rain.dao.role.RoleDao;
import com.rain.dao.role.RoleDaoImpl;
import com.rain.dao.subscription.SubscriptionDao;
import com.rain.dao.subscription.SubscriptionDaoImpl;

public class DefaultTask {

	/**
	 * This will invoke a function to populate the default data in subscription table.
	 * This function only invokes for the first time when the application starts.
	 * If for any reason the data of subscription table tampered, this method updates the 
	 * subscription table with default data.
	 * **/
	public static void invokeDefaultSubScription()
	{
		SubscriptionDao dao=new SubscriptionDaoImpl();
		dao.invokeDefaultSubscriptionValues();
	}
	
	
	
	
	/**
	 * This will invoke a function to populate the default data in Role table.
	 * This function only invokes for the first time when the application starts.
	 * If for any reason the data of Role table tampered, this method updates the 
	 * role table with default data.
	 * **/
	public static void invokeDefaultRole()
	{
		RoleDao dao=new RoleDaoImpl();
		dao.invokeDefaultRoles();
	}
}
