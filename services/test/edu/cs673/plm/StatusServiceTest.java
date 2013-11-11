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

import edu.cs673.plm.model.StatusList;
import edu.cs673.plm.model.TokenMessage;

@RunWith(JUnit4.class)
public class StatusServiceTest extends JerseyTest{
	private SessionToken st = new SessionToken(1);
	private String tok = st.generateToken();

	@Override
	protected Application configure() {
		return new ResourceConfig(StatusService.class);
	}

	@Test
	public void countStatuses(){
		TokenMessage tm = new TokenMessage();
		tm.setToken(tok);

		StatusList res =  target("status")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(tm,MediaType.APPLICATION_JSON_TYPE),StatusList.class);

		assertTrue(res.getStatuses().size()>0);
	}
}
