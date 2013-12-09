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
import edu.cs673.plm.model.JSONReleaseRequest;
import edu.cs673.plm.model.JSONProjectRequest;
import edu.cs673.plm.model.Release;
import edu.cs673.plm.model.Project;
import edu.cs673.plm.model.JSONUser;
import edu.cs673.plm.model.UserProject;
import edu.cs673.plm.model.StatusMessage;

@Path( "/projectmanage" )
public class ProjectManager {
	/************************************************************
	Function name: getOtherUserList()
	Author: Christian Heckendorf
	Created date: 11/18/2013
	Purpose: Returns a list of users not in a project
	************************************************************/
	@Path( "/p/{pid}/otherusers" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserList getOtherUserList(@PathParam("pid") long pid, TokenMessage token) {
		UserList ul = null;
		Dba dba = new Dba(true);
		try{
			if(Permission.canAccess(dba,new SessionToken(token.getToken()),pid,Permission.INVITE_USER)){
				ul = UserProjectDao.getOtherMemberList(dba,pid);
			}
		} finally{
			dba.closeEm();
		}

		return ul;
	}

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
				ul = UserProjectDao.getMemberList(dba,pid);
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
			if(Permission.canAccess(dba,st,pid,Permission.VIEW_PROJECT)){
				rl = ProjectDao.getReleaseList(dba,pid);
			}
		} finally{
			dba.closeEm();
		}

		return rl;
	}

	/************************************************************
	Function name: createRelease()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Creates a release under a project
	************************************************************/
	@Path( "/release/p/{pid}" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusMessage createRelease(@PathParam("pid") long pid, JSONReleaseRequest req) {
		StatusMessage sm = new StatusMessage(-1,"Internal Error");
		SessionToken st = new SessionToken(req.getToken().getToken());
		Dba dba = new Dba(false);
		try{
			if(Permission.canAccess(dba,st,pid,Permission.CREATE_RELEASE)){
				Release rel = new Release();
				rel.overlay(req.getRelease());
				sm = ReleaseDao.createRelease(dba,pid,rel);
			}
		} finally{
			dba.closeEm();
		}

		return sm;
	}

	/************************************************************
	Function name: updateRelease()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Updates a release
	************************************************************/
	@Path( "/release/r/{rid}" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusMessage updateRelease(@PathParam("rid") long rid, JSONReleaseRequest req) {
		StatusMessage sm = new StatusMessage(-1,"Internal Error");
		SessionToken st = new SessionToken(req.getToken().getToken());
		Dba dba = new Dba(false);
		try{
			Release rel = ReleaseDao.getReleaseById(dba,rid);
			if(Permission.canAccess(dba,st,rel.getProject().getId(),Permission.CREATE_RELEASE)){
				rel.overlay(req.getRelease());
				sm = ReleaseDao.updateRelease(dba,rel);
			}
		} finally{
			dba.closeEm();
		}

		return sm;
	}

	@Path( "/create" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusMessage createProject(JSONProjectRequest req) {
		StatusMessage sm = new StatusMessage(-1,"Internal Error");
		SessionToken st = new SessionToken(req.getToken().getToken());
		Dba dba = new Dba(false);
		try{
			Project proj = new Project();
			proj.overlay(req.getProject());
			sm = ProjectDao.createProject(dba,proj,st.getUid());
		} finally{
			dba.closeEm();
		}

		return sm;
	}

	/************************************************************
	Function name: addUser()
	Author: Christian Heckendorf
	Created date: 11/18/2013
	Purpose: Adds a user to a project
	************************************************************/
	@Path( "/p/{pid}/adduser/u/{uid}" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusMessage addUser(@PathParam("pid") long pid, @PathParam("uid") long uid, TokenMessage token) {
		StatusMessage sm = new StatusMessage(1,"Internal Error");
		SessionToken st = new SessionToken(token.getToken());
		Dba dba = new Dba(false);
		try{
			if(Permission.canAccess(dba,st,pid,Permission.INVITE_USER)){
				User u;
				Project p;

				if(UserProjectDao.findUserProjectByPid(dba,uid,pid)!=null)
					sm = new StatusMessage(3,"User already in project");
				else{
					p = ProjectDao.getProjectById(dba,pid);
					u = UserDao.getUserById(dba,uid);
					if(u==null || p==null)
						sm = new StatusMessage(2,"Invalid user or project");
					else{
						UserProjectDao.createUserProject(dba,u,p,RoleDao.findRoleById(dba,RoleDao.ROLE_DEVELOPER));
						sm = new StatusMessage(0,"Success");
					}
				}
			}
		} finally{
			dba.closeEm();
		}

		return sm;
	}

	/************************************************************
	Function name: removeUser()
	Author: Christian Heckendorf
	Created date: 11/19/2013
	Purpose: Removes a user from a project
	************************************************************/
	@Path( "/p/{pid}/removeuser/u/{uid}" )
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusMessage removeUser(@PathParam("pid") long pid, @PathParam("uid") long uid, TokenMessage token) {
		StatusMessage sm = new StatusMessage(1,"Internal Error");
		SessionToken st = new SessionToken(token.getToken());
		Dba dba = new Dba(false);
		try{
			if(Permission.canAccess(dba,st,pid,Permission.INVITE_USER)){
				UserProject up = UserProjectDao.findUserProjectByPid(dba,uid,pid);

				if(up==null)
					sm = new StatusMessage(2,"User not in project");
				else{
					UserProjectDao.removeUserProject(dba,up);
					sm = new StatusMessage(0,"Success");
				}
			}
		} finally{
			dba.closeEm();
		}

		return sm;
	}
}
