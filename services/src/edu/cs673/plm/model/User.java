/***************************************************************
Filename: User.java
Author: Christian Heckendorf
Created Date: 9/29/13
Purpose: Holds a user
Features: None
***************************************************************/
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
public class User{
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private long id;
	private String name;
	private String password;

	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name="UserProject",joinColumns=@JoinColumn(name="userId"),inverseJoinColumns=@JoinColumn(name="id"))
	private List<UserProject> userProjects;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="userAssigned")
	private List<Task> tasksAssigned;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="userCreated")
	private List<Bug> bugsCreated;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="userAssigned")
	private List<Bug> bugsAssigned;

	/***************************************************************
	Function name: getId
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Gets an id
	***************************************************************/
	public long getId(){
		return id;
	}

	/***************************************************************
	Function name: setId
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Sets an id
	***************************************************************/
	public void setId(long id){
		this.id=id;
	}

	/***************************************************************
	Function name: getName
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Gets a name
	***************************************************************/
	public String getName(){
		return name;
	}

	/***************************************************************
	Function name: setName
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Sets a name
	***************************************************************/
	public void setName(String name){
		this.name=name;
	}

	/***************************************************************
	Function name: setPassword
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Sets a password
	***************************************************************/
	public String getPassword(){
		return password;
	}

	/***************************************************************
	Function name: setPassword
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Sets a password
	***************************************************************/
	public void setPassword(String password){
		this.password=password;
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
	Function name: getTasksAssigned()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Returns the tasks assigned
	************************************************************/
	public List<Task> getTasksAssigned(){
		return tasksAssigned;
	}

	/************************************************************
	Function name: setTasksAssigned()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Sets the tasks assigned
	************************************************************/
	public void setTasksAssigned(List<Task> tasksAssigned){
		this.tasksAssigned=tasksAssigned;
	}

	/************************************************************
	Function name: getBugsCreated()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Returns the bugs created
	************************************************************/
	public List<Bug> getBugsCreated(){
		return bugsCreated;
	}

	/************************************************************
	Function name: setBugsCreated()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Sets the bugs created
	************************************************************/
	public void setBugsCreated(List<Bug> bugsCreated){
		this.bugsCreated=bugsCreated;
	}

	/************************************************************
	Function name: getBugsAssigned()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Returns the assigned bugs
	************************************************************/
	public List<Bug> getBugsAssigned(){
		return bugsAssigned;
	}

	/************************************************************
	Function name: setBugsAssigned()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Sets the assigned bugs
	************************************************************/
	public void setBugsAssigned(List<Bug> bugsAssigned){
		this.bugsAssigned=bugsAssigned;
	}

}
