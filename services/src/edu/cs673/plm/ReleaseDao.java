package edu.cs673.plm;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.cs673.plm.model.UserStory;
import edu.cs673.plm.model.UserStoryList;

public class ReleaseDao {
	public static UserStoryList getUserStoryList(Dba dba, long rid){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select u from UserStory u where u.release.id = :rid")
					.setParameter("rid",rid);
		try{
			UserStoryList usl = new UserStoryList();
			usl.addUserStories((List<UserStory>)q.getResultList());
			return usl;
		} catch(Exception e){
			return null;
		}
	}
}
