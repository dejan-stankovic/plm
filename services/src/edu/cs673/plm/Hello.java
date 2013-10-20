/***************************************************************
Filename: Hello.java
Author: Christian Heckendorf
Created Date: 9/21/13
Purpose: Example service
Features: None
***************************************************************/
package edu.cs673.plm;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.cs673.plm.model.HelloMessage;

@Path( "/hello" )
public class Hello
{
	/***************************************************************
	Function name: getHelloMessage
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Returns the hello message
	***************************************************************/
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public HelloMessage getHelloMessage()
	{
		HelloMessage hm = new HelloMessage();
		hm.setMessage("Hello World");
		return hm;
	}
}
