/************************************************************
Filename: PermissionList.java
Author: Christian Heckendorf
Created date: 10/05/2013
Purpose: Holds a list of permissions
Feature: Permission management
************************************************************/
package edu.cs673.plm.model;

import java.util.List;

public class PermissionList{
	private List<String> permissions;

	/************************************************************
	Function name: setPermissions()
	Author: Christian Heckendorf
	Created date: 10/05/2013
	Purpose: Returns the permissions
	************************************************************/
	public List<String> getPermissions(){
		return permissions;
	}

	/************************************************************
	Function name: setPermissions()
	Author: Christian Heckendorf
	Created date: 10/05/2013
	Purpose: Sets the permissions
	************************************************************/
	public void setPermissions(List<String> permissions){
		this.permissions=permissions;
	}
}
