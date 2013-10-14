/************************************************************
Filename: BugList.java
Author: Christian Heckendorf
Created date: 10/13/2013
Purpose: Holds a list of JSONBugs
Feature: Dashboard
************************************************************/
package edu.cs673.plm.model;

import java.util.List;
import java.util.ArrayList;

public class BugList{
	private List<JSONBug> bugs;

	/************************************************************
	Function name: BugList()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Default constructor
	************************************************************/
	public BugList(){
		bugs = new ArrayList<JSONBug>();
	}

	/************************************************************
	Function name: addBug()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Adds a bug to the list
	************************************************************/
	public void addBug(Bug bug){
		bugs.add(new JSONBug(bug));
	}

	/************************************************************
	Function name: addBugs()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Adds a list of bugs
	************************************************************/
	public void addBugs(List<Bug> bugs){
		for(Bug b : bugs){
			this.addBug(b);
		}
	}

	/************************************************************
	Function name: getBugs()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Returns the list of bugs
	************************************************************/
	public List<JSONBug> getBugs(){
		return bugs;
	}

	/************************************************************
	Function name: setBugs()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Sets the list of bugs
	************************************************************/
	public void setBugs(List<JSONBug> bugs){
		this.bugs=bugs;
	}
}
