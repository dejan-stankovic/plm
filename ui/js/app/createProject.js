/*************************************************************************
		File name: createProject.js
		Author: Tandhy
		Created date: 10.24.2013
		Purpose: create a new project
**************************************************************************/

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

	$("#projectStatus").html("");
	tok = getToken();

	projectName = $("input#txtProjectName").val();
	
	//alert(tok);
	//alert(projectName);
	
	$.ajax({
		type: 'POST',
		url: '/plm/rest/projectmanage/create',
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			token: {
				token:tok
			},
			project:{
				name: projectName 
			},
		}),
		success: function(data){
			// message":"Success
			if(data.message=="Success")
			{
				$("#projectStatus").html("New Project has been created.");
				document.getElementById("txtProjectName").value="";
				// refresh the project dropdown list
				// set combobox to be empty before assigning with project data
				emptyComboBox($("#cur-project").data("kendoComboBox"));
				getCurProjects();
			}
			else
			{ /* Usually internal error or other */
				$("#projectStatus").html(data.message);
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

    $("#createProjectBtn").click(function(){ 
		// this function will execute if the button is being hit
		if($("input#txtProjectName").val()=="")
			$("#projectStatus").html("Project name can not leave empty.");
		else	
			createProject();

	});
	
	$('#userSignout').click( function() { 
		// call user signout in common.js
		userSignOut();
	});
	
    $("#clearBtn").click(function(){ /*resetFields();*/ });
});
