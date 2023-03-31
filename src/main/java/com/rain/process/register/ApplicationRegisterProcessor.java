package com.rain.process.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rain.RainController;
import com.rain.beans.RegisterAppBean;
import com.rain.converter.ApplicationRegisterConverter;
import com.rain.dao.application.ApplicationBean;
import com.rain.dao.application.ApplicationContainer;
import com.rain.dao.application.ApplicationDao;
import com.rain.dao.application.ApplicationDaoImpl;
import com.rain.utils.RainException;
import com.rain.utils.RainExceptionEnum;

@Component
public class ApplicationRegisterProcessor {
	
	@Autowired
	private ApplicationRegisterConverter converter;
	
	@Autowired
	private ApplicationDao applicationDao;
	
	private static Logger log = LoggerFactory.getLogger(RainController.class);
	
	public void processAppRegistration(RegisterAppBean appBean,String payId) throws RainException
	{
		
		log.debug("Entered  AppRegistration processor");
		ApplicationContainer applicationContainer=converter.convert(appBean, payId);
		
		String sso=applicationDao.registerApplication(applicationContainer);
		
		log.debug("Exiting  AppRegistration processor");
		throw new RainException(RainExceptionEnum.SUCCESSFULL_APP_REGISTER,sso);
		
	}

}
