/************************************************************
Filename: JSONBug.java
Author: Christian Heckendorf
Created date: 10/13/2013
Purpose: Holds the public fields of a Bug
Feature: None
************************************************************/
package edu.cs673.plm.model;

public class JSONBug{
	long id;
	String name;
	String category;
	String priority;
	String risk;
	String status;

	/************************************************************
	Function name: JSONBug()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Default constructor
	************************************************************/
	public JSONBug(){
	}

	/************************************************************
	Function name: JSONBug()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Constructs a JSONBug from a Bug
	************************************************************/
	public JSONBug(Bug task){
		id = task.getId();
		name = task.getName();

		/* TODO: When the database is filled in, link these up */
		category = new String("Development");
		priority = new String("Low");
		risk = new String("High");
		status = new String("Initial");
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

	/************************************************************
	Function name: getCategory()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Returns the category
	************************************************************/
	public String getCategory(){
		return category;
	}

	/************************************************************
	Function name: setCategory()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Sets the category
	************************************************************/
	public void setCategory(String category){
		this.category = category;
	}

	/************************************************************
	Function name: getPriority()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Returns the priority
	************************************************************/
	public String getPriority(){
		return priority;
	}

	/************************************************************
	Function name: setPriority()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Sets the priority
	************************************************************/
	public void setPriority(String priority){
		this.priority = priority;
	}

	/************************************************************
	Function name: getRisk()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Returns the risk
	************************************************************/
	public String getRisk(){
		return risk;
	}

	/************************************************************
	Function name: setRisk()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Sets the risk
	************************************************************/
	public void setRisk(String risk){
		this.risk = risk;
	}

	/************************************************************
	Function name: getStatus()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Returns the status
	************************************************************/
	public String getStatus(){
		return status;
	}

	/************************************************************
	Function name: setStatus()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Sets the status
	************************************************************/
	public void setStatus(String status){
		this.status = status;
	}
}
