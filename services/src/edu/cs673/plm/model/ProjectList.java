/************************************************************
Filename: ProjectList.java
Author: Christian Heckendorf
Created date: 10/07/2013
Purpose: Holds a list of JSONProjects
Feature: Dashboard
************************************************************/
package edu.cs673.plm.model;

import java.util.List;
import java.util.ArrayList;

public class ProjectList{
	private List<JSONProject> projects;

	/************************************************************
	Function name: ProjectList()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Default constructor
	************************************************************/
	public ProjectList(){
		projects = new ArrayList<JSONProject>();
	}

	/************************************************************
	Function name: addProject()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Adds a project to the list
	************************************************************/
	public void addProject(Project project){
		projects.add(new JSONProject(project));
	}

	/************************************************************
	Function name: addProjects()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Adds a list of projects
	************************************************************/
	public void addProjects(List<Project> projects){
		for(Project p : projects){
			this.addProject(p);
		}
	}

	/************************************************************
	Function name: getProjects()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Returns the list of projects
	************************************************************/
	public List<JSONProject> getProjects(){
		return projects;
	}

	/************************************************************
	Function name: setProjects()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Sets the list of projects
	************************************************************/
	public void setProjects(List<JSONProject> projects){
		this.projects=projects;
	}
}
