/************************************************************
Filename: JSONReleaseRequest.java
Author: Christian Heckendorf
Created date: 10/26/2013
Purpose: Holds a token and release for requests
Feature: Project manage
************************************************************/
package edu.cs673.plm.model;

public class JSONReleaseRequest{
	private TokenMessage token;
	private JSONRelease release;

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
	Function name: getRelease()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Returns a release
	************************************************************/
	public JSONRelease getRelease(){
		return release;
	}

	/************************************************************
	Function name: setRelease()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Sets a release
	************************************************************/
	public void setRelease(JSONRelease release){
		this.release = release;
	}
}
