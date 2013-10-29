package edu.cs673.plm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Entity;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import edu.cs673.plm.model.TaskList;
import edu.cs673.plm.model.JSONTask;
import edu.cs673.plm.model.JSONUser;
import edu.cs673.plm.model.JSONTaskRequest;
import edu.cs673.plm.model.TokenMessage;
import edu.cs673.plm.model.StatusMessage;

@RunWith(JUnit4.class)
public class TaskServiceTest extends JerseyTest{
	private SessionToken st = new SessionToken(1);
	private String tok = st.generateToken();

	@Override
	protected Application configure() {
		return new ResourceConfig(TaskService.class);
	}

	@Ignore
	private long countTasks(TokenMessage tm){
		TaskList res =  target("task").path("us/1")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(tm,MediaType.APPLICATION_JSON_TYPE),TaskList.class);

		return res.getTasks().size();
	}

	@Test
	public void createTask(){
		long count;
		JSONTaskRequest req = new JSONTaskRequest();
		TokenMessage tm = new TokenMessage();
		JSONTask jt = new JSONTask();
		JSONUser ju = new JSONUser();

		ju.setId(2);

		jt.setName("newTaskName");
		jt.setDescription("newTaskDescription");
		jt.setAssigned(ju);

		tm.setToken(tok);

		req.setToken(tm);
		req.setTask(jt);

		count = countTasks(tm);

		StatusMessage res =  target("task").path("create/us/1")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(req,MediaType.APPLICATION_JSON_TYPE),StatusMessage.class);

		assertTrue(res.getMessage().equals("Success"));

		assertEquals(count+1,countTasks(tm));
	}

	@Test
	public void updateTask(){
		long count;
		JSONTaskRequest req = new JSONTaskRequest();
		TokenMessage tm = new TokenMessage();
		JSONTask jt = new JSONTask();
		JSONUser ju = new JSONUser();

		ju.setId(1);

		jt.setName("updatedTaskName");
		jt.setDescription("updatedTaskDescription");
		jt.setAssigned(ju);

		tm.setToken(tok);

		req.setToken(tm);
		req.setTask(jt);

		StatusMessage res =  target("task").path("update/t/1")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(req,MediaType.APPLICATION_JSON_TYPE),StatusMessage.class);

		assertTrue(res.getMessage().equals("Success"));
	}
}
