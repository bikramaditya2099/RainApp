package com.rain.dao.group;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rain.dao.application.ApplicationBean;
import com.rain.dao.login.LoginDao;
import com.rain.dao.user.UserBean;
import com.rain.utils.DAOUtils;
import com.rain.utils.EncoderAndDecoderUtils;
import com.rain.utils.HibernateUtils;
import com.rain.utils.RainException;
import com.rain.utils.RainExceptionEnum;

@Component
public class UserGroupDaoImpl implements UserGroupDao {

	@Autowired
	private DAOUtils daoUtils;
	@Autowired
	private LoginDao logindao;

	@Override
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

	@Override
	public void createGroup(String grpName, String appId, String sso)
			throws RainException {
		boolean con = daoUtils.checkForAdmin(EncoderAndDecoderUtils
				.decrypt(daoUtils.getUserCredentialBySSO(sso)[0]), appId);
		if (!con)
			throw new RainException(RainExceptionEnum.NOT_AN_ADMIN);

		boolean isAuth = logindao.authenticateBySSO(sso);
		if (!isAuth)
			throw new RainException(RainExceptionEnum.INVALID_CREDENTIAL);

		ApplicationBean applicationBean = daoUtils.getApplicationByAppId(appId);

		GroupBean grpBean = daoUtils
				.getGroupByAppidAndGroupName(appId, grpName);
		if (grpBean != null)
			throw new RainException(RainExceptionEnum.GROUP_ALREADY_EXISTS);

		Session session = HibernateUtils.getNewHibernateSession();
		GroupBean groupBean = new GroupBean();
		groupBean.setBean(applicationBean);
		groupBean.setName(grpName);
		UUID uniqueKey = UUID.randomUUID();
		groupBean.setGroupId(uniqueKey.toString());

		Transaction transaction = session.beginTransaction();
		session.save(groupBean);
		transaction.commit();
		session.close();

		throw new RainException(RainExceptionEnum.SUCCESSFULL_GROUP_CREATED,
				uniqueKey.toString());

	}

	@Override
	public void createSubGroup(String grpName, String grpRef, String appId,
			String sso) throws RainException {

		boolean con = daoUtils.checkForAdmin(EncoderAndDecoderUtils
				.decrypt(daoUtils.getUserCredentialBySSO(sso)[0]), appId);
		if (!con)
			throw new RainException(RainExceptionEnum.NOT_AN_ADMIN);

		boolean isAuth = logindao.authenticateBySSO(sso);
		if (!isAuth)
			throw new RainException(RainExceptionEnum.INVALID_CREDENTIAL);

		ApplicationBean applicationBean = daoUtils.getApplicationByAppId(appId);

		GroupBean grpBean = daoUtils.getGroupByAppidAndGroupRef(appId, grpRef);

		if (grpBean == null)
			throw new RainException(RainExceptionEnum.GROUP_ALREADY_EXISTS);

		SubGroupBean subGroupBean = daoUtils.getSubgroupByRefAndAppId(appId,
				grpRef, grpName);
		if (subGroupBean != null)
			throw new RainException(RainExceptionEnum.GROUP_ALREADY_EXISTS);

		Session session = HibernateUtils.getNewHibernateSession();
		SubGroupBean subBean = new SubGroupBean();
		subBean.setBean(grpBean);
		UUID uniqueKey = UUID.randomUUID();
		subBean.setGroupId(uniqueKey.toString());
		subBean.setName(grpName);
		subBean.setRefGrp(grpRef);

		Transaction transaction = session.beginTransaction();
		session.save(subBean);
		transaction.commit();
		session.close();

		throw new RainException(RainExceptionEnum.SUCCESSFULL_GROUP_CREATED,
				uniqueKey.toString());

	}

	@SuppressWarnings("unchecked")
	@Override
	public SubGroupBean getSubGroupByGroupId(String groupId, String appId,
			String sso) throws RainException {

		boolean isAuth = logindao.authenticateBySSO(sso);
		if (!isAuth)
			throw new RainException(RainExceptionEnum.INVALID_CREDENTIAL);

		Session session = HibernateUtils.getNewHibernateSession();
		Criteria criteria = session.createCriteria(SubGroupBean.class,
				"subGrptable");
		criteria.createAlias("subGrptable.bean", "grpTable");

		criteria.createAlias("grpTable.bean", "appTable");

		criteria.add(Restrictions.and(Restrictions.eq("appTable.appId", appId),
				Restrictions.eq("subGrptable.groupId", groupId)));

		List<SubGroupBean> subgroups = criteria.list();
		session.close();
		if (subgroups.size() > 0)
			return subgroups.get(0);
		else
			return null;
	}

	@Override
	public Group getAllGroupsByGroupId(String groupId, String appId, String sso)
			throws RainException {

		boolean isAuth = logindao.authenticateBySSO(sso);
		if (!isAuth)
			throw new RainException(RainExceptionEnum.INVALID_CREDENTIAL);

		if (daoUtils.getApplicationByAppId(appId) == null)
			throw new RainException(RainExceptionEnum.INVALID_APP_ID);

		SubGroupBean subgroup = getSubGroupByGroupId(groupId, appId, sso);
		if (subgroup == null)
			throw new RainException(RainExceptionEnum.GROUP_NOT_FOUND);
		List<SubGroupBean> subgroups = getSubgroupsByRefId(
				subgroup.getGroupId(), appId, sso);
		Group group = new Group();

		if (subgroups.size() <= 0) {
			group.setId(subgroup.getId());
			group.setName(subgroup.getName());
			group.setGroupId(subgroup.getGroupId());
			group.setGroup(null);
			return group;
		} else {
			group.setId(subgroup.getId());
			group.setName(subgroup.getName());
			group.setGroupId(subgroup.getGroupId());
			group.setGroup(getGroupBySubGroups(subgroups,
					new ArrayList<Group>(), appId, sso));
			return group;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubGroupBean> getSubgroupsByRefId(String refId, String appId,
			String sso) throws RainException {

		boolean isAuth = logindao.authenticateBySSO(sso);
		if (!isAuth)
			throw new RainException(RainExceptionEnum.INVALID_CREDENTIAL);

		if (daoUtils.getApplicationByAppId(appId) == null)
			throw new RainException(RainExceptionEnum.INVALID_APP_ID);

		Session session = HibernateUtils.getNewHibernateSession();
		Criteria criteria = session.createCriteria(SubGroupBean.class,
				"subGrptable");
		criteria.createAlias("subGrptable.bean", "grpTable");

		criteria.createAlias("grpTable.bean", "appTable");

		criteria.add(Restrictions.and(Restrictions.eq("appTable.appId", appId),
				Restrictions.eq("subGrptable.refGrp", refId)));

		List<SubGroupBean> subgroups = criteria.list();
		session.close();

		return subgroups;

	}

	public List<Group> getGroupBySubGroups(List<SubGroupBean> subgroups,
			List<Group> groups, String appId, String sso) throws RainException {

		for (SubGroupBean groupBean : subgroups) {
			Group group = new Group();
			group.setId(groupBean.getId());
			group.setName(groupBean.getName());
			group.setGroupId(groupBean.getGroupId());

			List<SubGroupBean> subgrp = getSubgroupsByRefId(
					groupBean.getGroupId(), appId, sso);
			if (subgrp.size() > 0)
				group.setGroup(getGroupBySubGroups(subgrp,
						new ArrayList<Group>(), appId, sso));

			groups.add(group);
		}
		return groups;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubGroupBean> getSubGroupByParentGroupId(String groupId,
			String appId, String sso) throws RainException {
		boolean isAuth = logindao.authenticateBySSO(sso);
		if (!isAuth)
			throw new RainException(RainExceptionEnum.INVALID_CREDENTIAL);

		Session session = HibernateUtils.getNewHibernateSession();
		Criteria criteria = session.createCriteria(SubGroupBean.class,
				"subGrptable");
		criteria.createAlias("subGrptable.bean", "grpTable");

		criteria.createAlias("grpTable.bean", "appTable");

		criteria.add(Restrictions.and(Restrictions.eq("appTable.appId", appId),
				Restrictions.eq("subGrptable.refGrp", groupId)));

		List<SubGroupBean> subgroups = criteria.list();
		session.close();

		return subgroups;
	}

	@Override
	public List<GroupBean> getAllGroups(String appId, String sso)
			throws RainException {

		boolean isAuth = logindao.authenticateBySSO(sso);
		if (!isAuth)
			throw new RainException(RainExceptionEnum.INVALID_CREDENTIAL);

		Session session = HibernateUtils.getNewHibernateSession();

		Criteria criteria = session.createCriteria(GroupBean.class, "grptable");

		criteria.createAlias("grptable.bean", "apptable");

		criteria.add(Restrictions.eq("apptable.appId", appId));

		List<GroupBean> list = criteria.list();

		if (list.size() <= 0)
			throw new RainException(RainExceptionEnum.GROUP_NOT_FOUND);
		return list;
	}

	@Override
	public  List<Group> getAllGroupsAndSubGroups(String appId, String sso)
			throws RainException {
	
		 List<GroupBean> grpList=getAllGroups(appId,sso);
		 
		 List<Group> grpLst=new ArrayList<Group>();
		 for(GroupBean grpBean : grpList)
		 {
			 Group groupBean=new Group();
			 groupBean.setId(grpBean.getId());
			 groupBean.setName(grpBean.getName());
			 groupBean.setGroupId(grpBean.getGroupId());
			 List<Group> groupList=new ArrayList<Group>();
			 List<SubGroupBean> subgroups=getSubGroupByParentGroupId(grpBean.getGroupId(), appId, sso);
			 if(subgroups.size()>0)
			 {
				for(SubGroupBean subBean:subgroups)
				{
					Group group=getAllGroupsByGroupId(subBean.getGroupId(), appId, sso);
					groupList.add(group);
				}
			 }
			 
			 groupBean.setGroup(groupList);
			 grpLst.add(groupBean);
		 }
		 
		 return grpLst;
	}

	@Override
	public void mapUserToGroup(String userId, String groupId, String appId,
			String sso) throws RainException {
		
		boolean isAuth = logindao.authenticateBySSO(sso);
		if (!isAuth)
			throw new RainException(RainExceptionEnum.INVALID_CREDENTIAL);

		if (daoUtils.getApplicationByAppId(appId) == null)
			throw new RainException(RainExceptionEnum.INVALID_APP_ID);
		
		
		String grp=daoUtils.checkGroupOrSubgroup(groupId);
		
		if(grp.equals("GP"))
		{
			UserBean bean=daoUtils.getUserByUserEmail(appId, userId);
			if(bean==null)throw new RainException(RainExceptionEnum.USER_NOT_EXIST);
			GroupBean grpBean=daoUtils.getGroupByAppidAndGroupRef(appId, groupId);
			if(grpBean==null)throw new RainException(RainExceptionEnum.GROUP_NOT_FOUND);
			Session session = HibernateUtils.getNewHibernateSession();
			UserBean ubean=(UserBean)session.get(UserBean.class,bean.getId() );
			List<GroupBean> beans=ubean.getGrpBeans();
			for(GroupBean groupBean:beans)
			{
				if(groupBean.getId()==grpBean.getId())
					throw new RainException(RainExceptionEnum.USER_ALREADY_MAPPED_TO_GROUP);
			}
			beans.add(grpBean);
			ubean.setGrpBeans(beans);
			Transaction transaction=session.beginTransaction();
			session.update(ubean);
			transaction.commit();
			session.close();
		}
		else if(grp.equals("SGP"))
		{
			UserBean bean=daoUtils.getUserByUserEmail(appId, userId);
			if(bean==null)throw new RainException(RainExceptionEnum.USER_NOT_EXIST);
			SubGroupBean subGroupBean=getSubGroupByGroupId(groupId, appId, sso);
			if(subGroupBean==null)throw new RainException(RainExceptionEnum.GROUP_NOT_FOUND);
			Session session = HibernateUtils.getNewHibernateSession();
			Criteria criteria=session.createCriteria(SubGroupUserMappingBean.class);
			criteria.add(Restrictions.and(Restrictions.eq("subGroupId", new Long(subGroupBean.getId())),Restrictions.eq("userId", bean.getId())));
			if(criteria.list().size()>0)
				throw new RainException(RainExceptionEnum.USER_ALREADY_MAPPED_TO_GROUP);
			SubGroupUserMappingBean groupUserMappingBean=new SubGroupUserMappingBean();
			groupUserMappingBean.setSubGroupId(subGroupBean.getId());
			groupUserMappingBean.setUserId(bean.getId());
			Transaction transaction=session.beginTransaction();
			session.save(groupUserMappingBean);
			transaction.commit();
			session.close();
			
		}
		
		throw new RainException(RainExceptionEnum.SUCCESSFULL_GROUP_MAPPED,groupId);
		
	}
	
	
	

}
