/************************************************************
Filename: ProjectDaoTest.java
Author: Christian Heckendorf
Created date: 09/29/2013
Purpose: Tests the ProjectDao class
Feature: None
************************************************************/
package edu.cs673.plm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import edu.cs673.plm.model.Project;

@RunWith(JUnit4.class)
public class ProjectDaoTest {

	/************************************************************
	Function name: getMemberCount
	Author: Christian Heckendorf
	Created date: 09/29/2013
	Purpose: Tests the getMemberCount function
	************************************************************/
	@Test
	public void getMemberCount() {
		Dba dba = new Dba(false);
		Project project = new Project();
		project.setId(1);

		assertEquals(2,ProjectDao.getMemberCount(dba,project));
	}
}
