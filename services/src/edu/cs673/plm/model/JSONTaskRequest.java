/************************************************************
Filename: JSONTaskRequest.java
Author: Christian Heckendorf
Created date: 10/27/2013
Purpose: Holds a token and task for requests
Feature: Task
************************************************************/
package edu.cs673.plm.model;

public class JSONTaskRequest{
	private TokenMessage token;
	private JSONTask task;

	/************************************************************
	Function name: getToken()
	Author: Christian Heckendorf
	Created date: 10/27/2013
	Purpose: Returns a token
	************************************************************/
	public TokenMessage getToken(){
		return token;
	}

	/************************************************************
	Function name: setToken()
	Author: Christian Heckendorf
	Created date: 10/27/2013
	Purpose: Sets a token
	************************************************************/
	public void setToken(TokenMessage token){
		this.token = token;
	}

	/************************************************************
	Function name: getJSONTask()
	Author: Christian Heckendorf
	Created date: 10/27/2013
	Purpose: Returns a task
	************************************************************/
	public JSONTask getTask(){
		return task;
	}

	/************************************************************
	Function name: setJSONTask()
	Author: Christian Heckendorf
	Created date: 10/27/2013
	Purpose: Sets a task
	************************************************************/
	public void setTask(JSONTask task){
		this.task = task;
	}
}
