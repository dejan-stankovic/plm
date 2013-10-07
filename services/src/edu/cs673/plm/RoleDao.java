/***************************************************************
Filename: RoleDao.java
Author: Christian Heckendorf
Created Date: 9/29/13
Purpose: Accesses the Role entity
Features: Role management
***************************************************************/
package edu.cs673.plm;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.cs673.plm.model.JSONRole;
import edu.cs673.plm.model.RoleList;
import edu.cs673.plm.model.Role;

public class RoleDao {
	public static final long ROLE_STAKEHOLDER = 1;
	public static final long ROLE_PROJECT_LEADER = 2;
	public static final long ROLE_BUSINESS_ANALYST = 3;
	public static final long ROLE_QUALITY_ASSURANCE = 4;
	public static final long ROLE_DEVELOPER= 5;
	public static final long ROLE_RELEASE_MANAGER = 6;

	/************************************************************
	Function name: getRoleList()
	Author: Christian Heckendorf
	Created date: 10/04/2013
	Purpose: Returns a list of available roles
	************************************************************/
	public static RoleList getRoleList(Dba dba){
		EntityManager em = dba.getActiveEm();
		Query q = em.createQuery("select role from Role role");
		try{
			List<Role> list = (List<Role>)q.getResultList();
			RoleList rl = new RoleList();
			rl.setRoles(new ArrayList<JSONRole>());
			for(Role r : list){
				rl.addRole(r);
			}
			return rl;
		} catch(Exception e){
			return null;
		}
	}

	/************************************************************
	Function name: findRoleById()
	Author: Christian Heckendorf
	Created date: 10/04/2013
	Purpose: Returns a role based on the ID
	************************************************************/
	public static Role findRoleById(Dba dba, long rid){
		EntityManager em = dba.getActiveEm();
		try{
			return em.find(Role.class,rid);
		} catch(Exception e){
			return null;
		}
	}
}
