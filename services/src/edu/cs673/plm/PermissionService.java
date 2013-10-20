/************************************************************
Filename: PermissionService.java
Author: Christian Heckendorf
Created date: 10/05/2013
Purpose: Service to get permissions
Feature: None
************************************************************/
package edu.cs673.plm;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import edu.cs673.plm.model.TokenMessage;
import edu.cs673.plm.model.StatusMessage;
import edu.cs673.plm.model.User;
import edu.cs673.plm.model.Role;
import edu.cs673.plm.model.UserProject;
import edu.cs673.plm.model.PermissionList;

@Path( "/permission" )
public class PermissionService {
	/************************************************************
	Function name: getUserPermissions()
	Author: Christian Heckendorf
	Created date: 10/05/2013
	Purpose: Returns a list of permissions available to a user
	************************************************************/
	@Path( "/p/{pid}" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PermissionList getUserPermissions(@PathParam("pid") long pid, TokenMessage token) {
		PermissionList pl = new PermissionList();
		Dba dba = new Dba(true);
		SessionToken st = new SessionToken(token.getToken());

		if(st.getUid()==0){
			return null;
		}

		try{
			User user = UserDao.getUserById(dba,st.getUid());
			UserProject up = UserProjectDao.findUserProjectByPid(dba,st.getUid(),pid);
			pl.setPermissions(Permission.getPermissions(up.getRole().getId()));
		} finally{
			dba.closeEm();
		}

		return pl;
	}
}
