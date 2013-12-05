var selectedUid;
var selectedDelUid;

/************************************************************
Function name: getUsersInProject
Author: Christian Heckendorf
Created date: 10/14/2013
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
			var users = $("#deleteUsersddl").data("kendoComboBox");
			users.setDataSource(new kendo.data.DataSource({ data: [] })); // Empty it first

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
Function name: getUsersNotInProject
Author: Christian Heckendorf
Created date: 10/14/2013
Purpose: Gets all users in the project
************************************************************/
function getUsersNotInProject(){
	var pid,tok;

	tok = getToken();
	pid = getCurProject();

	$.ajax({
		type: 'POST',
		url: '/plm/rest/projectmanage/p/'+pid+'/otherusers',
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
			users.setDataSource(new kendo.data.DataSource({ data: [] })); // Empty it first

			for(x in data.users){
				users.dataSource.add(data.users[x]);
			}
		},
		error: function(data){
			alert("error");
		}
	});
}

function addUser(){
	var pid,tok;

	tok = getToken();
	pid = getCurProject();

	$.ajax({
		type: 'POST',
		url: '/plm/rest/projectmanage/p/'+pid+'/adduser/u/'+selectedUid,
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			token: tok
		}),
		success: function(data){
			getUsersInProject();
			getUsersNotInProject();
		},
		error: function(data){
			alert("error");
		}
	});
}


function remUser(){
	var pid,tok;

	tok = getToken();
	pid = getCurProject();

	$.ajax({
		type: 'POST',
		url: '/plm/rest/projectmanage/p/'+pid+'/removeuser/u/'+selectedDelUid,
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			token: tok
		}),
		success: function(data){
			getUsersInProject();
			getUsersNotInProject();
		},
		error: function(data){
			alert("error");
		}
	});
}


$(document).ready(function () {
    $("#usersddl").kendoComboBox({
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
        dataSource: [],
        dataTextField: "name",
        dataValueField: "id",
        select: function(e){
            if (e.item == null) return;
            var DataItem = this.dataItem(e.item.index());
            selectedDelUid = DataItem.id;
        }
    });

    getUsersInProject();
    getUsersNotInProject();

	$("#addUserBtn").click(function(){ addUser();});
	$("#deleteUserBtn").click(function(){ remUser();});
});


