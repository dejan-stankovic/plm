/************************************************************
Filename: PermissionTest.java
Author: Christian Heckendorf
Created date: 10/06/2013
Purpose: Testing Permission class
Feature: Probably every feature except login and register
************************************************************/
package edu.cs673.plm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PermissionTest {

	@Test
	/************************************************************
	Function name: canAccess()
	Author: Christian Heckendorf
	Created date: 10/06/2013
	Purpose: Tests some basic access levels
	************************************************************/
	public void canAccess() {
		assertEquals(true,Permission.canAccess(RoleDao.ROLE_STAKEHOLDER,Permission.VIEW_PROJECT));
		assertEquals(false,Permission.canAccess(RoleDao.ROLE_STAKEHOLDER,Permission.CREATE_RELEASE));

		assertEquals(true,Permission.canAccess(RoleDao.ROLE_PROJECT_LEADER,Permission.SET_ROLE));
		assertEquals(true,Permission.canAccess(RoleDao.ROLE_PROJECT_LEADER,Permission.CREATE_RELEASE));
	}
}
