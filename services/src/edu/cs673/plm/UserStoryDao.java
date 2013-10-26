package edu.cs673.plm;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.cs673.plm.model.UserStory;
import edu.cs673.plm.model.UserStoryList;
import edu.cs673.plm.model.StatusMessage;

public class UserStoryDao {
	public static StatusMessage createUserStory(Dba dba, long rid, UserStory userStory){
		EntityManager em = dba.getActiveEm();
		userStory.setRelease(ReleaseDao.getReleaseById(dba,rid));
		em.persist(userStory);
		em.flush();
		return new StatusMessage(userStory.getId(),"Success");
	}
}
