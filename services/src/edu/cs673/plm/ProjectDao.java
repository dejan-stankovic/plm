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
import edu.cs673.plm.model.Role;
import edu.cs673.plm.model.User;
import edu.cs673.plm.model.UserProject;
import edu.cs673.plm.model.StatusMessage;

public class ProjectDao {
	/************************************************************
	Function name: createProject()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Creates a project and sets the project leader
	************************************************************/
	public static StatusMessage createProject(Dba dba, Project project, long uid){
		EntityManager em = dba.getActiveEm();

		try{
			User user = UserDao.getUserById(dba,uid);
			Role role = RoleDao.findRoleById(dba,RoleDao.ROLE_PROJECT_LEADER);

			em.persist(project);
			em.flush();

			UserProjectDao.createUserProject(dba,user,project,role);
		} catch(Exception e){
			e.printStackTrace();
			return new StatusMessage(-1,"Internal Error");
		}

		return new StatusMessage(project.getId(),"Success");
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

	/************************************************************
	Function name: getProjectIdFromRelease()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Gets a project id given a release id
	************************************************************/
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

	/************************************************************
	Function name: getProjectIdFromUserStory()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Gets a project id given a user story id
	************************************************************/
	public static long getProjectIdFromUserStory(Dba dba, long uid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("Select u.release.project.id from UserStory u where u.id = :uid")
					.setParameter("uid",uid);
		try{
			return ((Long)q.getSingleResult()).longValue();
		} catch(Exception e){
			return 0;
		}
	}

	/************************************************************
	Function name: getProjectIdFromTask()
	Author: Christian Heckendorf
	Created date: 10/27/2013
	Purpose: Gets a project id given a task id
	************************************************************/
	public static long getProjectIdFromTask(Dba dba, long tid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("Select t.userStory.release.project.id from Task t where t.id = :tid")
					.setParameter("tid",tid);
		try{
			return ((Long)q.getSingleResult()).longValue();
		} catch(Exception e){
			return 0;
		}
	}
}
