/***************************************************************
Filename: HelloMessage.java
Author: Christian Heckendorf
Created Date: 9/21/13
Purpose: Holds a message
Features: None
***************************************************************/
package edu.cs673.plm.model;

public class HelloMessage{
	private String message;

	/***************************************************************
	Function name: getMessage
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Gets a message
	***************************************************************/
	public String getMessage(){
		return message;
	}

	/***************************************************************
	Function name: setMessage
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Sets a message
	***************************************************************/
	public void setMessage(String message){
		this.message=message;
	}
}
