package com.rain.dao.user;

import java.util.List;

import com.rain.dao.application.ApplicationContainer;
import com.rain.dao.login.LoginBean;
import com.rain.utils.RainException;

public interface UserDao {

	List<UserBean> getUsersByAppId(long id);
	void registerUser(ApplicationContainer applicationContainer) throws RainException;
	UserBean getUserByUserEmail(String appId,String email);
    String generateSSO(LoginBean loginBean, String appId);
    public List<UserBean> getUsersByApplicationId(String id,String sso) throws RainException;
}
