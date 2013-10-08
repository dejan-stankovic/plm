/************************************************************
Filename: UserProjectDao.java
Author: Christian Heckendorf
Created date: 10/03/2013
Purpose: Helper methods to acces the UserProject table
Feature: None
************************************************************/
package edu.cs673.plm;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.cs673.plm.model.UserProject;
import edu.cs673.plm.model.Role;
import edu.cs673.plm.model.JSONUser;
import edu.cs673.plm.model.JSONProject;
import edu.cs673.plm.model.User;
import edu.cs673.plm.model.Project;

public class UserProjectDao {
	/************************************************************
	Function name: getProjectList()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Returns a list of projects a user is a member of
	************************************************************/
	public static List<JSONProject> getProjectList(Dba dba, long uid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select userProject.project from UserProject userProject where userProject.user.id = :uid")
					.setParameter("uid",uid);
		try{
			List<Project> projects = (List<Project>)q.getResultList();
			List<JSONProject> jprojects = new ArrayList<JSONProject>();
			for(Project p : projects){
				jprojects.add(new JSONProject(p));
			}
			return jprojects;
		} catch(Exception e){
			return null;
		}
	}

	/************************************************************
	Function name: getMemberList()
	Author: Christian Heckendorf
	Created date: 10/05/2013
	Purpose: Returns a list of users associated with a project
	************************************************************/
	public static List<JSONUser> getMemberList(Dba dba, long pid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select userProject.user from UserProject userProject where userProject.project.id = :pid")
					.setParameter("pid",pid);
		try{
			List<User> users = (List<User>)q.getResultList();
			List<JSONUser> jusers = new ArrayList<JSONUser>();
			for(User u : users){
				jusers.add(new JSONUser(u));
			}
			return jusers;
		} catch(Exception e){
			return null;
		}
	}

	/************************************************************
	Function name: findUserProjectByPid()
	Author: Christian Heckendorf
	Created date: 10/03/2013
	Purpose: Finds a UserProject in a list
	************************************************************/
	public static UserProject findUserProjectByPid(Dba dba, long uid, long pid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select userProject from UserProject userProject where userProject.user.id = :uid and userProject.project.id = :pid")
					.setParameter("uid",uid)
					.setParameter("pid",pid);
		try{
			return (UserProject)q.getSingleResult();
		} catch(Exception e){
			return null;
		}
	}

	/************************************************************
	Function name: getRole()
	Author: Christian Heckendorf
	Created date: 10/06/2013
	Purpose: Returns the role for a user in a project
	************************************************************/
	public static Role getRole(Dba dba, long uid, long pid){
		try{
			User user = UserDao.getUserById(dba,uid);
			UserProject up = findUserProjectByPid(dba,uid,pid);
			return up.getRole();
		} catch(Exception e){
			return null;
		}
	}


	/************************************************************
	Function name: setRole()
	Author: Christian Heckendorf
	Created date: 10/04/2013
	Purpose: Sets the role for a user on a project
	************************************************************/
	public static void setRole(Dba dba, UserProject up, Role role){
		EntityManager em = dba.getActiveEm();
		try{
			Query q = em.createQuery("update UserProject up set up.role = :role where up.id = :id")
					.setParameter("role",role)
					.setParameter("id",up.getId());
			q.executeUpdate();
		} catch(Exception e){
		}
	}
}
