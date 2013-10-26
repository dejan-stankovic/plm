/************************************************************
Filename: JSONUserStory.java
Author: Christian Heckendorf
Created date: 10/22/2013
Purpose: Holds the public user story information
Feature: User Story
************************************************************/
package edu.cs673.plm.model;

public class JSONUserStory{
	long id;
	String name;
	String description;

	/************************************************************
	Function name: JSONUserStory()
	Author: Christian Heckendorf
	Created date: 10/22/2013
	Purpose: Default constructor
	************************************************************/
	public JSONUserStory(){
	}

	public UserStory toUserStory(){
		UserStory ret = new UserStory();
		ret.setName(name);
		ret.setDescription(description);
		return ret;
	}

	/************************************************************
	Function name: JSONUserStory()
	Author: Christian Heckendorf
	Created date: 10/22/2013
	Purpose: Constructs a JSONUserStory from a UserStory
	************************************************************/
	public JSONUserStory(UserStory userStory){
		id = userStory.getId();
		name = userStory.getName();
		description = userStory.getDescription();
	}

	/************************************************************
	Function name: getId
	Author: Christian Heckendorf
	Created date: 10/22/2013
	Purpose: Returns the user ID
	************************************************************/
	public long getId(){
		return id;
	}

	/************************************************************
	Function name: setId
	Author: Christian Heckendorf
	Created date: 10/22/2013
	Purpose: Sets the user ID
	************************************************************/
	public void setId(long id){
		this.id = id;
	}

	/************************************************************
	Function name: getName
	Author: Christian Heckendorf
	Created date: 10/22/2013
	Purpose: Returns the name
	************************************************************/
	public String getName(){
		return name;
	}

	/************************************************************
	Function name: setName
	Author: Christian Heckendorf
	Created date: 10/22/2013
	Purpose: Sets the name
	************************************************************/
	public void setName(String name){
		this.name = name;
	}

	/************************************************************
	Function name: getDescription()
	Author: Christian Heckendorf
	Created date: 10/22/2013
	Purpose: Returns a description
	************************************************************/
	public String getDescription(){
		return description;
	}

	/************************************************************
	Function name: setDescription()
	Author: Christian Heckendorf
	Created date: 10/22/2013
	Purpose: Sets a description
	************************************************************/
	public void setDescription(String description){
		this.description = description;
	}
}
