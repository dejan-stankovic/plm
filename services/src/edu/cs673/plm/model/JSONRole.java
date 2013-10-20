/***************************************************************
Filename: JSONRole.java
Author: Christian Heckendorf
Created Date: 10/04/13
Purpose: Holds the public attributes of a role
Features: None
***************************************************************/
package edu.cs673.plm.model;

public class JSONRole{
	private long id;
	private String name;

	public JSONRole(){
	}

	/************************************************************
	Function name: JSONRole()
	Author: Christian Heckendorf
	Created date: 10/04/2013
	Purpose: Initializes a JSONRole from a Role
	************************************************************/
	public JSONRole(Role role){
		id=role.getId();
		name=role.getName();
	}

	/***************************************************************
	Function name: getId
	Author: Christian Heckendorf
	Created Date: 10/04/13
	Purpose: Gets an id
	***************************************************************/
	public long getId(){
		return id;
	}

	/***************************************************************
	Function name: setId
	Author: Christian Heckendorf
	Created Date: 10/04/13
	Purpose: Sets an id
	***************************************************************/
	public void setId(long id){
		this.id=id;
	}

	/***************************************************************
	Function name: getName
	Author: Christian Heckendorf
	Created Date: 10/04/13
	Purpose: Gets a name
	***************************************************************/
	public String getName(){
		return name;
	}

	/***************************************************************
	Function name: setName
	Author: Christian Heckendorf
	Created Date: 10/04/13
	Purpose: Sets a name
	***************************************************************/
	public void setName(String name){
		this.name=name;
	}
}
