package com.rain.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

	private static SessionFactory factory;
	
	private static HibernateUtils hibernateUtils;
	
	
	private HibernateUtils(){}
	static{
		getSessionFactory();
	}
	public HibernateUtils getInstance()
	{
		if(hibernateUtils==null)
			hibernateUtils=new HibernateUtils();
		
		return hibernateUtils;
	}
	
	public synchronized static SessionFactory getSessionFactory()
	{
		 
		if(factory==null)
			factory=new Configuration().configure().buildSessionFactory();
		
		return factory;
	}
	
	public static Session getNewHibernateSession()
	{
		factory=getSessionFactory();
		
		return factory.openSession();
		
	}
}
