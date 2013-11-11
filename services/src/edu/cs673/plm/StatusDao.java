/************************************************************
Filename: StatusDao.java
Author: Christian Heckendorf
Created date: 11/02/2013
Purpose: Status object access
Feature: None
************************************************************/
package edu.cs673.plm;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.cs673.plm.model.JSONStatus;
import edu.cs673.plm.model.StatusList;
import edu.cs673.plm.model.Status;

public class StatusDao {
	public static final long STATUS_INITIAL = 1;
	public static final long STATUS_PENDING = 2;
	public static final long STATUS_INPROGRESS = 3;
	public static final long STATUS_COMPLETE = 4;

	/************************************************************
	Function name: getStatusList()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Returns the statuses in the system
	************************************************************/
	public static StatusList getStatusList(Dba dba){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select status from Status status");
		try{
			StatusList sl = new StatusList();
			sl.addStatuses((List<Status>)q.getResultList());
			return sl;
		} catch(Exception e){
			return null;
		}
	}

	/************************************************************
	Function name: getStatusById()
	Author: Christian Heckendorf
	Created date: 11/02/2013
	Purpose: Finds a status by id
	************************************************************/
	public static Status getStatusById(Dba dba, long sid){
		EntityManager em = dba.getActiveEm();
		try{
			return em.find(Status.class,sid);
		} catch(Exception e){
			return null;
		}
	}
}
