package edu.cs673.plm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Entity;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import edu.cs673.plm.model.UserList;
import edu.cs673.plm.model.ProjectList;
import edu.cs673.plm.model.ReleaseList;
import edu.cs673.plm.model.JSONReleaseRequest;
import edu.cs673.plm.model.JSONRelease;
import edu.cs673.plm.model.JSONProjectRequest;
import edu.cs673.plm.model.JSONProject;
import edu.cs673.plm.model.TokenMessage;
import edu.cs673.plm.model.StatusMessage;

@RunWith(JUnit4.class)
public class ProjectManagerTest extends JerseyTest{
	private SessionToken st = new SessionToken(1);
	private String tok = st.generateToken();

	@Override
	protected Application configure() {
		return new ResourceConfig(ProjectManager.class);
	}

	@Ignore
	private long countReleases(TokenMessage tm){
		ReleaseList res =  target("projectmanage").path("releases/p/1")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(tm,MediaType.APPLICATION_JSON_TYPE),ReleaseList.class);

		return res.getReleases().size();
	}

	@Test
	public void createRelease() {
		long count;
		JSONReleaseRequest req = new JSONReleaseRequest();
		TokenMessage tm = new TokenMessage();
		JSONRelease jr = new JSONRelease();

		jr.setVersion("1.0.0");
		jr.setStartDate(new Date(System.currentTimeMillis())); // Now
		jr.setEndDate(new Date(System.currentTimeMillis()+86400000)); // Tomorrow

		tm.setToken(tok);

		req.setToken(tm);
		req.setRelease(jr);

		count = countReleases(tm);

		StatusMessage res =  target("projectmanage").path("release/p/1")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(req,MediaType.APPLICATION_JSON_TYPE),StatusMessage.class);

		assertTrue(res.getMessage().equals("Success"));

		assertEquals(count+1,countReleases(tm));
	}

	@Test
	public void updateRelease(){
		long count;
		JSONReleaseRequest req = new JSONReleaseRequest();
		TokenMessage tm = new TokenMessage();
		JSONRelease jr = new JSONRelease();

		jr.setVersion("2.0.0");
		jr.setStartDate(new Date(System.currentTimeMillis())); // Now
		jr.setEndDate(new Date(System.currentTimeMillis()+86400000)); // Tomorrow

		tm.setToken(tok);

		req.setToken(tm);
		req.setRelease(jr);

		StatusMessage res =  target("projectmanage").path("release/r/1")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(req,MediaType.APPLICATION_JSON_TYPE),StatusMessage.class);

		assertTrue(res.getMessage().equals("Success"));
	}

	@Ignore
	private long countUsers(TokenMessage tm, long pid){
		UserList res = target("projectmanage").path("p").path(String.valueOf(pid)).path("users")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(tm,MediaType.APPLICATION_JSON_TYPE),UserList.class);

		return res.getUsers().size();
	}

	@Test
	public void createProject() {
		long count;
		JSONProjectRequest req = new JSONProjectRequest();
		TokenMessage tm = new TokenMessage();
		JSONProject jp = new JSONProject();
		UserList ul;

		jp.setName("TestProject");

		tm.setToken(tok);

		req.setToken(tm);
		req.setProject(jp);

		StatusMessage res =  target("projectmanage").path("create")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(req,MediaType.APPLICATION_JSON_TYPE),StatusMessage.class);

		assertTrue(res.getMessage().equals("Success"));

		assertEquals(1,countUsers(tm,res.getCode()));
	}

	@Test
	public void otherUserList(){
		TokenMessage tm = new TokenMessage();
		UserList ul;

		tm.setToken(tok);

		ul =  target("projectmanage").path("p/1/otherusers")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(tm,MediaType.APPLICATION_JSON_TYPE),UserList.class);

		assertEquals(0,ul.getUsers().size());

		ul =  target("projectmanage").path("p/2/otherusers")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(tm,MediaType.APPLICATION_JSON_TYPE),UserList.class);

		assertTrue(ul.getUsers().size()>0);
	}

	@Test
	public void addRemoveUserProject(){
		TokenMessage tm = new TokenMessage();
		StatusMessage sm;

		tm.setToken(tok);

		sm =  target("projectmanage").path("p/1/removeuser/u/2")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(tm,MediaType.APPLICATION_JSON_TYPE),StatusMessage.class);

		assertEquals(0,sm.getCode());

		sm =  target("projectmanage").path("p/1/removeuser/u/2")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(tm,MediaType.APPLICATION_JSON_TYPE),StatusMessage.class);

		assertEquals(2,sm.getCode());

		sm =  target("projectmanage").path("p/1/adduser/u/2")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(tm,MediaType.APPLICATION_JSON_TYPE),StatusMessage.class);

		assertEquals(0,sm.getCode());

		sm =  target("projectmanage").path("p/1/adduser/u/2")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(tm,MediaType.APPLICATION_JSON_TYPE),StatusMessage.class);

		assertEquals(3,sm.getCode());

	}
}
