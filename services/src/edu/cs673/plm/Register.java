package edu.cs673.plm;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import edu.cs673.plm.model.StatusMessage;
import edu.cs673.plm.model.User;

@Path( "/register" )
public class Register {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusMessage getRegisterMessage(User user) {
		Dba dba = new Dba();
		StatusMessage sm;
		try{
			sm=UserDao.createUser(dba,user);
		} finally{
			dba.closeEm();
		}

		return sm;
	}
}
