/************************************************************
Filename: Status.java
Author: Christian Heckendorf
Created date: 11/01/2013
Purpose: Status entity object
Feature: None
************************************************************/
package edu.cs673.plm.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.FetchType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Status{
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private long id;
	private String name;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="status")
	private List<UserStory> userStories;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="status")
	private List<Task> tasks;

	/***************************************************************
	Function name: getId
	Author: Christian Heckendorf
	Created Date: 11/01/13
	Purpose: Gets an id
	***************************************************************/
	public long getId(){
		return id;
	}

	/***************************************************************
	Function name: setId
	Author: Christian Heckendorf
	Created Date: 11/01/13
	Purpose: Sets an id
	***************************************************************/
	public void setId(long id){
		this.id=id;
	}

	/***************************************************************
	Function name: getName
	Author: Christian Heckendorf
	Created Date: 11/01/13
	Purpose: Gets a name
	***************************************************************/
	public String getName(){
		return name;
	}

	/***************************************************************
	Function name: setName
	Author: Christian Heckendorf
	Created Date: 11/01/13
	Purpose: Sets a name
	***************************************************************/
	public void setName(String name){
		this.name=name;
	}

	/************************************************************
	Function name: getUserStories()
	Author: Christian Heckendorf
	Created date: 11/01/2013
	Purpose: Returns the user stories
	************************************************************/
	public List<UserStory> getUserStories(){
		return userStories;
	}

	/************************************************************
	Function name: setUserStories()
	Author: Christian Heckendorf
	Created date: 11/01/2013
	Purpose: Sets the user stories
	************************************************************/
	public void setUserStories(List<UserStory> userStories){
		this.userStories = userStories;
	}

	/************************************************************
	Function name: getTasks()
	Author: Christian Heckendorf
	Created date: 11/01/2013
	Purpose: Returns the tasks
	************************************************************/
	public List<Task> getTasks(){
		return tasks;
	}

	/************************************************************
	Function name: setTasks()
	Author: Christian Heckendorf
	Created date: 11/01/2013
	Purpose: Sets the tasks
	************************************************************/
	public void setTasks(List<Task> tasks){
		this.tasks = tasks;
	}
}
