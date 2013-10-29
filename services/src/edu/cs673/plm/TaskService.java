/************************************************************
Filename: TaskService.java
Author: Christian Heckendorf
Created date: 10/27/2013
Purpose: Provides task related services
Feature: Tasks
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
import edu.cs673.plm.model.Task;
import edu.cs673.plm.model.JSONTask;
import edu.cs673.plm.model.JSONTaskRequest;
import edu.cs673.plm.model.TaskList;

@Path( "/task" )
public class TaskService {
	/************************************************************
	Function name: getTaskList()
	Author: Christian Heckendorf
	Created date: 10/27/2013
	Purpose: Gets a list of tasks under a user story
	************************************************************/
	@Path( "/us/{usid}" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TaskList getTaskList(@PathParam("usid") long usid, TokenMessage token) {
		TaskList tl = null;
		Dba dba = new Dba(true);
		try{
			long pid = ProjectDao.getProjectIdFromUserStory(dba,usid);
			if(Permission.canAccess(dba,new SessionToken(token.getToken()),pid,Permission.VIEW_PROJECT)){
				tl = TaskDao.getTaskList(dba,usid);
			}
		} finally{
			dba.closeEm();
		}

		return tl;
	}

	/************************************************************
	Function name: createTask()
	Author: Christian Heckendorf
	Created date: 10/27/2013
	Purpose: Creates a task under a user story
	************************************************************/
	@Path( "/create/us/{usid}" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusMessage createTask(@PathParam("usid") long usid, JSONTaskRequest taskReq) {
		StatusMessage sm = new StatusMessage(-1,"Internal Error");
		Dba dba = new Dba(false);
		try{
			long pid = ProjectDao.getProjectIdFromUserStory(dba,usid);
			if(Permission.canAccess(dba,
						new SessionToken(taskReq.getToken().getToken()),
						pid,
						Permission.CREATE_USER_STORY)){
				Task task = TaskDao.convert(dba,taskReq.getTask());
				sm = TaskDao.createTask(dba,usid,task);
			}
		} finally{
			dba.closeEm();
		}

		return sm;
	}

	/************************************************************
	Function name: updateTask()
	Author: Christian Heckendorf
	Created date: 10/27/2013
	Purpose: Updates a task
	************************************************************/
	@Path( "/update/t/{tid}" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusMessage updateTask(@PathParam("tid") long tid, JSONTaskRequest taskReq) {
		StatusMessage sm = new StatusMessage(-1,"Internal Error");
		Dba dba = new Dba(false);
		try{
			long pid = ProjectDao.getProjectIdFromTask(dba,tid);
			if(Permission.canAccess(dba,
						new SessionToken(taskReq.getToken().getToken()),
						pid,
						Permission.CREATE_USER_STORY)){
				JSONTask jt = taskReq.getTask();
				jt.setId(tid); // Just in case
				Task task = TaskDao.convert(dba,jt);
				sm = TaskDao.updateTask(dba,task);
			}
		} finally{
			dba.closeEm();
		}

		return sm;
	}
}
