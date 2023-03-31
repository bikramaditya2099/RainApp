package com.rain.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.rain.beans.AuthBean;
import com.rain.beans.GroupMappingBean;
import com.rain.beans.GroupReqBean;
import com.rain.beans.RegisterAppBean;
import com.rain.beans.RegisterUserBean;
import com.rain.beans.SubscriptionRequestBean;

/**
 * 
 * @author Bikramaditya Gochhayat
 *
 */
@Component
public class RequestValidator {

	public static void validateSubscription(SubscriptionRequestBean bean) throws RainException
	{
		
		if(bean.getEmail()==null) throw new RainException(RainExceptionEnum.EMAIL_NOT_FOUND);
		if(bean.getEmail().trim().length()<=0) throw new RainException(RainExceptionEnum.EMAIL_NOT_FOUND);
		
		if(bean.getFirstName()==null) throw new RainException(RainExceptionEnum.FIRST_NAME_NOT_FOUND);
		if(bean.getFirstName().trim().length()<=0) throw new RainException(RainExceptionEnum.FIRST_NAME_NOT_FOUND);
		
		if(bean.getLastName()==null) throw new RainException(RainExceptionEnum.LAST_NAME_NOT_FOUND);
		if(bean.getLastName().trim().length()<=0) throw new RainException(RainExceptionEnum.LAST_NAME_NOT_FOUND);
		
		
		//payment mode validation to be done//
		
		
		if(bean.getSubscriptionType()<=0 || bean.getSubscriptionType()>5) throw new RainException(RainExceptionEnum.INVALID_SUBSCRIPTION_TYPE);
		if(bean.getSubscriptionType()!=1) throw new RainException(RainExceptionEnum.FEATURE_NOT_AVAILABLE);
		
		
		
	}
	
	
	public static void validateRegisterApp(RegisterAppBean appBean,String payId) throws RainException
	{
		if(payId==null) throw new RainException(RainExceptionEnum.PAYMENT_ID_NOT_FOUND_IN_HEADER);
		if(payId.trim().length()<=0) throw new RainException(RainExceptionEnum.PAYMENT_ID_NOT_FOUND_IN_HEADER);
		
		if(appBean.getAppName()==null) throw new RainException(RainExceptionEnum.APP_NAME_NOT_FOUND);
		if(appBean.getAppName().trim().length()<=0) throw new RainException(RainExceptionEnum.APP_NAME_NOT_FOUND);
		
		
		if(appBean.getOwner()==null)throw new RainException(RainExceptionEnum.OWNER_NAME_NOT_FOUND);
		if(appBean.getOwner().trim().length()<=0) throw new RainException(RainExceptionEnum.OWNER_NAME_NOT_FOUND);
		
		if(appBean.getUserEmail()==null)throw new RainException(RainExceptionEnum.EMAIL_NOT_FOUND);
		if(appBean.getUserEmail().trim().length()<=0) throw new RainException(RainExceptionEnum.EMAIL_NOT_FOUND);
		
		if(appBean.getPassword()==null)throw new RainException(RainExceptionEnum.PASSWORD_NOT_FOUND);
		if(appBean.getPassword().trim().length()<=0) throw new RainException(RainExceptionEnum.PASSWORD_NOT_FOUND);
		
		if(appBean.getDob()==null)throw new RainException(RainExceptionEnum.EMPTY_DOB);
		if(appBean.getDob().trim().length()<=0) throw new RainException(RainExceptionEnum.EMPTY_DOB);
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-YYYY");
		try {
			Date date=dateFormat.parse(appBean.getDob());
		} catch (ParseException e) {
			throw new RainException(RainExceptionEnum.INVALID_DOB_FORMAT);
		}
		
		if(appBean.getFirstName()==null)throw new RainException(RainExceptionEnum.FIRST_NAME_NOT_FOUND);
		if(appBean.getFirstName().trim().length()<=0) throw new RainException(RainExceptionEnum.FIRST_NAME_NOT_FOUND);
		
		if(appBean.getLastName()==null)throw new RainException(RainExceptionEnum.LAST_NAME_NOT_FOUND);
		if(appBean.getLastName().trim().length()<=0) throw new RainException(RainExceptionEnum.LAST_NAME_NOT_FOUND);
		
		if(appBean.getPhone()==null)throw new RainException(RainExceptionEnum.EMPTY_PHONE);
		if(appBean.getPhone().trim().length()<=0) throw new RainException(RainExceptionEnum.EMPTY_PHONE);
		
		if(appBean.getGender()==null)throw new RainException(RainExceptionEnum.GENDER_NOT_FOUND);
		if(appBean.getGender().trim().length()<=0) throw new RainException(RainExceptionEnum.GENDER_NOT_FOUND);
		
		if(!(appBean.getGender().equalsIgnoreCase("male")||appBean.getGender().equalsIgnoreCase("female"))) throw new RainException(RainExceptionEnum.INVALID_GENDER);
		
		
	}
	
	
	public static void validateRegisterUser(RegisterUserBean userBean,String appId) throws RainException
	{
		if(appId==null) throw new RainException(RainExceptionEnum.APP_ID_NOT_FOUND_IN_HEADER);
		if(appId.trim().length()<=0) throw new RainException(RainExceptionEnum.APP_ID_NOT_FOUND_IN_HEADER);
		
		if(userBean.getUserEmail()==null)throw new RainException(RainExceptionEnum.EMAIL_NOT_FOUND);
		if(userBean.getUserEmail().trim().length()<=0) throw new RainException(RainExceptionEnum.EMAIL_NOT_FOUND);
		
		if(userBean.getPassword()==null)throw new RainException(RainExceptionEnum.PASSWORD_NOT_FOUND);
		if(userBean.getPassword().trim().length()<=0) throw new RainException(RainExceptionEnum.PASSWORD_NOT_FOUND);
		
		if(userBean.getDob()==null)throw new RainException(RainExceptionEnum.EMPTY_DOB);
		if(userBean.getDob().trim().length()<=0) throw new RainException(RainExceptionEnum.EMPTY_DOB);
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd-mm-yyyy");
		try {
			Date date=dateFormat.parse(userBean.getDob());
		} catch (ParseException e) {
			throw new RainException(RainExceptionEnum.INVALID_DOB_FORMAT);
		}
		
		if(userBean.getFirstName()==null)throw new RainException(RainExceptionEnum.FIRST_NAME_NOT_FOUND);
		if(userBean.getFirstName().trim().length()<=0) throw new RainException(RainExceptionEnum.FIRST_NAME_NOT_FOUND);
		
		if(userBean.getLastName()==null)throw new RainException(RainExceptionEnum.LAST_NAME_NOT_FOUND);
		if(userBean.getLastName().trim().length()<=0) throw new RainException(RainExceptionEnum.LAST_NAME_NOT_FOUND);
		
		if(userBean.getPhone()==null)throw new RainException(RainExceptionEnum.EMPTY_PHONE);
		if(userBean.getPhone().trim().length()<=0) throw new RainException(RainExceptionEnum.EMPTY_PHONE);
		
		if(userBean.getGender()==null)throw new RainException(RainExceptionEnum.GENDER_NOT_FOUND);
		if(userBean.getGender().trim().length()<=0) throw new RainException(RainExceptionEnum.GENDER_NOT_FOUND);
		
		if(!(userBean.getGender().equalsIgnoreCase("male")||userBean.getGender().equalsIgnoreCase("female"))) throw new RainException(RainExceptionEnum.INVALID_GENDER);
		
		
	}
	
	public static void validateAuth(AuthBean authBean,String appId) throws RainException
	{
		if(authBean.getUserName()==null)throw new RainException(RainExceptionEnum.EMAIL_NOT_FOUND);
		if((authBean.getUserName().trim().length()<=0)) throw new RainException(RainExceptionEnum.EMAIL_NOT_FOUND);
		
		if(authBean.getPassword()==null)throw new RainException(RainExceptionEnum.PASSWORD_NOT_FOUND);
		if((authBean.getPassword().trim().length()<=0)) throw new RainException(RainExceptionEnum.PASSWORD_NOT_FOUND);
		
		if(appId==null) throw new RainException(RainExceptionEnum.APP_ID_NOT_FOUND_IN_HEADER);
		if(appId.trim().length()<=0) throw new RainException(RainExceptionEnum.APP_ID_NOT_FOUND_IN_HEADER);
		
	}
	
	public static void validateGroupRequest(GroupReqBean reqBean,String appId,String sso) throws RainException
	{
		if(reqBean.getGroupName()==null)throw new RainException(RainExceptionEnum.EMPTY_GROUP_NAME);
		if((reqBean.getGroupName().trim().length()<=0)) throw new RainException(RainExceptionEnum.EMPTY_GROUP_NAME);
		
		if(appId==null)throw new RainException(RainExceptionEnum.APP_ID_NOT_FOUND_IN_HEADER);
		if((appId.trim().length()<=0)) throw new RainException(RainExceptionEnum.APP_ID_NOT_FOUND_IN_HEADER);
		
		if(sso==null)throw new RainException(RainExceptionEnum.SSO_NOT_FOUND);
		if((sso.trim().length()<=0)) throw new RainException(RainExceptionEnum.SSO_NOT_FOUND);
		
	}
	
	
	public static void validateGroupMappingRequest(GroupMappingBean grpMapBean,String appId,String sso) throws RainException
	{
		if(appId==null)throw new RainException(RainExceptionEnum.APP_ID_NOT_FOUND_IN_HEADER);
		if((appId.trim().length()<=0)) throw new RainException(RainExceptionEnum.APP_ID_NOT_FOUND_IN_HEADER);
		
		if(sso==null)throw new RainException(RainExceptionEnum.SSO_NOT_FOUND);
		if((sso.trim().length()<=0)) throw new RainException(RainExceptionEnum.SSO_NOT_FOUND);
		
		if(grpMapBean.getGroupId()==null)throw new RainException(RainExceptionEnum.EMPTY_GROUP_ID);
		if((grpMapBean.getGroupId().trim().length()<=0)) throw new RainException(RainExceptionEnum.EMPTY_GROUP_ID);
		
		if(grpMapBean.getUserId()==null)throw new RainException(RainExceptionEnum.EMAIL_NOT_FOUND);
		if((grpMapBean.getUserId().trim().length()<=0)) throw new RainException(RainExceptionEnum.EMAIL_NOT_FOUND);
	}
	
	
	public static void validateUserReuest(String appId,String sso) throws RainException
	{
		if(appId==null)throw new RainException(RainExceptionEnum.APP_ID_NOT_FOUND_IN_HEADER);
		if((appId.trim().length()<=0)) throw new RainException(RainExceptionEnum.APP_ID_NOT_FOUND_IN_HEADER);
		
		if(sso==null)throw new RainException(RainExceptionEnum.SSO_NOT_FOUND);
		if((sso.trim().length()<=0)) throw new RainException(RainExceptionEnum.SSO_NOT_FOUND);
	}
	
	
}