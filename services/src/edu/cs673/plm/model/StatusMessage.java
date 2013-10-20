/************************************************************
Filename: StatusMessage.java
Author: Christian Heckendorf
Created date: 09/29/2013
Purpose: Holds a generic status message
Feature: Register
************************************************************/
package edu.cs673.plm.model;

public class StatusMessage{
	private int code;
	private String message;

	/************************************************************
	Function name: StatusMessage
	Author: Christian Heckendorf
	Created date: 09/29/2013
	Purpose: Constructs a message
	************************************************************/
	public StatusMessage(int code, String message){
		this.code=code;
		this.message=message;
	}

	/************************************************************
	Function name: getCode
	Author: Christian Heckendorf
	Created date: 09/29/2013
	Purpose: Returns the code
	************************************************************/
	public int getCode(){
		return code;
	}

	/************************************************************
	Function name: setCode
	Author: Christian Heckendorf
	Created date: 09/29/2013
	Purpose: Sets the code
	************************************************************/
	public void setCode(int code){
		this.code=code;
	}

	/************************************************************
	Function name: getMessage
	Author: Christian Heckendorf
	Created date: 09/29/2013
	Purpose: Returns the message
	************************************************************/
	public String getMessage(){
		return message;
	}

	/************************************************************
	Function name: setMessage
	Author: Christian Heckendorf
	Created date: 09/29/2013
	Purpose: Sets a message
	************************************************************/
	public void setMessage(String message){
		this.message=message;
	}
}
