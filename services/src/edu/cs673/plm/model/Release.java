/************************************************************
Filename: Release.java
Author: Christian Heckendorf
Created date: 10/01/2013
Purpose: Holds a release
Feature: None
************************************************************/
package edu.cs673.plm.model;

import java.util.List;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="`Release`")
public class Release{
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private long id;
	private String version;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="projectId")
	private Project project;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="release")
	private List<UserStory> userStories;

	/************************************************************
	Function name: overlay()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Overlays a JSONRelease onto this object
	************************************************************/
	public void overlay(JSONRelease jsonRelease){
		version = jsonRelease.getVersion();
		startDate = jsonRelease.getStartDate();
		endDate = jsonRelease.getEndDate();
	}

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
	Function name: getVersion
	Author: Christian Heckendorf
	Created Date: 10/01/13
	Purpose: Gets a version
	***************************************************************/
	public String getVersion(){
		return version;
	}

	/***************************************************************
	Function name: setVersion
	Author: Christian Heckendorf
	Created Date: 10/01/13
	Purpose: Sets a version
	***************************************************************/
	public void setVersion(String version){
		this.version=version;
	}

	/************************************************************
	Function name: getProject()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Returns the project
	************************************************************/
	public Project getProject(){
		return project;
	}

	/************************************************************
	Function name: setProject()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Sets a project
	************************************************************/
	public void setProject(Project project){
		this.project=project;
	}

	/************************************************************
	Function name: getUserStories()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Returns the user stories
	************************************************************/
	public List<UserStory> getUserStories(){
		return userStories;
	}

	/************************************************************
	Function name: setUserStories()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Sets the user stories
	************************************************************/
	public void setUserStories(List<UserStory> userStories){
		this.userStories=userStories;
	}

	/************************************************************
	Function name: getEndDate()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Returns a endDate
	************************************************************/
	public Date getEndDate(){
		return endDate;
	}

	/************************************************************
	Function name: setEndDate()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Sets a endDate
	************************************************************/
	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}

	/************************************************************
	Function name: getStartDate()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Returns a startDate
	************************************************************/
	public Date getStartDate(){
		return startDate;
	}

	/************************************************************
	Function name: setStartDate()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Sets a startDate
	************************************************************/
	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}
}
