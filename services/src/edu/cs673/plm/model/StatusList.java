/************************************************************
Filename: StatusList.java
Author: Christian Heckendorf
Created date: 11/02/2013
Purpose: Holds a list of JSONStatuses
Feature: Dashboard
************************************************************/
package edu.cs673.plm.model;

import java.util.List;
import java.util.ArrayList;

public class StatusList{
	private List<JSONStatus> statuses;

	/************************************************************
	Function name: StatusList()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Default constructor
	************************************************************/
	public StatusList(){
		statuses = new ArrayList<JSONStatus>();
	}

	/************************************************************
	Function name: addStatus()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Adds a status to the list
	************************************************************/
	public void addStatus(Status status){
		statuses.add(new JSONStatus(status));
	}

	/************************************************************
	Function name: addStatuses()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Adds a list of statuses
	************************************************************/
	public void addStatuses(List<Status> statuses){
		for(Status r : statuses){
			this.addStatus(r);
		}
	}

	/************************************************************
	Function name: getStatuses()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Returns the list of statuses
	************************************************************/
	public List<JSONStatus> getStatuses(){
		return statuses;
	}

	/************************************************************
	Function name: setStatuses()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Sets the list of statuses
	************************************************************/
	public void setStatuses(List<JSONStatus> statuses){
		this.statuses=statuses;
	}
}
