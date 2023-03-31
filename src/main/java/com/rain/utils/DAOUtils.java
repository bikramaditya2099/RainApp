package com.rain.utils;

import java.util.Base64;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rain.dao.application.ApplicationBean;
import com.rain.dao.group.GroupBean;
import com.rain.dao.group.SubGroupBean;
import com.rain.dao.login.LoginBean;
import com.rain.dao.login.LoginDao;
import com.rain.dao.subscription.Payment;
import com.rain.dao.user.UserBean;
/**
 * 
 * @author Bikramaditya Gochhayat
 *
 */
@Component
public class DAOUtils {

	@Autowired
	private LoginDao loginDao;

	private static Logger log = LoggerFactory.getLogger(DAOUtils.class);
	
	/**
	 * 
	 * @param paymentId
	 * @return Payment Object associated with the provided payment id
	 * @throws RainException
	 */

	@SuppressWarnings("unchecked")
	public Payment getPaymentByPaymentId(String paymentId) throws RainException {

		log.debug("Inside getPaymentByPaymentId method ");
		Session session = HibernateUtils.getNewHibernateSession();
		Criteria criteria = session.createCriteria(Payment.class);
		criteria.add(Restrictions.eq("paymentId", paymentId));
		List<Payment> list = criteria.list();
		if (list.size() == 0)
			throw new RainException(RainExceptionEnum.INVALID_PAYMENT_ID);
		session.close();
		log.debug("Exiting getPaymentByPaymentId method ");
		return list.get(0);
	}
	
	/**
	 * 
	 * @param paymentId
	 * @return Application Object associated with the provided payment id
	 * @throws RainException
	 */

	@SuppressWarnings("unchecked")
	public ApplicationBean getApplicationByPaymentId(String paymentId)
			throws RainException {

		log.debug("Inside getApplicationByPaymentId method ");
		Session session = HibernateUtils.getNewHibernateSession();
		Criteria criteria = session.createCriteria(ApplicationBean.class,
				"applicationBean");
		criteria.createAlias("applicationBean.payment", "pay");
		criteria.add(Restrictions.eq("pay.paymentId", paymentId));

		List<ApplicationBean> list = criteria.list();
		session.close();

		log.debug("Exiting getApplicationByPaymentId method ");
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}
	
	/**
	 * 
	 * @param appId
	 * @return list of groups associated with application id
	 */

	@SuppressWarnings("unchecked")
	public List<GroupBean> getGroupsByAppId(String appId) {

		Session session = HibernateUtils.getNewHibernateSession();
		Criteria criteria = session
				.createCriteria(GroupBean.class, "groupBean");
		criteria.createAlias("groupBean.bean", "appBean");
		criteria.add(Restrictions.eq("appBean.appId", appId));

		List<GroupBean> grBeans = criteria.list();

		session.close();

		if (grBeans.size() > 0)
			return grBeans;
		else
			return null;

	}

	/**
	 * Generates sso by applying encoding on username password and application id.
	 * The SSO length is more than 300 characters, While storing in db make sure to change the type of sso column to text
	 * @param loginBean
	 * @param appId
	 * @return SSO
	 */
	@SuppressWarnings("unused")
	private String generateSSO(LoginBean loginBean, String appId) {
		return EncoderAndDecoderUtils.encrypt(loginBean.getUserName().concat(
				EncoderAndDecoderUtils.delim)
				+ loginBean.getPassword().concat(EncoderAndDecoderUtils.delim)
				+ appId);
	}

	@SuppressWarnings("unchecked")
	public UserBean getUserByUserEmail(String appId, String email) {

		Session session = HibernateUtils.getNewHibernateSession();
		Criteria criteria = session.createCriteria(UserBean.class, "userBean");
		criteria.createAlias("userBean.bean", "appBean");
		criteria.add(Restrictions.and(Restrictions.eq("appBean.appId", appId),
				Restrictions.eq("email", email)));

		List<UserBean> userBeans = criteria.list();

		session.close();
		if (userBeans.size() > 0)
			return userBeans.get(0);
		else
			return null;
	}

	/**
	 * returns true if user is ADMIN
	 * or else returns false
	 */
	public boolean checkForAdmin(String userID, String appId) {
		UserBean userBean = getUserByUserEmail(appId, userID);

		if (userBean.getRoleBean().getId() == 1)
			return true;

		return false;

	}

	/**
	 * returns credentials in the format username - password -appId
	 * **/
	public String[] getUserCredentialBySSO(String sso)throws RainException {
		
		String decryptedString="";
		try {
			decryptedString = EncoderAndDecoderUtils.decrypt(sso);
		} catch (Exception e) {
			throw new RainException(RainExceptionEnum.INVALID_SSO);
		}
		
		return decryptedString.split(EncoderAndDecoderUtils.delim);
	}
	
	/**
	 * Applies encoding on custom encoder
	 * @param encodedString
	 * @return encoded string
	 */
	
	public String encodeWrapper(String encodedString)
	{
		return Base64.getEncoder().encodeToString(encodedString.getBytes());
	}
	
	/**
	 * Applies decoding on  encoded wrapper string
	 * @param encodedString
	 * @return decoded string
	 */
	public String decodeWrapper(String encodedbase64String)
	{
		return new String(Base64.getDecoder().decode(encodedbase64String));
	}
	
	/**
	 * 
	 * @param appId
	 * @return Application Object associated with the provided application Id
	 */
	@SuppressWarnings("unchecked")
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
	
	/**
	 * 
	 * @param appId
	 * @param groupName
	 * @return group associated with provided groupname and application id
	 */
	@SuppressWarnings("unchecked")
	public GroupBean getGroupByAppidAndGroupName(String appId,String groupName){
		Session session=HibernateUtils.getNewHibernateSession();
		
		Criteria criteria=session.createCriteria(GroupBean.class,"grptable");
		
		criteria.createAlias("grptable.bean", "apptable");
		
		criteria.add(Restrictions.and(Restrictions.eq("apptable.appId",appId ),Restrictions.eq("grptable.name", groupName)));
		
		List<GroupBean> list=criteria.list();
		
		if(list.size()>0)
			return list.get(0);
		else
		return null;
		
	}
	
	
	/**
	 * 
	 * @param appId
	 * @param groupref
	 * @return Group associated with provided application id  and group reference
	 */
	@SuppressWarnings("unchecked")
	public GroupBean getGroupByAppidAndGroupRef(String appId,String groupref){
		Session session=HibernateUtils.getNewHibernateSession();
		
		Criteria criteria=session.createCriteria(GroupBean.class,"grptable");
		
		criteria.createAlias("grptable.bean", "apptable");
		
		criteria.add(Restrictions.and(Restrictions.eq("apptable.appId",appId ),Restrictions.eq("grptable.groupId", groupref)));
		
		List<GroupBean> list=criteria.list();
		session.close();
		if(list.size()>0)
			return list.get(0);
		else
		{
			Session sess=HibernateUtils.getNewHibernateSession();
			
			Criteria crit=sess.createCriteria(SubGroupBean.class,"subGrptable");
			crit.createAlias("subGrptable.bean", "grpTable");
			
			crit.createAlias("grpTable.bean", "appTable");
			
			crit.add(Restrictions.and(Restrictions.eq("appTable.appId",appId ),Restrictions.eq("subGrptable.groupId", groupref)));
			
			List<SubGroupBean> sublist=crit.list();
			sess.close();
			if(sublist.size()>0)
			return sublist.get(0).getBean();
			else
				return null;
			
		}
		
	}
	
	/**
	 * 
	 * @param appId
	 * @param groupRef
	 * @param groupName
	 * @return subgroup associated with the provided appId, group reference and group name
	 */
	@SuppressWarnings("unchecked")
	public SubGroupBean getSubgroupByRefAndAppId(String appId,String groupRef,String groupName)
	{
		Session session=HibernateUtils.getNewHibernateSession();
		
		Criteria criteria=session.createCriteria(SubGroupBean.class,"subGrptable");
		
		criteria.createAlias("subGrptable.bean", "grpTable");
		
		criteria.createAlias("grpTable.bean", "appTable");
		
		//criteria.add(Restrictions.and(Restrictions.eq("appTable.appId",appId ),Restrictions.eq("subGrptable.refGrp", groupRef)));
		
		Criterion cond1=Restrictions.and(Restrictions.eq("appTable.appId",appId ),Restrictions.eq("subGrptable.refGrp", groupRef));
		Criterion cond2=Restrictions.eq("subGrptable.name", groupName);
		
		Criterion cond3=Restrictions.and(cond1,cond2);
		
		criteria.add(cond3);
		
		List<SubGroupBean> list=criteria.list();
		
		if(list.size()>0)
			return list.get(0);
		else
		return null;
		
	}
	
	
	public String checkGroupOrSubgroup(String groupId) throws RainException
	{
		Session session=HibernateUtils.getNewHibernateSession();
		
		Criteria criteria=session.createCriteria(GroupBean.class);
		criteria.add(Restrictions.eq("groupId", groupId));
		List<GroupBean> list=criteria.list();
		session.close();
		if(list.size()>0)
			return "GP";
		else{
			Session session1=HibernateUtils.getNewHibernateSession();
			Criteria criteria1=session1.createCriteria(SubGroupBean.class);
			criteria1.add(Restrictions.eq("groupId", groupId));
			List<SubGroupBean> list1=criteria1.list();
			session1.close();
			if(list1.size()>0)
				return "SGP";
		}
		
		throw new RainException(RainExceptionEnum.GROUP_NOT_FOUND);
	}
	
	


}
