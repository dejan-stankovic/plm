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

import edu.cs673.plm.model.JSONUserStory;
import edu.cs673.plm.model.UserStory;
import edu.cs673.plm.model.UserStoryList;
import edu.cs673.plm.model.StatusMessage;
import edu.cs673.plm.model.Release;

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

	/************************************************************
	Function name: updateUserStory()
	Author: Christian Heckendorf
	Created date: 10/27/2013
	Purpose: Updates a user story
	************************************************************/
	public static StatusMessage updateUserStory(Dba dba, UserStory userStory){
		EntityManager em = dba.getActiveEm();

		try{
			em.persist(userStory);
		} catch(Exception e){
			return new StatusMessage(-1,"Internal Error");
		}

		return new StatusMessage(userStory.getId(),"Success");
	}

	/************************************************************
	Function name: migrateUserStory()
	Author: Christian Heckendorf
	Created date: 11/29/2013
	Purpose: Migrates a user story to a new release
	************************************************************/
	public static StatusMessage migrateUserStory(Dba dba, long uid, long rid){
		EntityManager em = dba.getActiveEm();

		try{
			Release release = ReleaseDao.getReleaseById(dba,rid);
			if(release==null)
				return new StatusMessage(-1,"Internal Error");
			Query q = em.createQuery("update UserStory us set us.release = :release where us.id = :id")
					.setParameter("release",release)
					.setParameter("id",uid);
			q.executeUpdate();
		} catch(Exception e){
			return new StatusMessage(-1,"Internal Error");
		}

		return new StatusMessage(0,"Success");
	}

	/************************************************************
	Function name: convert()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Converts a JSONUserStory to a UserStory
	************************************************************/
	public static UserStory convert(Dba dba, JSONUserStory juserStory){
		EntityManager em = dba.getActiveEm();

		try{
			UserStory us;
			
			if(juserStory.getId()>0)
				us = UserStoryDao.getUserStoryById(dba,juserStory.getId());
			else
				us = new UserStory();

			us.overlay(juserStory);

			if(juserStory.getStatus()!=null)
				us.setStatus(StatusDao.getStatusById(dba,juserStory.getStatus().getId()));

			if(juserStory.getOwner()!=null)
				us.setOwner(UserDao.getUserById(dba,juserStory.getOwner().getId()));

			return us;
		} catch(Exception e){
			return null;
		}
	}

	/************************************************************
	Function name: getUserStoryById()
	Author: Christian Heckendorf
	Created date: 10/27/2013
	Purpose: Returns a user story by id
	************************************************************/
	public static UserStory getUserStoryById(Dba dba, long usid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select u from UserStory u where u.id = :usid")
					.setParameter("usid",usid);
		try{
			return (UserStory)q.getSingleResult();
		} catch(Exception e){
			return null;
		}
	}
}
