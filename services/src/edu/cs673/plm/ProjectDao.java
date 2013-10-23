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

public class ProjectDao {
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

	public static long getProjectIdFromRelease(Dba dba, long rid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("Select r.project.id from Release r where r.id = :rid")
					.setParameter("rid",rid);
		try{
			return ((Long)q.getSingleResult()).longValue();
		} catch(Exception e){
			return 0;
		}
	}
}
