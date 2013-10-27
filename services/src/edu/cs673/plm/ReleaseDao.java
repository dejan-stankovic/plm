/************************************************************
Filename: ReleaseDao.java
Author: Christian Heckendorf
Created date: 10/26/2013
Purpose: Accesses the Release entity
Feature: None
************************************************************/
package edu.cs673.plm;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.cs673.plm.model.Release;
import edu.cs673.plm.model.UserStory;
import edu.cs673.plm.model.UserStoryList;
import edu.cs673.plm.model.StatusMessage;
import edu.cs673.plm.model.Project;

public class ReleaseDao {
	/************************************************************
	Function name: createRelease()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Creates a release under a project
	************************************************************/
	public static StatusMessage createRelease(Dba dba, long pid, Release release){
		Project project;
		EntityManager em = dba.getActiveEm();
		release.setProject(ProjectDao.getProjectById(dba,pid));
		try{
			em.persist(release);
			em.flush();
		} catch(Exception e){
			return new StatusMessage(-1,"Internal Error");
		}

		return new StatusMessage(release.getId(),"Success");
	}

	/************************************************************
	Function name: updateRelease()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Updates a release
	************************************************************/
	public static StatusMessage updateRelease(Dba dba, Release release){
		EntityManager em = dba.getActiveEm();
		try{
			em.persist(release);
		} catch(Exception e){
			return new StatusMessage(-1,"Internal Error");
		}

		return new StatusMessage(0,"Success");
	}

	/************************************************************
	Function name: getUserStoryList()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Returns a list of user stories under the release
	************************************************************/
	public static UserStoryList getUserStoryList(Dba dba, long rid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select u from UserStory u where u.release.id = :rid")
					.setParameter("rid",rid);
		try{
			UserStoryList usl = new UserStoryList();
			usl.addUserStories((List<UserStory>)q.getResultList());
			return usl;
		} catch(Exception e){
			return null;
		}
	}

	/************************************************************
	Function name: getReleaseById()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Returns a release given a release id
	************************************************************/
	public static Release getReleaseById(Dba dba, long rid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select r from Release r where r.id = :rid")
					.setParameter("rid",rid);
		try{
			return (Release)q.getSingleResult();
		} catch(Exception e){
			return null;
		}
	}
}
