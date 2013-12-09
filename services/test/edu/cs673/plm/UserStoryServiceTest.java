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

import edu.cs673.plm.model.UserStoryList;
import edu.cs673.plm.model.JSONStoryRequest;
import edu.cs673.plm.model.JSONUserStory;
import edu.cs673.plm.model.JSONStatus;
import edu.cs673.plm.model.JSONUser;
import edu.cs673.plm.model.TokenMessage;
import edu.cs673.plm.model.StatusMessage;

@RunWith(JUnit4.class)
public class UserStoryServiceTest extends JerseyTest{
	private SessionToken st = new SessionToken(1);
	private String tok = st.generateToken();

	@Override
	protected Application configure() {
		return new ResourceConfig(UserStoryService.class);
	}

	@Ignore
	private long countUserStories(TokenMessage tm){
		UserStoryList res =  target("userstory").path("r/1")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(tm,MediaType.APPLICATION_JSON_TYPE),UserStoryList.class);

		return res.getUserStories().size();
	}

	@Test
	public void createUserStory() {
		long count;
		JSONStoryRequest req = new JSONStoryRequest();
		TokenMessage tm = new TokenMessage();
		JSONUserStory jus = new JSONUserStory();
		JSONUser ju = new JSONUser();
		JSONStatus js = new JSONStatus();

		ju.setId(2);

		js.setId(1);

		jus.setName("name");
		jus.setDescription("desc");
		jus.setOwner(ju);
		jus.setStatus(js);

		tm.setToken(tok);

		req.setToken(tm);
		req.setUserStory(jus);

		count = countUserStories(tm);

		StatusMessage res =  target("userstory").path("create/r/1")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(req,MediaType.APPLICATION_JSON_TYPE),StatusMessage.class);

		assertTrue(res.getMessage().equals("Success"));

		assertEquals(count+1,countUserStories(tm));
	}

	@Test
	public void updateUserStory(){
		JSONStoryRequest req = new JSONStoryRequest();
		TokenMessage tm = new TokenMessage();
		JSONUserStory jus = new JSONUserStory();
		JSONUser ju = new JSONUser();
		JSONStatus js = new JSONStatus();

		ju.setId(1);

		js.setId(3);

		jus.setName("name");
		jus.setDescription("desc");
		jus.setOwner(ju);
		jus.setStatus(js);

		tm.setToken(tok);

		req.setToken(tm);
		req.setUserStory(jus);

		StatusMessage res =  target("userstory").path("update/u/1")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(req,MediaType.APPLICATION_JSON_TYPE),StatusMessage.class);

		assertTrue(res.getMessage().equals("Success"));
	}

	@Test
	public void migrateUserStory(){
		TokenMessage tm = new TokenMessage();

		tm.setToken(tok);

		StatusMessage res =  target("userstory").path("migrate/u/1/r/2")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(tm,MediaType.APPLICATION_JSON_TYPE),StatusMessage.class);

		assertTrue(res.getMessage().equals("Success"));
	}
}
