/************************************************************
Filename: StatusService.java
Author: Christian Heckendorf
Created date: 11/02/2013
Purpose: Status services
Feature: UserStory, Task
************************************************************/
package edu.cs673.plm;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import edu.cs673.plm.model.Status;
import edu.cs673.plm.model.StatusList;
import edu.cs673.plm.model.JSONStatus;
import edu.cs673.plm.model.TokenMessage;

@Path( "/status" )
public class StatusService {
	/************************************************************
	Function name: getStatuses()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Returns a list of statuses in the system
	************************************************************/
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusList getStatuses(TokenMessage token){
		Dba dba = new Dba(true);
		try{
			return StatusDao.getStatusList(dba);
		} catch(Exception e){
			return null;
		}
	}
}
