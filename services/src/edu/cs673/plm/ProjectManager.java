/************************************************************
Filename: ProjectManager.java
Author: Christian Heckendorf
Created date: 10/05/2013
Purpose: Services for project management
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
import edu.cs673.plm.model.User;
import edu.cs673.plm.model.UserList;
import edu.cs673.plm.model.ReleaseList;
import edu.cs673.plm.model.JSONUser;
import edu.cs673.plm.model.UserProject;

@Path( "/projectmanage" )
public class ProjectManager {
	/************************************************************
	Function name: getUserList()
	Author: Christian Heckendorf
	Created date: 10/05/2013
	Purpose: Returns a list of users associated with a project
	************************************************************/
	@Path( "/p/{pid}/users" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserList getUserList(@PathParam("pid") long pid, TokenMessage token) {
		UserList ul = null;
		Dba dba = new Dba(true);
		try{
			if(Permission.canAccess(dba,new SessionToken(token.getToken()),pid,Permission.VIEW_PROJECT)){
				ul = new UserList();
				ul.setUsers(UserProjectDao.getMemberList(dba,pid));
			}
		} finally{
			dba.closeEm();
		}

		return ul;
	}

	/************************************************************
	Function name: getReleaseList()
	Author: Christian Heckendorf
	Created date: 10/08/2013
	Purpose: Gets a list of releases under a project
	************************************************************/
	@Path( "/releases/p/{pid}" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReleaseList getReleaseList(@PathParam("pid") long pid, TokenMessage token) {
		SessionToken st = new SessionToken(token.getToken());
		ReleaseList rl = null;
		Dba dba = new Dba(true);
		try{
			if(Permission.canAccess(dba,new SessionToken(token.getToken()),pid,Permission.VIEW_PROJECT)){
				rl = new ReleaseList();
				rl.setReleases(ProjectDao.getReleaseList(dba,pid));
			}
		} finally{
			dba.closeEm();
		}

		return rl;
	}
}
