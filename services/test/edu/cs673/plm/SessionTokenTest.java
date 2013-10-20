/************************************************************
Filename: SessionTokenTest.java
Author: Christian Heckendorf
Created date: 09/28/2013
Purpose: Tests the SessionToken class
Feature: None
************************************************************/
package edu.cs673.plm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SessionTokenTest {

	/************************************************************
	Function name: decodeToken
	Author: Christian Heckendorf
	Created date: 09/28/2013
	Purpose: Tests if the class can decode a token properly
	************************************************************/
	@Test
	public void decodeToken() {
		SessionToken decst;
		SessionToken st = new SessionToken(1);
		String tok = st.generateToken();

		decst = new SessionToken(tok);

		assertEquals(1,decst.getUid());
	}
}
