package com.rain.process.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rain.RainController;
import com.rain.beans.AuthBean;
import com.rain.dao.login.LoginDao;
import com.rain.utils.RainException;

@Component
public class AuthProcessor {
	
	@Autowired
	private LoginDao dao;
	
	private static Logger log = LoggerFactory.getLogger(RainController.class);

	public void processAuth(AuthBean authBean,String appId) throws RainException
	{
		log.debug("Entered proccesor");
		dao.authenticate(authBean.getUserName(),authBean.getPassword(), appId);
		log.debug("Exiting proccesor");
	}
	
}
