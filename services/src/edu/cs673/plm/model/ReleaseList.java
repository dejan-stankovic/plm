/************************************************************
Filename: ReleaseList.java
Author: Christian Heckendorf
Created date: 10/07/2013
Purpose: Holds a list of JSONReleases
Feature: Dashboard
************************************************************/
package edu.cs673.plm.model;

import java.util.List;

public class ReleaseList{
	private List<JSONRelease> releases;

	/************************************************************
	Function name: addRelease()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Adds a release to the list
	************************************************************/
	public void addRelease(Release release){
		releases.add(new JSONRelease(release));
	}

	/************************************************************
	Function name: getReleases()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Returns the list of releases
	************************************************************/
	public List<JSONRelease> getReleases(){
		return releases;
	}

	/************************************************************
	Function name: setReleases()
	Author: Christian Heckendorf
	Created date: 10/07/2013
	Purpose: Sets the list of releases
	************************************************************/
	public void setReleases(List<JSONRelease> releases){
		this.releases=releases;
	}
}
