/*************************************************************************
		File name: createProject.js
		Author: Tandhy
		Created date: 10.24.2013
		Purpose: create a new project
**************************************************************************/

var selectedUid, selectedRid;

/************************************************************
Function name: getRoles
Author: Christian Heckendorf
Created date: 10/14/2013
Purpose: Gets the role for a user
************************************************************/
function getRoles(){
	var tok;

	tok = getToken();

	$.ajax({
		type: 'POST',
		url: '/plm/dashboard/projects',
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			token: tok
		}),
		success: function(data){
			//var role1 = $("#projectddl").data("kendoComboBox");
			// return userId role in the project

			/*for(x in data.roles){
				role1.dataSource.add(data.roles[x]);
			}*/
		},
		error: function(data){
			alert("error");
		}
	});
}


/************************************************************
Function name: createProject
Author: Tandhy
Created date: 10/28/2013
Purpose: create New Project
feature:
	1. Create new Project
	2. assign current user as project leader
	3. back to dashboard.html
************************************************************/
function createProject()
{
	var projectName, projectDescription;

	$("#errormsg").html("");

	createdId = selectedUid;
	projectName = $("input#txtProjectName").val();
	projectDescription = $("input#txtProjectDescription").val();

	$.ajax({
		type: 'POST',
		url: '/plm/rest/projectmanage',
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			// name, description
			name : projectName,
			description : projectDescription,
		}),
		success: function(data){

			/*the following code checkes whether the entered userid is valid */
			 if(data.code==0)
			  {
			  $("h1").html("New Project created.");
			  // redirect to dashboard
			  window.location="dashboard.html"; /*Redirect to the login page after succesful registration*/
			  }
			 else if(data.code==1)
			 {
			   $("#errormsg").html("Please try again."); /*displays error message*/
			 }
			   else { /* Usually internal error or other */
			   $("#errormsg").html(data.message);
			 }
		},
		error: function(data){
			alert("error");
		}
	});
}

/************************************************************
		Function name: onready
		Author: Tandhy
		Created date: 10/22/2013
		Purpose: ready function invoked when page is rendered
**************************************************************/

$(document).ready(function () {
		
	// initialize textarea
	$(document).ready(function(){
		$("#txtProjectDescription").kendoEditor();
	});	
		

    $("#createProjectBtn").click(function(){ 
		// this function will execute if the button is being hit
		saveNewProject();
	});
	
    $("#clearBtn").click(function(){ /*resetFields();*/ });
});
