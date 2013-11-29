/************************************************************
Filename: UserStoryService.java
Author: Christian Heckendorf
Created date: 10/22/2013
Purpose: User story related services
Feature: User Story
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
import edu.cs673.plm.model.UserStory;
import edu.cs673.plm.model.JSONUserStory;
import edu.cs673.plm.model.JSONStoryRequest;
import edu.cs673.plm.model.UserStoryList;

@Path( "/userstory" )
public class UserStoryService {
	/************************************************************
	Function name: getUserStoryList()
	Author: Christian Heckendorf
	Created date: 10/22/2013
	Purpose: Returns a list of user stories under a release
	************************************************************/
	@Path( "/r/{rid}" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserStoryList getUserStoryList(@PathParam("rid") long rid, TokenMessage token) {
		UserStoryList usl = null;
		Dba dba = new Dba(true);
		try{
			long pid = ProjectDao.getProjectIdFromRelease(dba,rid);
			if(Permission.canAccess(dba,new SessionToken(token.getToken()),pid,Permission.VIEW_PROJECT)){
				usl = ReleaseDao.getUserStoryList(dba,rid);
			}
		} finally{
			dba.closeEm();
		}

		return usl;
	}

	/************************************************************
	Function name: createUserStory()
	Author: Christian Heckendorf
	Created date: 10/22/2013
	Purpose: Creates a user story
	************************************************************/
	@Path( "/create/r/{rid}" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusMessage createUserStory(@PathParam("rid") long rid, JSONStoryRequest userStoryReq) {
		StatusMessage sm = new StatusMessage(-1,"Internal Error");
		Dba dba = new Dba(false);
		try{
			long pid = ProjectDao.getProjectIdFromRelease(dba,rid);
			if(Permission.canAccess(dba,
						new SessionToken(userStoryReq.getToken().getToken()),
						pid,
						Permission.CREATE_USER_STORY)){
				JSONUserStory jus = userStoryReq.getUserStory();
				UserStory us = UserStoryDao.convert(dba,jus);
				sm = UserStoryDao.createUserStory(dba,rid,us);
			}
		} finally{
			dba.closeEm();
		}

		return sm;
	}

	/************************************************************
	Function name: updateUserStory()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Updates a user story
	************************************************************/
	@Path( "/update/u/{uid}" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusMessage updateUserStory(@PathParam("uid") long uid, JSONStoryRequest userStoryReq) {
		StatusMessage sm = new StatusMessage(-1,"Internal Error");
		Dba dba = new Dba(false);
		try{
			long pid = ProjectDao.getProjectIdFromUserStory(dba,uid);
			if(Permission.canAccess(dba,
						new SessionToken(userStoryReq.getToken().getToken()),
						pid,
						Permission.CREATE_USER_STORY)){
				JSONUserStory jus = userStoryReq.getUserStory();
				jus.setId(uid); // Just in case
				UserStory us = UserStoryDao.convert(dba,jus);
				sm = UserStoryDao.updateUserStory(dba,us);
			}
		} finally{
			dba.closeEm();
		}

		return sm;
	}

	/************************************************************
	Function name: migrateUserStory()
	Author: Christian Heckendorf
	Created date: 11/29/2013
	Purpose: Migrates a user story to a new release
	************************************************************/
	@Path( "/migrate/u/{uid}/r/{rid}" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusMessage migrateUserStory(@PathParam("uid") long uid, @PathParam("rid") long rid, TokenMessage token){
		StatusMessage sm = new StatusMessage(-1,"Internal Error");
		Dba dba = new Dba(false);
		try{
			long pid = ProjectDao.getProjectIdFromUserStory(dba,uid);
			if(Permission.canAccess(dba,
						new SessionToken(token.getToken()),
						pid,
						Permission.CREATE_USER_STORY)){
				sm = UserStoryDao.migrateUserStory(dba,uid,rid);
			}
		} finally{
			dba.closeEm();
		}

		return sm;
	}
}
