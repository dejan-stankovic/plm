/***************************************************************
Filename: UserDao.java
Author: Christian Heckendorf
Created Date: 9/21/13
Purpose: Helper methods to access the User database table
Features: Probably all features
***************************************************************/
package edu.cs673.plm;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.cs673.plm.model.User;
import edu.cs673.plm.model.StatusMessage;
import edu.cs673.plm.model.Bug;
import edu.cs673.plm.model.Task;
import edu.cs673.plm.model.TaskList;
import edu.cs673.plm.model.BugList;

public class UserDao {
	/************************************************************
	Function name: getBugList()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Returns a list of tasks for a release for a user
	************************************************************/
	public static BugList getBugList(Dba dba, long uid, long pid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select b from Bug b where "+
								"b.userAssigned.id = :uid "+
								"and b.project.id = :pid")
					.setParameter("uid",uid)
					.setParameter("pid",pid);
		try{
			BugList bl = new BugList();
			bl.addBugs((List<Bug>)q.getResultList());
			return bl;
		} catch(Exception e){
			return null;
		}
	}

	/************************************************************
	Function name: getBugList()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Returns a list of bugs assigned to a user
	************************************************************/
	public static BugList getBugList(Dba dba, long uid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select b from Bug b where b.userAssigned.id = :uid")
					.setParameter("uid",uid);
		try{
			BugList bl = new BugList();
			bl.addBugs((List<Bug>)q.getResultList());
			return bl;
		} catch(Exception e){
			return null;
		}
	}

	/************************************************************
	Function name: getTaskList()
	Author: Christian Heckendorf
	Created date: 10/08/2013
	Purpose: Returns a list of tasks for a release for a user
	************************************************************/
	public static TaskList getTaskList(Dba dba, long uid, long rid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select t from Task t, UserStory userStory "+
								"where t.userStory.id = userStory.id "+
								"and t.userAssigned.id = :uid "+
								"and userStory.release.id = :rid")
					.setParameter("uid",uid)
					.setParameter("rid",rid);
		try{
			TaskList tl = new TaskList();
			tl.addTasks((List<Task>)q.getResultList());
			return tl;
		} catch(Exception e){
			return null;
		}
	}

	/************************************************************
	Function name: getTaskList()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Returns a list of tasks assigned to a user
	************************************************************/
	public static TaskList getTaskList(Dba dba, long uid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select t from Task t where t.userAssigned.id = :uid")
					.setParameter("uid",uid);
		try{
			TaskList tl = new TaskList();
			tl.addTasks((List<Task>)q.getResultList());
			return tl;
		} catch(Exception e){
			return null;
		}
	}

	/************************************************************
	Function name: getUserById()
	Author: Christian Heckendorf
	Created date: 10/01/2013
	Purpose: Fills in a user object by ID
	************************************************************/
	public static User getUserById(Dba dba, long uid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select user from User user where user.id = :id")
			.setParameter("id",uid);
		try{
			User user = (User)q.getSingleResult();
			return user;
		} catch (Exception e){
		}
		return null;
	}

	/***************************************************************
	Function name: nameValid
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Checks if the name has been used or not
	***************************************************************/
	private static boolean nameValid(EntityManager em, User user){
		if(((Long) em.createQuery("select count(*) from User user where user.name = :name")
					.setParameter("name",user.getName()).getSingleResult()).intValue()>0)
			return false;
		return true;
	}

	/***************************************************************
	Function name: createUser
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Creates an entry in the User table
	***************************************************************/
	public static StatusMessage createUser(Dba dba, User user) {
		EntityManager em = dba.getActiveEm();
		if(nameValid(em,user)){
			em.persist(user);
			return new StatusMessage(0,"Success");
		}
		return new StatusMessage(1,"Invalid Name");
	}

	/***************************************************************
	Function name: getUserId
	Author: Christian Heckendorf
	Created Date: 9/28/13
	Purpose: Finds the User ID for the requested user
	***************************************************************/
	public static long getUserId(Dba dba, User user){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select user.id from User user where user.name = :name and user.password = :pass")
					.setParameter("name",user.getName())
					.setParameter("pass",user.getPassword());
		try{
			long uid = ((Long)q.getSingleResult()).longValue();
			return uid;
		} catch(Exception e){
			return 0;
		}
	}
}
