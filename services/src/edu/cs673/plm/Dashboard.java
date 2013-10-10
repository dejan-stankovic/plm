/************************************************************
Filename: Dashboard.java
Author: Christian Heckendorf
Created date: 10/08/2013
Purpose: Provides services for the dashboard
Feature: Dashboard
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
import edu.cs673.plm.model.ProjectList;
import edu.cs673.plm.model.TaskList;
import edu.cs673.plm.model.JSONProject;
import edu.cs673.plm.model.JSONTask;
import edu.cs673.plm.model.UserProject;

@Path( "/dashboard" )
public class Dashboard {
	/************************************************************
	Function name: getProjectList()
	Author: Christian Heckendorf
	Created date: 10/08/2013
	Purpose: Returns a list of projects for a user
	************************************************************/
	@Path( "/projects" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProjectList getProjectList(TokenMessage token) {
		SessionToken st = new SessionToken(token.getToken());
		ProjectList pl = null;
		Dba dba = new Dba(true);
		try{
			pl = new ProjectList();
			pl.setProjects(UserProjectDao.getProjectList(dba,st.getUid()));
		} finally{
			dba.closeEm();
		}

		return pl;
	}

	/************************************************************
	Function name: getTaskList()
	Author: Christian Heckendorf
	Created date: 10/08/2013
	Purpose: Returns a list of tasks assigned to a user
	************************************************************/
	@Path( "/tasks" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TaskList getTaskList(TokenMessage token) {
		SessionToken st = new SessionToken(token.getToken());
		TaskList tl = null;
		Dba dba = new Dba(true);
		try{
			tl = new TaskList();
			tl.setTasks(UserDao.getTaskList(dba,st.getUid()));
		} finally{
			dba.closeEm();
		}

		return tl;
	}

	/************************************************************
	Function name: getTaskList()
	Author: Christian Heckendorf
	Created date: 10/08/2013
	Purpose: Returns tasks assigned to a user under a release
	************************************************************/
	@Path( "/tasks/release/{rid}" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TaskList getTaskList(@PathParam("rid") long rid, TokenMessage token) {
		SessionToken st = new SessionToken(token.getToken());
		TaskList tl = null;
		Dba dba = new Dba(true);
		try{
			tl = new TaskList();
			tl.setTasks(UserDao.getTaskList(dba,st.getUid(),rid));
		} finally{
			dba.closeEm();
		}

		return tl;
	}
}
