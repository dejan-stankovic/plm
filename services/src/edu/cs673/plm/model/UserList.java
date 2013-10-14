/************************************************************
Filename: UserList.java
Author: Christian Heckendorf
Created date: 10/05/2013
Purpose: Holds a list of JSONUsers
Feature: Role management
************************************************************/
package edu.cs673.plm.model;

import java.util.List;
import java.util.ArrayList;

public class UserList{
	private List<JSONUser> users;

	/************************************************************
	Function name: UserList()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Default constructor
	************************************************************/
	public UserList(){
		users = new ArrayList<JSONUser>();
	}

	/************************************************************
	Function name: addUser()
	Author: Christian Heckendorf
	Created date: 10/05/2013
	Purpose: Adds a user to the list
	************************************************************/
	public void addUser(User user){
		users.add(new JSONUser(user));
	}

	/************************************************************
	Function name: addUsers()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Adds a list of users
	************************************************************/
	public void addUsers(List<User> users){
		for(User u : users){
			this.addUser(u);
		}
	}

	/************************************************************
	Function name: getUsers()
	Author: Christian Heckendorf
	Created date: 10/05/2013
	Purpose: Returns the list of users
	************************************************************/
	public List<JSONUser> getUsers(){
		return users;
	}

	/************************************************************
	Function name: setUsers()
	Author: Christian Heckendorf
	Created date: 10/05/2013
	Purpose: Sets the list of users
	************************************************************/
	public void setUsers(List<JSONUser> users){
		this.users=users;
	}
}
