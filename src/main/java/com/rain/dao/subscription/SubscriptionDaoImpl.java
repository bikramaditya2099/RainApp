package com.rain.dao.subscription;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rain.utils.EmailTemplates;
import com.rain.utils.EncoderAndDecoderUtils;
import com.rain.utils.HibernateUtils;
import com.rain.utils.MailSender;
import com.rain.utils.RainException;
import com.rain.utils.RainExceptionEnum;

@Component
public class SubscriptionDaoImpl implements SubscriptionDao {

	@Autowired
	private MailSender sender;
	
	
	@Override
	public SubscriptionBean getSubscriptionByEmail(String emailId) {
		Session session=HibernateUtils.getNewHibernateSession();
		Criteria criteria = session.createCriteria(Payment.class);
		criteria.add(Restrictions.eq("email", emailId));
		List<Payment> payList=criteria.list();
		if(payList.size()<=0)
		{return null;}
		else
		return payList.get(0).getSubscriptionBean();
	}



	@Override
	public String subscribe(Payment payment,int subScribeId) throws RainException {
		
		if(getSubscriptionByEmail(payment.getEmail())!=null)
			throw new RainException(RainExceptionEnum.ALREADY_SUBSCRIBED);
		SubscriptionBean subscriptionBean=getSubscriptionById(subScribeId);
		Session session=HibernateUtils.getNewHibernateSession();
		payment.setSubscriptionBean(subscriptionBean);
		payment.setPaymentId(EncoderAndDecoderUtils.encrypt(String.valueOf(System.currentTimeMillis())));
		Transaction transaction=session.beginTransaction();
		session.save(payment);
		transaction.commit();
		session.close();
		
		sender.send(payment.getEmail(), EmailTemplates._subscriptiontemplate.replace("_PAYMENTID_", payment.getPaymentId()), "no-reply");
		return payment.getPaymentId();
	}
	


	public SubscriptionBean getSubscriptionById(int id) {
		
		Session session=HibernateUtils.getNewHibernateSession();
		SubscriptionBean subscriptionBean=(SubscriptionBean) session.get(SubscriptionBean.class,id);
		session.close();
		return subscriptionBean;
	}


	@Override
	public List<SubscriptionBean> getSubscriptionList() {
		Session session=HibernateUtils.getNewHibernateSession();
		Criteria criteria=session.createCriteria(SubscriptionBean.class);
		List<SubscriptionBean> subList=criteria.list();
		session.close();
		return subList;
	}
	
	
	



	@Override
	public void invokeDefaultSubscriptionValues() {
		
		if(getSubscriptionList()==null || getSubscriptionList().size()==0)
		{
	
		SubscriptionBean bean1=new SubscriptionBean(1, "demo",5, 0);
		SubscriptionBean bean2=new SubscriptionBean(2, "basic",30, 15000);
		SubscriptionBean bean3=new SubscriptionBean(3, "standard",150,50000);
		SubscriptionBean bean4=new SubscriptionBean(4, "pro",500, 100000);
		SubscriptionBean bean5=new SubscriptionBean(5, "unlimited",1000000,1000000);
		
		List<SubscriptionBean> beans=new ArrayList<SubscriptionBean>();
		beans.add(bean1);
		beans.add(bean2);
		beans.add(bean3);
		beans.add(bean4);
		beans.add(bean5);
		
		Session session=HibernateUtils.getNewHibernateSession();
		Transaction tx=session.beginTransaction();
		
		
		for(SubscriptionBean bean:beans)
				session.save(bean);
			
		tx.commit();
		session.close();
		}
		
	}


}
