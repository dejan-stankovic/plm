/***************************************************************
Filename: Role.java
Author: Christian Heckendorf
Created Date: 9/29/13
Purpose: Holds a role
Features: None
***************************************************************/
package edu.cs673.plm.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Role{
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private long id;
	private String name;

	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name="UserProject",joinColumns=@JoinColumn(name="roleId"),inverseJoinColumns=@JoinColumn(name="id"))
	private List<UserProject> userProjects;

	/***************************************************************
	Function name: getId
	Author: Christian Heckendorf
	Created Date: 9/29/13
	Purpose: Gets an id
	***************************************************************/
	public long getId(){
		return id;
	}

	/***************************************************************
	Function name: setId
	Author: Christian Heckendorf
	Created Date: 9/29/13
	Purpose: Sets an id
	***************************************************************/
	public void setId(long id){
		this.id=id;
	}

	/***************************************************************
	Function name: getName
	Author: Christian Heckendorf
	Created Date: 9/29/13
	Purpose: Gets a name
	***************************************************************/
	public String getName(){
		return name;
	}

	/***************************************************************
	Function name: setName
	Author: Christian Heckendorf
	Created Date: 9/29/13
	Purpose: Sets a name
	***************************************************************/
	public void setName(String name){
		this.name=name;
	}

	/***************************************************************
	Function name: getUserProjects
	Author: Christian Heckendorf
	Created Date: 9/29/13
	Purpose: Gets a list of UserProjects
	***************************************************************/
	public List<UserProject> getUserProjects(){
		return userProjects;
	}

	/***************************************************************
	Function name: setUserProjects
	Author: Christian Heckendorf
	Created Date: 9/29/13
	Purpose: Sets a list of UserProjects
	***************************************************************/
	public void setUserProjects(List<UserProject> userProjects){
		this.userProjects=userProjects;
	}
}
