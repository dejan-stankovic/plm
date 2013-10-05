/************************************************************
Filename: UserProjectDao.java
Author: Christian Heckendorf
Created date: 10/03/2013
Purpose: Helper methods to acces the UserProject table
Feature: None
************************************************************/
package edu.cs673.plm;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.cs673.plm.model.UserProject;
import edu.cs673.plm.model.Role;

public class UserProjectDao {
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
