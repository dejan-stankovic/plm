package edu.cs673.plm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import edu.cs673.plm.model.User;
import edu.cs673.plm.model.Task;

@RunWith(JUnit4.class)
public class UserDaoTest {

	@Test
	public void getTaskCount() {
		Dba dba = new Dba(true);

		assertEquals(1,UserDao.getUserById(dba,1).getTasksAssigned().size());
	}
}
