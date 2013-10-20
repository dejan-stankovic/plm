/************************************************************
Filename: TaskList.java
Author: Christian Heckendorf
Created date: 10/07/2013
Purpose: Holds a list of JSONTasks
Feature: Dashboard
************************************************************/
package edu.cs673.plm.model;

import java.util.List;
import java.util.ArrayList;

public class TaskList{
	private List<JSONTask> tasks;

	/************************************************************
	Function name: TaskList
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Default constructor
	************************************************************/
	public TaskList(){
		tasks = new ArrayList<JSONTask>();
	}

	/************************************************************
	Function name: addTask()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Adds a task to the list
	************************************************************/
	public void addTask(Task task){
		tasks.add(new JSONTask(task));
	}

	/************************************************************
	Function name: addTasks()
	Author: Christian Heckendorf
	Created date: 10/13/2013
	Purpose: Adds a list of tasks
	************************************************************/
	public void addTasks(List<Task> tasks){
		for(Task t : tasks){
			this.addTask(t);
		}
	}

	/************************************************************
	Function name: getTasks()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Returns the list of tasks
	************************************************************/
	public List<JSONTask> getTasks(){
		return tasks;
	}

	/************************************************************
	Function name: setTasks()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Sets the list of tasks
	************************************************************/
	public void setTasks(List<JSONTask> tasks){
		this.tasks=tasks;
	}
}
