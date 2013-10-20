/************************************************************
Filename: TokenMessage.java
Author: Christian Heckendorf
Created date: 09/29/2013
Purpose: Holds a token message
Feature: Renew token
************************************************************/
package edu.cs673.plm.model;

public class TokenMessage{
	private String token;

	/************************************************************
	Function name: getToken
	Author: Christian Heckendorf
	Created date: 09/29/2013
	Purpose: Returns the token
	************************************************************/
	public String getToken(){
		return token;
	}

	/************************************************************
	Function name: setToken
	Author: Christian Heckendorf
	Created date: 09/29/2013
	Purpose: Sets a token
	************************************************************/
	public void setToken(String token){
		this.token=token;
	}
}
