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
import edu.cs673.plm.model.JSONRelease;

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
	public static List<JSONRelease> getReleaseList(Dba dba, long pid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select r from Release r where r.project.id = :pid")
					.setParameter("pid",pid);
		try{
			List<Release> releases = (List<Release>)q.getResultList();
			List<JSONRelease> jreleases = new ArrayList<JSONRelease>();
			for(Release r : releases){
				jreleases.add(new JSONRelease(r));
			}
			return jreleases;
		} catch(Exception e){
			return null;
		}
	}
}
