/************************************************************
Filename: JSONUser.java
Author: Christian Heckendorf
Created date: 10/05/2013
Purpose: Holds the public user information
Feature: None
************************************************************/
package edu.cs673.plm.model;

public class JSONUser{
	long id;
	String name;

	/************************************************************
	Function name: JSONUser()
	Author: Christian Heckendorf
	Created date: 10/05/2013
	Purpose: Default constructor
	************************************************************/
	public JSONUser(){
	}

	/************************************************************
	Function name: JSONUser()
	Author: Christian Heckendorf
	Created date: 10/05/2013
	Purpose: Constructs a JSONUser from a User
	************************************************************/
	public JSONUser(User user){
		id = user.getId();
		name = user.getName();
	}

	/************************************************************
	Function name: getId
	Author: Christian Heckendorf
	Created date: 10/5/2013
	Purpose: Returns the user ID
	************************************************************/
	public long getId(){
		return id;
	}

	/************************************************************
	Function name: setId
	Author: Christian Heckendorf
	Created date: 10/5/2013
	Purpose: Sets the user ID
	************************************************************/
	public void setId(long id){
		this.id = id;
	}

	/************************************************************
	Function name: getName
	Author: Christian Heckendorf
	Created date: 10/5/2013
	Purpose: Returns the name
	************************************************************/
	public String getName(){
		return name;
	}

	/************************************************************
	Function name: setName
	Author: Christian Heckendorf
	Created date: 10/5/2013
	Purpose: Sets the name
	************************************************************/
	public void setName(String name){
		this.name = name;
	}
}
