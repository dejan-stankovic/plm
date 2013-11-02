/************************************************************
Filename: UserStoryComments.java
Author: Christian Heckendorf
Created date: 11/02/2013
Purpose: Holds a user story comment
Feature: None
************************************************************/
package edu.cs673.plm.model;

import java.util.List;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.FetchType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class UserStoryComment{
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private long id;
	private String comments;

	@Temporal(TemporalType.DATE)
	private Date createdOn;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="authorId")
	private User author;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userStoryId")
	private UserStory userStory;

	/************************************************************
	Function name: getId()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Returns a id
	************************************************************/
	public long getId(){
		return id;
	}

	/************************************************************
	Function name: setId()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Sets a id
	************************************************************/
	public void setId(long id){
		this.id = id;
	}

	/************************************************************
	Function name: getComments()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Returns a comments
	************************************************************/
	public String getComments(){
		return comments;
	}

	/************************************************************
	Function name: setComments()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Sets a comments
	************************************************************/
	public void setComments(String comments){
		this.comments = comments;
	}

	/************************************************************
	Function name: getCreatedOn()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Returns a createdOn
	************************************************************/
	public Date getCreatedOn(){
		return createdOn;
	}

	/************************************************************
	Function name: setCreatedOn()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Sets a createdOn
	************************************************************/
	public void setCreatedOn(Date createdOn){
		this.createdOn = createdOn;
	}

	/************************************************************
	Function name: getAuthor()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Returns a author
	************************************************************/
	public User getAuthor(){
		return author;
	}

	/************************************************************
	Function name: setAuthor()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Sets a author
	************************************************************/
	public void setAuthor(User author){
		this.author = author;
	}

	/************************************************************
	Function name: getUserStory()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Returns a userStory
	************************************************************/
	public UserStory getUserStory(){
		return userStory;
	}

	/************************************************************
	Function name: setUserStory()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Sets a userStory
	************************************************************/
	public void setUserStory(UserStory userStory){
		this.userStory = userStory;
	}
}
