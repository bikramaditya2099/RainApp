package com.rain.dao.login;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rain.RainController;
import com.rain.dao.user.UserBean;
import com.rain.dao.user.UserDao;
import com.rain.dao.user.UserDaoImpl;
import com.rain.dao.user.UserResponseBean;
import com.rain.utils.DAOUtils;
import com.rain.utils.EncoderAndDecoderUtils;
import com.rain.utils.HibernateUtils;
import com.rain.utils.RainDateFormat;
import com.rain.utils.RainException;
import com.rain.utils.RainExceptionEnum;

@Component
public class LoginDaoImpl implements LoginDao{
	@Autowired
	private UserDao userdao;
	@Autowired
	private RainDateFormat dateFormat;
	@Autowired
	private DAOUtils daoUtils;

	private static Logger log = LoggerFactory.getLogger(RainController.class);
	@Override
	public String createLogin(LoginBean bean) throws RainException {
		log.debug("Entered createLogin DAO");
		if(bean.getApplicationBean().getLeftUsers()==0)
			throw new RainException(RainExceptionEnum.INSUFFICIENT_USER);
		bean.getApplicationBean().setLeftUsers(bean.getApplicationBean().getLeftUsers()-1);
		bean.setUserName(EncoderAndDecoderUtils.encrypt(bean.getUserName()));
		bean.setPassword(EncoderAndDecoderUtils.encrypt(bean.getPassword()));
		String sso=EncoderAndDecoderUtils.encrypt(bean.getUserName().concat("~~")+bean.getPassword());
		bean.setSso(sso);
		Session session=HibernateUtils.getNewHibernateSession();
		Transaction transaction=session.beginTransaction();
		session.save(bean);
		transaction.commit();
		session.close();
		log.debug("Exiting createLogin DAO");
		return sso;
	}

	@Override
	public UserBean authenticate(String userName, String password, String appId) throws RainException {
		log.debug("Entered authenticate DAO");
		UserBean bean=userdao.getUserByUserEmail(appId, userName);
		if(bean==null)
			throw new RainException(RainExceptionEnum.USER_NOT_EXIST);
		
		LoginBean loginBean=bean.getLoginBean();
		String dPass=EncoderAndDecoderUtils.decrypt(loginBean.getPassword());
		if(!password.equals(dPass))
			throw new RainException(RainExceptionEnum.INVALID_CREDENTIAL);
		
		loginBean.setSso(userdao.generateSSO(loginBean, appId));
		updateLogin(loginBean);
		UserResponseBean responseBean=new UserResponseBean();
		responseBean.setDob(dateFormat.format(bean.getDob()));
		responseBean.setEmail(bean.getEmail());
		responseBean.setFirstName(bean.getFirstName());
		responseBean.setGender(bean.getGender());
		responseBean.setLastName(bean.getLastName());
		responseBean.setPhone(bean.getPhone());
		responseBean.setSso(loginBean.getSso());
		responseBean.setRole(bean.getRoleBean().getRole());
		log.debug("Exiting authenticate DAO");
		throw new RainException(RainExceptionEnum.SUCCESSFULL_LOGIN,responseBean);
		
		
	}

	@Override
	public void updateLogin(LoginBean bean) {
		Session session=HibernateUtils.getNewHibernateSession();
		Transaction transaction=session.beginTransaction();
		session.update(bean);
		transaction.commit();
		session.close();
		
	}

	@Override
	public boolean authenticateBySSO(String sso) throws RainException{
		String[] credentials=daoUtils.getUserCredentialBySSO(sso);
		String userName=credentials[0];
		String password=credentials[1];
		String appId=credentials[2];
		
		UserBean bean=userdao.getUserByUserEmail(appId, EncoderAndDecoderUtils.decrypt(userName));
		if(bean==null)
			return false;
		
		LoginBean loginBean=bean.getLoginBean();
		String dPass=EncoderAndDecoderUtils.decrypt(loginBean.getPassword());
		if(!EncoderAndDecoderUtils.decrypt(password).equals(dPass))
			return false;
		
		return true;
		
		
	}
	
	
	

	

	
}
