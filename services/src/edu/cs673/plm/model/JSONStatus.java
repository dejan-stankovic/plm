/************************************************************
Filename: JSONStatus.java
Author: Christian Heckendorf
Created date: 11/02/2013
Purpose: Holds the public fields of a Status
Feature: None
************************************************************/
package edu.cs673.plm.model;

public class JSONStatus{
	long id;
	String name;

	/************************************************************
	Function name: JSONStatus()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Default constructor
	************************************************************/
	public JSONStatus(){
	}

	/************************************************************
	Function name: JSONStatus()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Constructs a JSONStatus from a Status
	************************************************************/
	public JSONStatus(Status status){
		id = status.getId();
		name = status.getName();
	}

	/************************************************************
	Function name: getId
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Returns the status ID
	************************************************************/
	public long getId(){
		return id;
	}

	/************************************************************
	Function name: setId
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Sets the status ID
	************************************************************/
	public void setId(long id){
		this.id = id;
	}

	/************************************************************
	Function name: getname
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Returns the name
	************************************************************/
	public String getname(){
		return name;
	}

	/************************************************************
	Function name: setname
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Sets the name
	************************************************************/
	public void setname(String name){
		this.name = name;
	}
}
