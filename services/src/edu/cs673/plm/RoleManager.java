/************************************************************
Filename: RoleManager.java
Author: Christian Heckendorf
Created date: 10/01/2013
Purpose: Manages roles
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
import edu.cs673.plm.model.RoleList;
import edu.cs673.plm.model.JSONUserRole;
import edu.cs673.plm.model.UserProject;

@Path( "/rolemanage" )
public class RoleManager {
	/************************************************************
	Function name: getRoles
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Returns a list of available roles
	************************************************************/
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RoleList getRoles(TokenMessage token){
		Dba dba = new Dba(true);
		try{
			return RoleDao.getRoleList(dba);
		} catch(Exception e){
			return null;
		}
	}

	/************************************************************
	Function name: getUserRole
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Gets the current role for the user
	************************************************************/
	@Path( "/p/{pid}/u/{uid}" )
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONUserRole getUserRole(@PathParam("pid") long pid, @PathParam("uid") long uid, TokenMessage token) {
		JSONUserRole ur = null;
		Dba dba = new Dba(true);
		try{
			User user = UserDao.getUserById(dba,uid);
			UserProject up = UserProjectDao.findUserProjectByPid(dba,uid,pid);
			ur = new JSONUserRole(user,up.getRole());
		} finally{
			dba.closeEm();
		}

		return ur;
	}
}
