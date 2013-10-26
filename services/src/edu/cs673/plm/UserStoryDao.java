/************************************************************
Filename: UserStoryDao.java
Author: Christian Heckendorf
Created date: 10/26/2013
Purpose: Accesses user story information in the database
Feature: None
************************************************************/
package edu.cs673.plm;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.cs673.plm.model.UserStory;
import edu.cs673.plm.model.UserStoryList;
import edu.cs673.plm.model.StatusMessage;

public class UserStoryDao {
	/************************************************************
	Function name: createUserStory()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Creates a user story
	************************************************************/
	public static StatusMessage createUserStory(Dba dba, long rid, UserStory userStory){
		EntityManager em = dba.getActiveEm();
		userStory.setRelease(ReleaseDao.getReleaseById(dba,rid));
		em.persist(userStory);
		em.flush();
		return new StatusMessage(userStory.getId(),"Success");
	}

	public static StatusMessage updateUserStory(Dba dba, UserStory userStory){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select u from UserStory u where u.id = :uid")
					.setParameter("uid",userStory.getId());
		try{
			UserStory us = (UserStory)q.getSingleResult();
			us.overlay(userStory);
			em.persist(us);
		} catch(Exception e){
			return new StatusMessage(-1,"Internal Error");
		}
		return new StatusMessage(userStory.getId(),"Success");
	}
}
