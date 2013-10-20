/************************************************************
Filename: UserProject.java
Author: Christian Heckendorf
Created date: 09/29/2013
Purpose: Holds a UserProject entity
Feature: None
************************************************************/
package edu.cs673.plm.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class UserProject{
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	private User user;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="roleId")
	private Role role;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="projectId")
	private Project project;

	/************************************************************
	Function name: getId
	Author: Christian Heckendorf
	Created date: 09/29/2013
	Purpose: Returns the ID
	************************************************************/
	public long getId(){
		return id;
	}

	/************************************************************
	Function name: setId
	Author: Christian Heckendorf
	Created date: 09/29/2013
	Purpose: Sets the ID
	************************************************************/
	public void setId(long id){
		this.id=id;
	}

	/************************************************************
	Function name: getUser
	Author: Christian Heckendorf
	Created date: 09/29/2013
	Purpose: Returns the user
	************************************************************/
	public User getUser(){
		return user;
	}

	/************************************************************
	Function name: setUser
	Author: Christian Heckendorf
	Created date: 09/29/2013
	Purpose: Sets the user
	************************************************************/
	public void setUser(User user){
		this.user=user;
	}

	/************************************************************
	Function name: getRole
	Author: Christian Heckendorf
	Created date: 09/29/2013
	Purpose: Returns the role
	************************************************************/
	public Role getRole(){
		return role;
	}

	/************************************************************
	Function name: setRole
	Author: Christian Heckendorf
	Created date: 09/29/2013
	Purpose: Sets the role
	************************************************************/
	public void setRole(Role role){
		this.role=role;
	}

	/************************************************************
	Function name: getProject
	Author: Christian Heckendorf
	Created date: 09/29/2013
	Purpose: Returns the project
	************************************************************/
	public Project getProject(){
		return project;
	}

	/************************************************************
	Function name: setProject
	Author: Christian Heckendorf
	Created date: 09/29/2013
	Purpose: Sets the project
	************************************************************/
	public void setProject(Project project){
		this.project=project;
	}
}
