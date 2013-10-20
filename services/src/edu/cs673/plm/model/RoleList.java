/************************************************************
Filename: RoleList.java
Author: Christian Heckendorf
Created date: 10/04/2013
Purpose: Holds a list of roles
Feature: Role management
************************************************************/
package edu.cs673.plm.model;

import java.util.List;
import java.util.ArrayList;

public class RoleList{
	private List<JSONRole> roles;

	/************************************************************
	Function name: RoleList()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Default constructor
	************************************************************/
	public RoleList(){
		roles = new ArrayList<JSONRole>();
	}

	/************************************************************
	Function name: getRoles()
	Author: Christian Heckendorf
	Created date: 10/04/2013
	Purpose: Adds a role to the list
	************************************************************/
	public void addRole(Role role){
		roles.add(new JSONRole(role));
	}

	/************************************************************
	Function name: setRoles()
	Author: Christian Heckendorf
	Created date: 10/04/2013
	Purpose: Returns the roles
	************************************************************/
	public List<JSONRole> getRoles(){
		return roles;
	}

	/************************************************************
	Function name: setRoles()
	Author: Christian Heckendorf
	Created date: 10/04/2013
	Purpose: Sets the roles
	************************************************************/
	public void setRoles(List<JSONRole> roles){
		this.roles=roles;
	}
}
