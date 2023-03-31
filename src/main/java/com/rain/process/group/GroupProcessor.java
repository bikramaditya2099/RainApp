package com.rain.process.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rain.beans.GroupMappingBean;
import com.rain.beans.GroupReqBean;
import com.rain.dao.group.Group;
import com.rain.dao.group.UserGroupDao;
import com.rain.utils.RainException;
import com.rain.utils.RainExceptionEnum;

@Component
public class GroupProcessor {

	@Autowired
	private UserGroupDao groupDao;

	public void processCreateGroup(GroupReqBean grpReqBean, String appId,
			String sso) throws RainException {
		
		if(grpReqBean.getGrpRef()!=null){
			groupDao.createSubGroup(grpReqBean.getGroupName(), grpReqBean.getGrpRef(), appId, sso);
		}
		else
		groupDao.createGroup(grpReqBean.getGroupName(), appId, sso);

	}
	
	public void getAllGroupsAndSubGroups(String appId,String sso) throws RainException
	{
		List<Group> groups=groupDao.getAllGroupsAndSubGroups(appId, sso);
		
		throw new RainException(RainExceptionEnum.SUCCESSFULL_GROUP_RETRIEVE,groups);
	}
	
	public void mapUser( GroupMappingBean grpMapBean ,String appId,String sso) throws RainException
	{
		groupDao.mapUserToGroup(grpMapBean.getUserId(), grpMapBean.getGroupId(), appId, sso);
	}
}
