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
import edu.cs673.plm.model.UserList;
import edu.cs673.plm.model.User;
import edu.cs673.plm.model.Project;
import edu.cs673.plm.model.ProjectList;

public class UserProjectDao {
	/************************************************************
	Function name: getProjectList()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Returns a list of projects a user is a member of
	************************************************************/
	public static ProjectList getProjectList(Dba dba, long uid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select userProject.project from UserProject userProject where userProject.user.id = :uid")
					.setParameter("uid",uid);
		try{
			ProjectList pl = new ProjectList();
			pl.addProjects((List<Project>)q.getResultList());
			return pl;
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
	public static UserList getMemberList(Dba dba, long pid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select userProject.user from UserProject userProject where userProject.project.id = :pid")
					.setParameter("pid",pid);
		try{
			UserList ul = new UserList();
			ul.addUsers((List<User>)q.getResultList());
			return ul;
		} catch(Exception e){
			return null;
		}
	}

	/************************************************************
	Function name: getOtherMemberList()
	Author: Christian Heckendorf
	Created date: 11/18/2013
	Purpose: Returns a list of users not in a project
	************************************************************/
	public static UserList getOtherMemberList(Dba dba, long pid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery( "select u from User u where u.id not in "+
				"(select up.user.id from UserProject up where up.project.id = :pid)")
					.setParameter("pid",pid);
		try{
			UserList ul = new UserList();
			ul.addUsers((List<User>)q.getResultList());
			return ul;
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

	/************************************************************
	Function name: createUserProject()
	Author: Christian Heckendorf
	Created date: 10/26/2013
	Purpose: Assigns a user to a project as a role
	************************************************************/
	public static void createUserProject(Dba dba, User user, Project project, Role role){
		EntityManager em = dba.getActiveEm();
		UserProject up;

		try{
			up = new UserProject();
			up.setUser(user);
			up.setProject(project);
			up.setRole(role);

			em.persist(up);
		} catch (Exception e){
		}
	}

	/************************************************************
	Function name: removeUserProject()
	Author: Christian Heckendorf
	Created date: 11/19/2013
	Purpose: Removes a user from a project
	************************************************************/
	public static void removeUserProject(Dba dba, UserProject userProject){
		EntityManager em = dba.getActiveEm();

		try{
			em.remove(userProject);
		} catch(Exception e){
		}
	}
}
