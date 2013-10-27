/************************************************************
Filename: JSONProjectRequest.java
Author: Christian Heckendorf
Created date: 10/26/2013
Purpose: Holds a token and project for requests
Feature: Project manage
************************************************************/
package edu.cs673.plm.model;

public class JSONProjectRequest{
	private TokenMessage token;
	private JSONProject project;

	/************************************************************
	Function name: getToken()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Returns a token
	************************************************************/
	public TokenMessage getToken(){
		return token;
	}

	/************************************************************
	Function name: setToken()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Sets a token
	************************************************************/
	public void setToken(TokenMessage token){
		this.token = token;
	}

	/************************************************************
	Function name: getProject()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Returns a project
	************************************************************/
	public JSONProject getProject(){
		return project;
	}

	/************************************************************
	Function name: setProject()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Sets a project
	************************************************************/
	public void setProject(JSONProject project){
		this.project = project;
	}
}
