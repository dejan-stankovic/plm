/************************************************************
Filename: Permission.java
Author: Christian Heckendorf
Created date: 10/05/2013
Purpose: Holds permission information
Feature: None
************************************************************/
package edu.cs673.plm;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import edu.cs673.plm.model.User;
import edu.cs673.plm.model.UserProject;

public class Permission {
	public static final String[] names = {
		"ViewProject",
		"CreateRelease",
		"CreateUserStory",
		"CreateTask",
		"CreateBug",
		"InviteUser",
		"SetRole",
		"EditBug",
		"EditTask",
		"EditRelease",
	};
	public static final int VIEW_PROJECT = 0;
	public static final int CREATE_RELEASE = 1;
	public static final int CREATE_USER_STORY = 2;
	public static final int CREATE_TASK = 3;
	public static final int CREATE_BUG = 4;
	public static final int INVITE_USER = 5;
	public static final int SET_ROLE = 6;
	public static final int EDIT_BUG = 7;
	public static final int EDIT_TASK = 8;
	public static final int EDIT_RELEASE = 9;

	private static final int[][] rolePermissions = {
		{}, // Empty -- Roles are 1-indexed
		{VIEW_PROJECT}, // Stakeholder
		{VIEW_PROJECT,CREATE_RELEASE,CREATE_TASK,INVITE_USER,SET_ROLE}, // Project Leader
		{VIEW_PROJECT,CREATE_USER_STORY}, // Business Analyst
		{VIEW_PROJECT,CREATE_BUG}, // Quality Assurance
		{VIEW_PROJECT,EDIT_BUG,EDIT_TASK}, // Developer
		{VIEW_PROJECT,EDIT_RELEASE}, // Release Manager
	};

	/************************************************************
	Function name: getPermissions()
	Author: Christian Heckendorf
	Created date: 10/05/2013
	Purpose: Generates permissions by role
	************************************************************/
	public static List<String> getPermissions(long roleId){
		List<String> l = new ArrayList<String>();
		for(int p : rolePermissions[(int)roleId]){
			l.add(names[p]);
		}
		return l;
	}

	/************************************************************
	Function name: canAccess()
	Author: Christian Heckendorf
	Created date: 10/06/2013
	Purpose: Returns true if the role has access to a feature
	************************************************************/
	public static boolean canAccess(long roleId, int permission){
		for(int p : rolePermissions[(int)roleId]){
			if(p==permission)
				return true;
		}
		return false;
	}

	/************************************************************
	Function name: canAccess()
	Author: Christian Heckendorf
	Created date: 10/06/2013
	Purpose: Returns true if the user has access to a feature
	************************************************************/
	public static boolean canAccess(Dba dba, SessionToken st, long pid, int permission){
		try{
			if(st.getUid()==0)
				return false;

			User user = UserDao.getUserById(dba,st.getUid());
			UserProject up = UserProjectDao.findUserProjectByPid(dba,st.getUid(),pid);

			return canAccess(up.getRole().getId(),permission);
		} catch(Exception e){
			return false;
		}
	}
}
