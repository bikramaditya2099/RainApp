package com.rain.dao.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rain.dao.application.ApplicationBean;
import com.rain.dao.application.ApplicationContainer;
import com.rain.dao.application.ApplicationDao;
import com.rain.dao.application.ApplicationDaoImpl;
import com.rain.dao.group.GroupBean;
import com.rain.dao.group.UserGroupDao;
import com.rain.dao.login.LoginBean;
import com.rain.dao.role.RoleBean;
import com.rain.dao.subscription.Payment;
import com.rain.utils.DAOUtils;
import com.rain.utils.EncoderAndDecoderUtils;
import com.rain.utils.HibernateUtils;
import com.rain.utils.RainException;
import com.rain.utils.RainExceptionEnum;

@Component
public class UserDaoImpl implements UserDao {

	@Autowired
	private UserGroupDao usergrpDao;
	@Autowired
	private DAOUtils daoUtils;
	
	@Override
	public List<UserBean> getUsersByAppId(long id) {
		Session session=HibernateUtils.getNewHibernateSession();
		Criteria criteria = session.createCriteria(UserBean.class, "userBean");
		criteria.createAlias("userBean.bean", "appBean");
		criteria.add(Restrictions.eq("appBean.id",id));
		
		List<UserBean> list=criteria.list();
		
		session.close();
		return list;
	}
	
	@Override
	public List<UserBean> getUsersByApplicationId(String id,String sso) throws RainException {
		boolean con = daoUtils.checkForAdmin(EncoderAndDecoderUtils
				.decrypt(daoUtils.getUserCredentialBySSO(sso)[0]), id);
		if (!con)
			throw new RainException(RainExceptionEnum.NOT_AN_ADMIN);
		Session session=HibernateUtils.getNewHibernateSession();
		Criteria criteria = session.createCriteria(UserBean.class, "userBean");
		criteria.createAlias("userBean.bean", "appBean");
		criteria.add(Restrictions.eq("appBean.appId",id));
		
		List<UserBean> list=criteria.list();
		
		session.close();
		return list;
	}

	@Override
	public void registerUser(ApplicationContainer applicationContainer)
			throws RainException {
		
		ApplicationDao applicationDao=new ApplicationDaoImpl();
		UserBean userBean=applicationContainer.getUserBean();
		LoginBean loginBean=applicationContainer.getLoginBean();
		RoleBean roleBean=applicationContainer.getBean();
		String appId=applicationContainer.getPaymentId();
		

		UserDao userDao=new UserDaoImpl();
		UserBean userBeanD=userDao.getUserByUserEmail(appId, userBean.getEmail());
		if(userBeanD!=null)
			throw new RainException(RainExceptionEnum.USER_ALREADY_REGISTERED);
		
		ApplicationBean abean=applicationDao.getApplicationByAppId(appId);
		if(abean==null)
			throw new RainException(RainExceptionEnum.INVALID_APP_ID);
		if(abean.getLeftUsers()==0)
			throw new RainException(RainExceptionEnum.INSUFFICIENT_USER);
		
		
		
		
		
		
		if(loginBean.getSsoAutoUpdate()==null)
			loginBean.setSsoAutoUpdate("N");
		else if(loginBean.getSsoAutoUpdate().length()<=0)
			loginBean.setSsoAutoUpdate("Y");
		
		loginBean.setUserName(EncoderAndDecoderUtils.encrypt(loginBean.getUserName()));
		loginBean.setPassword(EncoderAndDecoderUtils.encrypt(loginBean.getPassword()));
		String sso=generateSSO(loginBean, appId);
		loginBean.setSso(sso);
		loginBean.setApplicationBean(abean);
		
		List<LoginBean> _beans=new ArrayList<LoginBean>();
		_beans.add(loginBean);
		
		abean.setLeftUsers(abean.getLeftUsers()-1);
		abean.setLoginBeans(_beans);
		
		userBean.setBean(abean);
		userBean.setRoleBean(roleBean);
		userBean.setLoginBean(loginBean);
		
		userBean.setGrpBeans(usergrpDao.getGroupsByAppId(appId));
		
		
		Session session=HibernateUtils.getNewHibernateSession();
		Transaction transaction=session.beginTransaction();
		session.saveOrUpdate(userBean);
		transaction.commit();
		session.close();
		
		
		
	}

	@Override
	public String generateSSO(LoginBean loginBean, String appId) {
		return EncoderAndDecoderUtils.encrypt(loginBean.getUserName().concat(EncoderAndDecoderUtils.delim)+loginBean.getPassword().concat(EncoderAndDecoderUtils.delim)+appId);
	}

	@Override
	public UserBean getUserByUserEmail(String appId,String email) {
		
		
		Session session=HibernateUtils.getNewHibernateSession();
		Criteria criteria = session.createCriteria(UserBean.class, "userBean");
		criteria.createAlias("userBean.bean", "appBean");
		criteria.add(Restrictions.and(Restrictions.eq("appBean.appId", appId), Restrictions.eq("email", email)));
		
		List<UserBean> userBeans=criteria.list();
	
		session.close();
		if(userBeans.size()>0)
			return userBeans.get(0);
		else
		return null;
	}
	

	
	
	

}
