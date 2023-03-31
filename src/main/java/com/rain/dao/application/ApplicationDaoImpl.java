package com.rain.dao.application;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rain.RainController;
import com.rain.dao.group.GroupBean;
import com.rain.dao.login.LoginBean;
import com.rain.dao.role.RoleBean;
import com.rain.dao.subscription.Payment;
import com.rain.dao.user.UserBean;
import com.rain.dao.user.UserDao;
import com.rain.dao.user.UserDaoImpl;
import com.rain.utils.EmailTemplates;
import com.rain.utils.EncoderAndDecoderUtils;
import com.rain.utils.HibernateUtils;
import com.rain.utils.MailSender;
import com.rain.utils.RainException;
import com.rain.utils.RainExceptionEnum;

@Component
public class ApplicationDaoImpl implements ApplicationDao {

	@Autowired
	private MailSender sender;
	
	private static Logger log = LoggerFactory.getLogger(ApplicationDaoImpl.class);
	
	@Override
	public Payment getPaymentByPaymentId(String paymentId) throws RainException {
		
		log.debug("Inside getPaymentByPaymentId method ");
		Session session=HibernateUtils.getNewHibernateSession();
		Criteria criteria=session.createCriteria(Payment.class);
		criteria.add(Restrictions.eq("paymentId", paymentId));
		List<Payment> list=criteria.list();
		if(list.size()==0)
			throw new RainException(RainExceptionEnum.INVALID_PAYMENT_ID);
		session.close();
		log.debug("Exiting getPaymentByPaymentId method ");
		return list.get(0);
	}
	
	

	
	@Override
	public ApplicationBean getApplicationByPaymentId(String paymentId)
			throws RainException {
		
		log.debug("Inside getApplicationByPaymentId method ");
		Session session=HibernateUtils.getNewHibernateSession();
		Criteria criteria = session.createCriteria(ApplicationBean.class, "applicationBean");
		criteria.createAlias("applicationBean.payment", "pay");
		criteria.add(Restrictions.eq("pay.paymentId", paymentId));
		
		List<ApplicationBean> list=criteria.list();
		session.close();
		
		log.debug("Exiting getApplicationByPaymentId method ");
		if(list.size()>0)
		return list.get(0);
		else
			return null;
	}




	public String registerApplication(ApplicationBean applicationBean,String paymentId) throws RainException {
		
		log.debug("Inside registerApplication method ");
		ApplicationBean abean=getApplicationByPaymentId(paymentId);
		if(abean!=null)
			throw new RainException(RainExceptionEnum.APPLICATION_ALREADY_REGISTERED_WITH_APPID);
		
		Payment payment=getPaymentByPaymentId(paymentId);
		if(payment==null)
			throw new RainException(RainExceptionEnum.INVALID_PAYMENT_ID);
		List<LoginBean> beans=applicationBean.getLoginBeans();
		List<LoginBean> _beans=new ArrayList<LoginBean>();
		String appId=EncoderAndDecoderUtils.encrypt(String.valueOf(System.currentTimeMillis()).concat(applicationBean.getAppName()));
		for(LoginBean bean:beans)
		{
			bean.setSso(EncoderAndDecoderUtils.encrypt(bean.getUserName().concat(EncoderAndDecoderUtils.delim)+bean.getPassword().concat(EncoderAndDecoderUtils.delim)+appId));
			if(bean.getSsoAutoUpdate()==null)
				bean.setSsoAutoUpdate("N");
			else if(bean.getSsoAutoUpdate().length()<=0)
				bean.setSsoAutoUpdate("Y");
			
			bean.setUserName(EncoderAndDecoderUtils.encrypt(bean.getUserName()));
			bean.setPassword(EncoderAndDecoderUtils.encrypt(bean.getPassword()));
			bean.setApplicationBean(applicationBean);
			_beans.add(bean);
			
		}
		applicationBean.setLeftUsers(payment.getSubscriptionBean().getTotalUser()-1);
		applicationBean.setLoginBeans(_beans);
		
		applicationBean.setAppId(appId);
		applicationBean.setPayment(payment);
		Session session=HibernateUtils.getNewHibernateSession();
		Transaction transaction=session.beginTransaction();
		session.save(applicationBean);
		transaction.commit();
		session.close();
		sender.send(payment.getEmail(), EmailTemplates._applicationRegistertemplate.replace("_APPNAME_", abean.getAppName()).replace("_APPID_", abean.getAppId()), "no-reply");
		log.debug("Exiting registerApplication method ");
		return appId;
	}
	
	
	
	
	
	@Override
	public String registerApplication(ApplicationContainer applicationContainer)
			throws RainException {
		log.debug("Inside registerApplication method ");
		ApplicationBean applicationBean=applicationContainer.getApplicationBean();
		UserBean userBean=applicationContainer.getUserBean();
		LoginBean loginBean=applicationContainer.getLoginBean();
		RoleBean roleBean=applicationContainer.getBean();
		GroupBean groupBean=applicationContainer.getGrBean();
		String paymentId=applicationContainer.getPaymentId();
		
		
		ApplicationBean abean=getApplicationByPaymentId(paymentId);
		if(abean!=null)
			throw new RainException(RainExceptionEnum.APPLICATION_ALREADY_REGISTERED_WITH_APPID);
		
		String appId=EncoderAndDecoderUtils.encrypt(String.valueOf(System.currentTimeMillis()).concat(applicationBean.getAppName()));
		
		Payment payment=getPaymentByPaymentId(paymentId);
		if(payment==null)
			throw new RainException(RainExceptionEnum.INVALID_PAYMENT_ID);
		
		
		
		if(loginBean.getSsoAutoUpdate()==null)
			loginBean.setSsoAutoUpdate("N");
		else if(loginBean.getSsoAutoUpdate().length()<=0)
			loginBean.setSsoAutoUpdate("Y");
		
		loginBean.setUserName(EncoderAndDecoderUtils.encrypt(loginBean.getUserName()));
		loginBean.setPassword(EncoderAndDecoderUtils.encrypt(loginBean.getPassword()));
		loginBean.setSso(EncoderAndDecoderUtils.encrypt(loginBean.getUserName().concat(EncoderAndDecoderUtils.delim)+loginBean.getPassword().concat(EncoderAndDecoderUtils.delim)+appId));
		
		loginBean.setApplicationBean(applicationBean);
		
		List<LoginBean> _beans=new ArrayList<LoginBean>();
		_beans.add(loginBean);
		
		applicationBean.setLeftUsers(payment.getSubscriptionBean().getTotalUser()-1);
		applicationBean.setLoginBeans(_beans);
		
		applicationBean.setAppId(appId);
		applicationBean.setPayment(payment);
		
		UserDao userDao=new UserDaoImpl();
		UserBean userBeanD=userDao.getUserByUserEmail(appId, loginBean.getUserName());
		if(userBeanD!=null)
			throw new RainException(RainExceptionEnum.USER_ALREADY_REGISTERED);
		userBean.setBean(applicationBean);
		userBean.setRoleBean(roleBean);
		userBean.setLoginBean(loginBean);
		
		List<GroupBean> groupBeans=new ArrayList<GroupBean>();
		groupBeans.add(groupBean);
		userBean.setGrpBeans(groupBeans);
		
		Session session=HibernateUtils.getNewHibernateSession();
		Transaction transaction=session.beginTransaction();
		session.save(userBean);
		transaction.commit();
		session.close();
		sender.send(payment.getEmail(), EmailTemplates._applicationRegistertemplate.replace("_APPNAME_", applicationBean.getAppName()).replace("_APPID_", applicationBean.getAppId()), "no-reply");
		log.debug("Exiting registerApplication method ");
		return appId;
		
	}




	@Override
	public ApplicationBean getApplicationByAppId(String appId) {
		log.debug("Inside getApplicationByAppId method ");
		Session session=HibernateUtils.getNewHibernateSession();
		Criteria criteria=session.createCriteria(ApplicationBean.class);
		criteria.add(Restrictions.eq("appId", appId));
		List<ApplicationBean> list=criteria.list();
		session.close();
		log.debug("Exiting getApplicationByAppId method ");
		if(list.size()>=1)
		return list.get(0);
		else return null;
		
	}


}
