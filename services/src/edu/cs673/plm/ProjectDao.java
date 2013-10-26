/***************************************************************
Filename: ProjectDao.java
Author: Christian Heckendorf
Created Date: 9/29/13
Purpose: Accesses the Project entity
Features: None yet
***************************************************************/
package edu.cs673.plm;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.cs673.plm.model.Project;
import edu.cs673.plm.model.Release;
import edu.cs673.plm.model.ReleaseList;
import edu.cs673.plm.model.StatusMessage;

public class ProjectDao {
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
			em.flush()
		} catch(Exception e){
			return new StatusMessage(-1,"Internal Error");
		}

		return new StatusMessage(release.getId(),"Success");
	}

	/************************************************************
	Function name: getProjectById()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Gets a project by ID
	************************************************************/
	public static Project getProjectById(Dba dba, long pid){
		Project project;
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select p from Project p where p.id = :pid")
						.setParameter("pid",pid);
		try{
			project = (Project)q.getSingleResult();
		} catch(Exception e){
			return null;
		}

		return project;
	}

	/***************************************************************
	Function name: getMemberCount
	Author: Christian Heckendorf
	Created Date: 9/29/13
	Purpose: Returns the number of users in a project
	***************************************************************/
	public static long getMemberCount(Dba dba, Project project){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select project from Project project where project.id = :id")
						.setParameter("id",project.getId());
		try{
			project = (Project)q.getSingleResult();
		} catch(Exception e){
			return 0;
		}

		return project.getUserProjects().size();
	}

	/************************************************************
	Function name: getReleaseList()
	Author: Christian Heckendorf
	Created date: 10/08/2013
	Purpose: Returns a list of releases under a project
	************************************************************/
	public static ReleaseList getReleaseList(Dba dba, long pid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select r from Release r where r.project.id = :pid")
					.setParameter("pid",pid);
		try{
			ReleaseList rl = new ReleaseList();
			rl.addReleases((List<Release>)q.getResultList());
			return rl;
		} catch(Exception e){
			return null;
		}
	}
}
