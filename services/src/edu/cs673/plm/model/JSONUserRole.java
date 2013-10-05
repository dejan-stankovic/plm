/************************************************************
Filename: JSONUserRole.java
Author: Christian Heckendorf
Created date: 09/30/2013
Purpose: Holds public information for JSON conversion
Feature: None
************************************************************/
package edu.cs673.plm.model;

public class JSONUserRole{
	long userId;
	String userName;
	long roleId;
	String roleName;

	/************************************************************
	Function name: JSONUserRole
	Author: Christian Heckendorf
	Created date: 09/30/2013
	Purpose: Default constructor
	************************************************************/
	public JSONUserRole(){
	}

	/************************************************************
	Function name: JSONUserRole
	Author: Christian Heckendorf
	Created date: 09/30/2013
	Purpose: Convert from user and role
	************************************************************/
	public JSONUserRole(User user, Role role){
		userId = user.getId();
		userName = user.getName();
		roleId = role.getId();
		roleName = role.getName();
	}

	/************************************************************
	Function name: getUserId
	Author: Christian Heckendorf
	Created date: 09/30/2013
	Purpose: Returns the user ID
	************************************************************/
	public long getUserId(){
		return userId;
	}

	/************************************************************
	Function name: setUserId
	Author: Christian Heckendorf
	Created date: 09/30/2013
	Purpose: Sets the user ID
	************************************************************/
	public void setUserId(long userId){
		this.userId = userId;
	}

	/************************************************************
	Function name: getUserName
	Author: Christian Heckendorf
	Created date: 09/30/2013
	Purpose: Returns the name
	************************************************************/
	public String getUserName(){
		return userName;
	}

	/************************************************************
	Function name: setUserName
	Author: Christian Heckendorf
	Created date: 09/30/2013
	Purpose: Sets the name
	************************************************************/
	public void setUserName(String userName){
		this.userName = userName;
	}

	/************************************************************
	Function name: getRoleId()
	Author: Christian Heckendorf
	Created date: 10/04/2013
	Purpose: Returns the role ID
	************************************************************/
	public long getRoleId(){
		return roleId;
	}

	/************************************************************
	Function name: setRoleId()
	Author: Christian Heckendorf
	Created date: 10/04/2013
	Purpose: Sets the role ID
	************************************************************/
	public void setRoleId(long roleId){
		this.roleId=roleId;
	}

	/************************************************************
	Function name: getRoleName
	Author: Christian Heckendorf
	Created date: 09/30/2013
	Purpose: Returns the role name
	************************************************************/
	public String getRoleName(){
		return roleName;
	}

	/************************************************************
	Function name: setRoleName
	Author: Christian Heckendorf
	Created date: 09/30/2013
	Purpose: Sets the role name
	************************************************************/
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}
}
