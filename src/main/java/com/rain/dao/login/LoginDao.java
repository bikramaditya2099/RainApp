package com.rain.dao.login;

import com.rain.dao.user.UserBean;
import com.rain.utils.RainException;

public interface LoginDao {

	public String createLogin(LoginBean bean) throws RainException ;
	
	public UserBean authenticate(String userName,String password,String appId) throws RainException;
	
	void updateLogin(LoginBean bean);
	
	public boolean authenticateBySSO(String sso) throws RainException;
}
