/************************************************************
Filename: UserStory.java
Author: Christian Heckendorf
Created date: 10/01/2013
Purpose: Holds a user story
Feature: None
************************************************************/
package edu.cs673.plm.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class UserStory{
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private long id;
	private String name;
	private String description;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="releaseId")
	private Release release;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="userStory")
	private List<Task> tasks;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="userStory")
	private List<Bug> bugs;

	/***************************************************************
	Function name: getId
	Author: Christian Heckendorf
	Created Date: 10/01/13
	Purpose: Gets an id
	***************************************************************/
	public long getId(){
		return id;
	}

	/***************************************************************
	Function name: setId
	Author: Christian Heckendorf
	Created Date: 10/01/13
	Purpose: Sets an id
	***************************************************************/
	public void setId(long id){
		this.id=id;
	}

	/***************************************************************
	Function name: getName
	Author: Christian Heckendorf
	Created Date: 10/01/13
	Purpose: Gets a name
	***************************************************************/
	public String getName(){
		return name;
	}

	/***************************************************************
	Function name: setName
	Author: Christian Heckendorf
	Created Date: 10/01/13
	Purpose: Sets a name
	***************************************************************/
	public void setName(String name){
		this.name=name;
	}

	/************************************************************
	Function name: getDescription()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Returns the description
	************************************************************/
	public String getDescription(){
		return description;
	}

	/************************************************************
	Function name: setDescription()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Sets the description
	************************************************************/
	public void setDescription(String description){
		this.description=description;
	}

	/************************************************************
	Function name: getRelease()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Returns the release
	************************************************************/
	public Release getRelease(){
		return release;
	}

	/************************************************************
	Function name: setRelease()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Sets a release
	************************************************************/
	public void setRelease(Release release){
		this.release=release;
	}

	/************************************************************
	Function name: getTasks()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Returns the tasks
	************************************************************/
	public List<Task> getTasks(){
		return tasks;
	}

	/************************************************************
	Function name: setTasks()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Sets the tasks
	************************************************************/
	public void setTasks(List<Task> tasks){
		this.tasks=tasks;
	}

	/************************************************************
	Function name: getBugs()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Returns the bugs
	************************************************************/
	public List<Bug> getBugs(){
		return bugs;
	}

	/************************************************************
	Function name: setBugs()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Sets bugs
	************************************************************/
	public void setBugs(List<Bug> bugs){
		this.bugs=bugs;
	}
}
