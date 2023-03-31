package com.rain.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rain.RainController;
import com.rain.beans.RegisterAppBean;
import com.rain.dao.application.ApplicationBean;
import com.rain.dao.application.ApplicationContainer;
import com.rain.dao.group.GroupBean;
import com.rain.dao.login.LoginBean;
import com.rain.dao.role.RoleBean;
import com.rain.dao.user.UserBean;
import com.rain.utils.RainDateFormat;

@Component
public class ApplicationRegisterConverter {
	
	@Autowired
	private RainDateFormat dateFormat;
	private static Logger log = LoggerFactory.getLogger(RainController.class);
	public static ApplicationBean convert(RegisterAppBean appBean)
	{
		log.debug("Inside convert method ");
		ApplicationBean applicationBean=new ApplicationBean();
		applicationBean.setAppName(appBean.getAppName());
		applicationBean.setActive("Y");
		applicationBean.setCreatedOn(new Date());
		applicationBean.setOwner(appBean.getOwner());
		
		
		LoginBean bean=new LoginBean();
		bean.setUserName(appBean.getUserEmail());
		bean.setPassword(appBean.getPassword());
		
		List<LoginBean> list=new ArrayList<LoginBean>();
		list.add(bean);
		
		applicationBean.setLoginBeans(list);
		log.debug("Exiting convert method ");
		return applicationBean;
		
	}
	
	public  ApplicationContainer convert(RegisterAppBean appBean,String payMentId)
	{
		log.debug("Inside convert method ");
		ApplicationBean applicationBean=new ApplicationBean();
		applicationBean.setAppName(appBean.getAppName());
		applicationBean.setActive("Y");
		applicationBean.setCreatedOn(new Date());
		applicationBean.setOwner(appBean.getOwner());
		
		
		LoginBean bean=new LoginBean();
		bean.setUserName(appBean.getUserEmail());
		bean.setPassword(appBean.getPassword());
		
		UserBean userBean=new UserBean();
		try {
			userBean.setDob(dateFormat.parse(appBean.getDob()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		userBean.setEmail(appBean.getUserEmail());
		userBean.setFirstName(appBean.getFirstName());
		userBean.setGender(appBean.getGender());
		userBean.setLastName(appBean.getLastName());
		userBean.setPhone(Long.parseLong(appBean.getPhone()));
		
		RoleBean roleBean=new RoleBean();
		roleBean.setRole("ADMIN");
		roleBean.setId(1);
		
		
		
		
		GroupBean groupBean=new GroupBean();
		groupBean.setBean(applicationBean);
		groupBean.setName("ALL");
		UUID uniqueKey = UUID.randomUUID();
		groupBean.setGroupId(uniqueKey.toString());
		
		
		
		
		ApplicationContainer applicationContainer=new ApplicationContainer();
		applicationContainer.setBean(roleBean);
		applicationContainer.setLoginBean(bean);
		applicationContainer.setUserBean(userBean);
		applicationContainer.setApplicationBean(applicationBean);
		applicationContainer.setPaymentId(payMentId);
		applicationContainer.setGrBean(groupBean);
		log.debug("Exiting convert method ");
		return applicationContainer;
		
		
		
		
	}
	

}
