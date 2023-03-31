package com.rain.dao.role;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rain.utils.HibernateUtils;

public class RoleDaoImpl implements RoleDao {

	@Override
	public void invokeDefaultRoles() {
		Session session=HibernateUtils.getNewHibernateSession();
		Criteria  criteria=session.createCriteria(RoleBean.class);
		List<RoleBean> roleBean=criteria.list();
		if(roleBean.size()<=0){
		Transaction transaction=session.beginTransaction();
		for(RoleBean bean:RoleBean.setDefaultRoles())
		{
			session.save(bean);
		}
		transaction.commit();
		session.close();
		}

	}

}
