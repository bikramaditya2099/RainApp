package com.rain.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rain.RainController;
import com.rain.beans.RegisterUserBean;
import com.rain.beans.UserInfoBean;
import com.rain.dao.application.ApplicationContainer;
import com.rain.dao.login.LoginBean;
import com.rain.dao.role.RoleBean;
import com.rain.dao.user.UserBean;

@Component
public class UserConverter {

	private static Logger log = LoggerFactory.getLogger(RainController.class);
	public static ApplicationContainer convert(RegisterUserBean uBean,String appId)
	{
		
		log.debug("Inside convert method ");
		LoginBean bean=new LoginBean();
		bean.setUserName(uBean.getUserEmail());
		bean.setPassword(uBean.getPassword());
		
		UserBean userBean=new UserBean();
		try {
			userBean.setDob(new SimpleDateFormat("dd-MM-YYYY").parse(uBean.getDob()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		userBean.setEmail(uBean.getUserEmail());
		userBean.setFirstName(uBean.getFirstName());
		userBean.setGender(uBean.getGender());
		userBean.setLastName(uBean.getLastName());
		userBean.setPhone(Long.parseLong(uBean.getPhone()));
		
		RoleBean roleBean=new RoleBean();
		roleBean.setRole("END_USER");
		roleBean.setId(2);
		
		
		
		ApplicationContainer applicationContainer=new ApplicationContainer();
		applicationContainer.setBean(roleBean);
		applicationContainer.setLoginBean(bean);
		applicationContainer.setUserBean(userBean);
		applicationContainer.setPaymentId(appId);
		log.debug("Exiting convert method ");
		return applicationContainer;
		
	}
	
	public static List<UserInfoBean> convert(List<UserBean> bean)
	{
		List<UserInfoBean> beans=new ArrayList<UserInfoBean>();
		for(UserBean userBean:bean)
		{
			UserInfoBean infoBean=new UserInfoBean();
			infoBean.setDob(new SimpleDateFormat("MM/dd/yyyy").format(userBean.getDob()));
			infoBean.setEmail(userBean.getEmail());
			infoBean.setFirstName(userBean.getFirstName());
			infoBean.setGender(userBean.getGender());
			infoBean.setId(userBean.getId());
			infoBean.setLastName(userBean.getLastName());
			infoBean.setPhone(userBean.getPhone());
			infoBean.setRole(userBean.getRoleBean().getRole());
			beans.add(infoBean);
		}
		
		return beans;
		
	}
}
