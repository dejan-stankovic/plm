/************************************************************
Filename: JSONStoryRequest.java
Author: Christian Heckendorf
Created date: 10/22/2013
Purpose: Holds a token and user story for requests
Feature: User Story
************************************************************/
package edu.cs673.plm.model;

public class JSONStoryRequest{
	private TokenMessage token;
	private JSONUserStory userStory;

	/************************************************************
	Function name: getToken()
	Author: Christian Heckendorf
	Created date: 10/22/2013
	Purpose: Returns a token
	************************************************************/
	public TokenMessage getToken(){
		return token;
	}

	/************************************************************
	Function name: setToken()
	Author: Christian Heckendorf
	Created date: 10/22/2013
	Purpose: Sets a token
	************************************************************/
	public void setToken(TokenMessage token){
		this.token = token;
	}

	/************************************************************
	Function name: getJSONUserStory()
	Author: Christian Heckendorf
	Created date: 10/22/2013
	Purpose: Returns a user story
	************************************************************/
	public JSONUserStory getJSONUserStory(){
		return userStory;
	}

	/************************************************************
	Function name: setJSONUserStory()
	Author: Christian Heckendorf
	Created date: 10/22/2013
	Purpose: Sets a user story
	************************************************************/
	public void setJSONUserStory(JSONUserStory userStory){
		this.userStory = userStory;
	}
}
