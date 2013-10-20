/***************************************************************
Filename: Login.java
Author: Christian Heckendorf
Created Date: 9/28/13
Purpose: Authenticates a user and creates a session token
Features: Login
***************************************************************/
package edu.cs673.plm;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import edu.cs673.plm.model.StatusMessage;
import edu.cs673.plm.model.User;

@Path( "/login" )
public class Login {
	/***************************************************************
	Function name: getLoginMessage
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Returns a session token or error
	***************************************************************/
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusMessage getLoginMessage(User user) {
		Dba dba = new Dba(true);
		long uid;
		StatusMessage sm;
		SessionToken st;
		String tok;

		try{
			uid=UserDao.getUserId(dba,user);
			if(uid>0){
				st = new SessionToken(uid);
				tok = st.generateToken();
				if(tok==null)
					sm = new StatusMessage(2,"Internal Error");
				else
					sm = new StatusMessage(0,tok);
			}
			else
				sm = new StatusMessage(1,"Invalid User");
		} finally{
			dba.closeEm();
		}

		return sm;
	}
}
