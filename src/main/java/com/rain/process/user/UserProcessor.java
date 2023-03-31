package com.rain.process.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rain.beans.RegisterUserBean;
import com.rain.beans.UserInfoBean;
import com.rain.converter.UserConverter;
import com.rain.dao.application.ApplicationContainer;
import com.rain.dao.login.LoginDao;
import com.rain.dao.user.UserBean;
import com.rain.dao.user.UserDao;
import com.rain.dao.user.UserDaoImpl;
import com.rain.utils.RainException;
import com.rain.utils.RainExceptionEnum;

@Component
public class UserProcessor {

	@Autowired
	private UserConverter converter;
	@Autowired
	private UserDao userDao;
	@Autowired
	private LoginDao loginDao;
	public void processUserRegistration(RegisterUserBean userBean,String appId) throws RainException
	{
		
		ApplicationContainer applicationContainer=converter.convert(userBean, appId);
		
		userDao.registerUser(applicationContainer);
		
		throw new RainException(RainExceptionEnum.SUCCESSFULL_USER_REGISTER);
	}
	
	public void getAllUserInfo(String appId,String sso) throws RainException
	{
		boolean isAuth = loginDao.authenticateBySSO(sso);
		if (!isAuth)
			throw new RainException(RainExceptionEnum.INVALID_CREDENTIAL);
		
		List<UserBean> list=userDao.getUsersByApplicationId(appId,sso);
		List<UserInfoBean> userlist=converter.convert(list);
		throw new RainException(RainExceptionEnum.SUCCESSFULL_USER_FETCHED,userlist);
	}
}
