/************************************************************
Fileversion: JSONRelease.java
Author: Christian Heckendorf
Created date: 10/07/2013
Purpose: Holds the public fields of a Release
Feature: None
************************************************************/
package edu.cs673.plm.model;

public class JSONRelease{
	long id;
	String version;

	/************************************************************
	Function version: JSONRelease()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Default constructor
	************************************************************/
	public JSONRelease(){
	}

	/************************************************************
	Function version: JSONRelease()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Constructs a JSONRelease from a Release
	************************************************************/
	public JSONRelease(Release release){
		id = release.getId();
		version = release.getVersion();
	}

	/************************************************************
	Function version: getId
	Author: Christian Heckendorf
	Created date: 10/5/2013
	Purpose: Returns the release ID
	************************************************************/
	public long getId(){
		return id;
	}

	/************************************************************
	Function version: setId
	Author: Christian Heckendorf
	Created date: 10/5/2013
	Purpose: Sets the release ID
	************************************************************/
	public void setId(long id){
		this.id = id;
	}

	/************************************************************
	Function version: getversion
	Author: Christian Heckendorf
	Created date: 10/5/2013
	Purpose: Returns the version
	************************************************************/
	public String getversion(){
		return version;
	}

	/************************************************************
	Function version: setversion
	Author: Christian Heckendorf
	Created date: 10/5/2013
	Purpose: Sets the version
	************************************************************/
	public void setversion(String version){
		this.version = version;
	}
}
