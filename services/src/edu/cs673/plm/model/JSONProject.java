/************************************************************
Filename: JSONProject.java
Author: Christian Heckendorf
Created date: 10/07/2013
Purpose: Holds the public fields of a Project
Feature: None
************************************************************/
package edu.cs673.plm.model;

public class JSONProject{
	long id;
	String name;

	/************************************************************
	Function name: JSONProject()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Default constructor
	************************************************************/
	public JSONProject(){
	}

	/************************************************************
	Function name: JSONProject()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Constructs a JSONProject from a Project
	************************************************************/
	public JSONProject(Project project){
		id = project.getId();
		name = project.getName();
	}

	/************************************************************
	Function name: getId
	Author: Christian Heckendorf
	Created date: 10/5/2013
	Purpose: Returns the project ID
	************************************************************/
	public long getId(){
		return id;
	}

	/************************************************************
	Function name: setId
	Author: Christian Heckendorf
	Created date: 10/5/2013
	Purpose: Sets the project ID
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
