package edu.cs673.plm.model;

import java.util.List;

public class RoleList{
	private List<JSONRole> roles;

	public void addRole(Role role){
		roles.add(new JSONRole(role));
	}

	public List<JSONRole> getRoles(){
		return roles;
	}

	public void setRoles(List<JSONRole> roles){
		this.roles=roles;
	}
}
