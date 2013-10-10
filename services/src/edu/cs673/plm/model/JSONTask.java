/************************************************************
Filename: JSONTask.java
Author: Christian Heckendorf
Created date: 10/07/2013
Purpose: Holds the public fields of a Task
Feature: None
************************************************************/
package edu.cs673.plm.model;

public class JSONTask{
	long id;
	String name;

	/************************************************************
	Function name: JSONTask()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Default constructor
	************************************************************/
	public JSONTask(){
	}

	/************************************************************
	Function name: JSONTask()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Constructs a JSONTask from a Task
	************************************************************/
	public JSONTask(Task task){
		id = task.getId();
		name = task.getName();
	}

	/************************************************************
	Function name: getId
	Author: Christian Heckendorf
	Created date: 10/5/2013
	Purpose: Returns the task ID
	************************************************************/
	public long getId(){
		return id;
	}

	/************************************************************
	Function name: setId
	Author: Christian Heckendorf
	Created date: 10/5/2013
	Purpose: Sets the task ID
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
