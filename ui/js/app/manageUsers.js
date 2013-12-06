/*************************************************************************
File name: manageUsers.js
Author: Tandhy
Created date: 11/17/2013
Purpose: handle page functionality associated with manageUsers page
**************************************************************************/
/*global variables */
var selectedUid, deletedUid;

/************************************************************
Function name: getUsersInProject
Author: Tandhy
Created date: 10/20/2013
Purpose: Gets all users in the project
************************************************************/
function getUsersInProject(){
	var pid,tok;

	tok = getToken();
	pid = getCurProject();

	$.ajax({
		type: 'POST',
		url: '/plm/rest/projectmanage/p/'+pid+'/users',
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			token: tok
		}),
		success: function(data){
			var usersDataSource = new kendo.data.DataSource({});

			var i = 1;
			for(user in data.users)
			{
				usersDataSource.add({
					No:i,
					Id:data.users[user]['id'],
					Name:data.users[user]['name']
				});
				i++;
			}


			var grid = $("#grid").data("kendoGrid");
			grid.setDataSource(usersDataSource);
		},
		error: function(data){
			alert("error");
		}
	});
}

/************************************************************
Function name: getUsersInProject
Author: tandhy
Created date: 12/04/2013
Purpose: Gets all users in the project
************************************************************/
function getUsersInProjectComboBox(){
	var pid,tok;

	tok = getToken();
	pid = getCurProject();

	$.ajax({
		type: 'POST',
		url: '/plm/rest/projectmanage/p/'+pid+'/users',
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			token: tok
		}),
		success: function(data){
			var users = $("#deleteUsersddl").data("kendoComboBox");
			users.dataSource.add({"id":0,"name":"Select a user"});
			for(x in data.users){
				users.dataSource.add(data.users[x]);
			}
		},
		error: function(data){
			alert("error");
		}
	});
}

/************************************************************
Function name: addUserToProject
Author: Tandhy
Created date: 10/20/2013
Purpose: Add user into project
************************************************************/
function addUserToProject()
{
	$("#projectStatus").html("");
	//url: '/plm/rest/projectmanage/p/'+pid+'/adduser/u/'+uid
	var tok = getToken();
	var pid = getCurProject();
	var uid = selectedUid;

	if(uid==null) {
		return;
	}

	
	$.ajax({
		type: 'POST',
		url: '/plm/rest/projectmanage/p/' + pid + '/adduser/u/' + uid,
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			token: tok
		}),
		success: function(data){
			if(data.message=="Success")
			{
				$("usersStatus").html("User has been added to project.");
				// refresh the grid
				loadData();
			}
			else
			{ /* Usually internal error or other */
				$("#usersStatus").html(data.message);
			}
			
		},
		error: function(data){
			alert("error");
		}
	});
}

/************************************************************
Function name: getNonAssociatedUser
Author: Tandhy
Created date: 10/20/2013
Purpose: Get list of users that are not associated with the project 
************************************************************/
function getNonAssociatedUser(){
	var tok = getToken();
	var pid = getCurProject();

	$.ajax({
		type: 'POST',
		url: '/plm/rest/projectmanage/p/' + pid + '/otherusers',
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			token: tok
		}),
		success: function(data){
			var users = $("#usersddl").data("kendoComboBox");	
			users.dataSource.add({"id":0,"name":"Select a user"});
			for(x in data.users){
				users.dataSource.add(data.users[x]);
			}
		},
		error: function(data){
			alert("error");
		}
	});
}

/************************************************************
Function name: loadData
Author: Tandhy
Created date: 11/17/2013
Purpose: load fetches the data from services and loads page
************************************************************/
function loadData() {
    token = getToken();
    projectId = getCurProject();
    loadManageUsersGrid();
	emptyComboBox($("#usersddl").data("kendoComboBox"));
	$("#usersddl").data("kendoComboBox").select(0);
	emptyComboBox($("#deleteUsersddl").data("kendoComboBox"));
	$("#deleteUsersddl").data("kendoComboBox").select(0);
	getNonAssociatedUser();
	getUsersInProject();
	getUsersInProjectComboBox();
}

/************************************************************
Function name: loadManageUsersGrid
Author: Tandhy
Created date: 11/17/2013
Purpose: load user list grid
************************************************************/
function loadManageUsersGrid() {
    $("#grid").kendoGrid({
		dataSource: {
            /*data: [
				{ No: "1", Member: "tandhy1", Role: "Developer" },
				{ No: "2", Member: "tandhy2", Role: "Developer" },
				{ No: "3", Member: "tandhy3", Role: "Developer" },
				{ No: "4", Member: "tandhy4", Role: "Developer" },
			],*/
            pageSize: 15,
			schema: {
				model: { id: "Id" }
			}
        },
        columns: [{
			field: "No",
			Title: "No",
			width: "5%",
		}, {
            hidden: true,
			field: "Id",
            Title: "Id",
            width: "5%",
        }, {
            field: "Name",
            Title: "Name",
            width: "90%",
        }
        ],
        sortable: {
            mode: "multiple",
            allowUnsort: true
        },
		editable: false,
        reorderable: true,
        resizable: true,
        pageable: true,
        scrollable: false,
        selectable: "row"
    });

}

/************************************************************
Function name: removeUserFromProject
Author: Tandhy
Created date: 11/17/2013
Purpose: remove user from project
************************************************************/
function removeUserFromProject()
{
	var tok = getToken();
	var pid = getCurProject();
	var uid = deletedUid;

	$.ajax({
		type: 'POST',
		url: '/plm/rest/projectmanage/p/'+pid+'/removeuser/u/'+uid,
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			token: tok
		}),
		success: function(data){
			if(data.message=="Success")
			{
				$("usersStatus").html("User has been removed to project.");
				loadData();
			}
			else
			{ /* Usually internal error or other */
				$("#usersStatus").html(data.message);
			}
		},
		error: function(data){
			alert("error");
		}
	});
}


/************************************************************
Function name: onready
Author: tandhy
Created date: 11/2/2013
Purpose: document on ready function to load the page
************************************************************/
$(document).ready(function () {	
    $("#usersddl").kendoComboBox({
		placeholder:"Select a User",
        dataSource: [],
        dataTextField: "name",
        dataValueField: "id",
        select: function(e){
            if (e.item == null) return;
            var DataItem = this.dataItem(e.item.index());
            selectedUid = DataItem.id;
        }
    });

    $("#deleteUsersddl").kendoComboBox({
		placeholder:"Select a User",
        dataSource: [],
        dataTextField: "name",
        dataValueField: "id",
        select: function(e){
            if (e.item == null) return;
            var DataItem = this.dataItem(e.item.index());
            deletedUid = DataItem.id;
        }
    });

    $("#addUserBtn").click(function(){ 
		// this function will execute if the button is being hit
		if( selectedUid == null || selectedUid == 0 )
			$("#usersStatus").html("Select a user to be added to project.");
		else	
			//$("#usersStatus").html("User has been added to project.");
			addUserToProject();

	});
	
    $("#deleteUserBtn").click(function(){ 
		// this function will execute if the button is being hit
		if( deletedUid == null  || deletedUid == 0 )
			$("#usersStatus").html("Select a user to be removed from project.");
		else	
			//$("#usersStatus").html("User has been added to project.");
			removeUserFromProject();

	});
	
	loadData();

	$('#userSignout').click( function() { 
		// call user signout in common.js
		userSignOut();
	});	
	
});
