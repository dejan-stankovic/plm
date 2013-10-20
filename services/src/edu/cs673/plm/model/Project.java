/***************************************************************
Filename: Project.java
Author: Christian Heckendorf
Created Date: 9/29/13
Purpose: Holds a project
Features: None
***************************************************************/
package edu.cs673.plm.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Project{
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private long id;
	private String name;

	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name="UserProject",joinColumns=@JoinColumn(name="projectId"),inverseJoinColumns=@JoinColumn(name="id"))
	private List<UserProject> userProjects;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="project")
	private List<Release> releases;

	/***************************************************************
	Function name: getId
	Author: Christian Heckendorf
	Created Date: 9/29/13
	Purpose: Gets an id
	***************************************************************/
	public long getId(){
		return id;
	}

	/***************************************************************
	Function name: setId
	Author: Christian Heckendorf
	Created Date: 9/29/13
	Purpose: Sets an id
	***************************************************************/
	public void setId(long id){
		this.id=id;
	}

	/***************************************************************
	Function name: getName
	Author: Christian Heckendorf
	Created Date: 9/29/13
	Purpose: Gets a name
	***************************************************************/
	public String getName(){
		return name;
	}

	/***************************************************************
	Function name: setName
	Author: Christian Heckendorf
	Created Date: 9/29/13
	Purpose: Sets a name
	***************************************************************/
	public void setName(String name){
		this.name=name;
	}

	/***************************************************************
	Function name: getUserProjects
	Author: Christian Heckendorf
	Created Date: 9/29/13
	Purpose: Gets a list of UserProjects
	***************************************************************/
	public List<UserProject> getUserProjects(){
		return userProjects;
	}

	/***************************************************************
	Function name: setUserProjects
	Author: Christian Heckendorf
	Created Date: 9/29/13
	Purpose: Sets a list of UserProjects
	***************************************************************/
	public void setUserProjects(List<UserProject> userProjects){
		this.userProjects=userProjects;
	}

	/************************************************************
	Function name: getReleases()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Returns releases
	************************************************************/
	public List<Release> getReleases(){
		return releases;
	}

	/************************************************************
	Function name: setReleases()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Sets releases
	************************************************************/
	public void setReleases(List<Release> releases){
		this.releases=releases;
	}
}
