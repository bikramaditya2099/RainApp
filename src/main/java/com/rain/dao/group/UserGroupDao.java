package com.rain.dao.group;

import java.util.List;

import com.rain.utils.RainException;

public interface UserGroupDao {

	List<GroupBean> getGroupsByAppId(String appId);
	
	void createGroup(String grpName,String appId,String sso) throws RainException;
	void createSubGroup(String grpName,String grpRef,String appId,String sso) throws RainException;
	SubGroupBean getSubGroupByGroupId(String groupId,String appId,String sso) throws RainException ;
	List<SubGroupBean> getSubgroupsByRefId(String refId,String appId,String sso) throws RainException;
	Group getAllGroupsByGroupId(String groupId,String appId,String sso) throws RainException;
	List<SubGroupBean> getSubGroupByParentGroupId(String groupId,String appId,String sso) throws RainException ;
	
	List<GroupBean> getAllGroups(String appId,String sso) throws RainException ;
	
	 List<Group> getAllGroupsAndSubGroups(String appId,String sso) throws RainException ;
	 
	 void mapUserToGroup(String userId,String groupId,String appId,String sso)throws RainException ;
}
