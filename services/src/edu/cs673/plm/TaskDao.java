/************************************************************
Filename: TaskDao.java
Author: Christian Heckendorf
Created date: 10/27/2013
Purpose: Accesses the task entity
Feature: Tasks
************************************************************/
package edu.cs673.plm;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.cs673.plm.model.JSONTask;
import edu.cs673.plm.model.Task;
import edu.cs673.plm.model.TaskList;
import edu.cs673.plm.model.StatusMessage;

public class TaskDao {
	/************************************************************
	Function name: getTaskList()
	Author: Christian Heckendorf
	Created date: 10/27/2013
	Purpose: Returns a list of tasks under a user story
	************************************************************/
	public static TaskList getTaskList(Dba dba, long usid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select t from Task t where t.userStory.id = :usid")
					.setParameter("usid",usid);

		try{
			TaskList tl = new TaskList();
			tl.addTasks((List<Task>)q.getResultList());
			return tl;
		} catch (Exception e){
			return null;
		}
	}

	/************************************************************
	Function name: createTask()
	Author: Christian Heckendorf
	Created date: 10/27/2013
	Purpose: Creates a task under a user story
	************************************************************/
	public static StatusMessage createTask(Dba dba, long usid, Task task){
		EntityManager em = dba.getActiveEm();

		task.setUserStory(UserStoryDao.getUserStoryById(dba,usid));

		if(task.getUserStory()==null)
			return new StatusMessage(-1,"Internal Error");

		em.persist(task);
		em.flush();

		return new StatusMessage(task.getId(),"Success");
	}

	/************************************************************
	Function name: updateTask()
	Author: Christian Heckendorf
	Created date: 10/27/2013
	Purpose: Updates a task in the database
	************************************************************/
	public static StatusMessage updateTask(Dba dba, Task task){
		EntityManager em = dba.getActiveEm();
		em.persist(task);
		return new StatusMessage(task.getId(),"Success");
	}

	/************************************************************
	Function name: convert()
	Author: Christian Heckendorf
	Created date: 10/27/2013
	Purpose: Converts a JSONTask to a Task
	************************************************************/
	public static Task convert(Dba dba, JSONTask jtask){
		EntityManager em = dba.getActiveEm();
		Task ret;

		if(jtask.getId()>0)
			ret = TaskDao.getTaskById(dba,jtask.getId());
		else
			ret = new Task();

		ret.overlay(jtask);

		if(jtask.getAssigned()!=null)
			ret.setUserAssigned(UserDao.getUserById(dba,jtask.getAssigned().getId()));

		if(jtask.getStatus()!=null){
			ret.setStatus(StatusDao.getStatusById(dba,jtask.getStatus().getId()));
		}

		return ret;
	}

	/************************************************************
	Function name: getTaskById()
	Author: Christian Heckendorf
	Created date: 10/27/2013
	Purpose: Returns a task given a task id
	************************************************************/
	public static Task getTaskById(Dba dba, long tid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select t from Task t where t.id = :tid")
					.setParameter("tid",tid);

		try{
			return (Task)q.getSingleResult();
		} catch (Exception e){
			return null;
		}
	}
}
