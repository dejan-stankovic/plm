/*************************************************************************
		File name: manageroles.js
		Author: Alan
		Created date: 09/30/2013
		Purpose: client functionality associated with manageroles page
**************************************************************************/

var selectedUid, selectedRid;

/************************************************************
Function name: setRole
Author: Christian Heckendorf
Created date: 10/14/2013
Purpose: Sets the role for a user
************************************************************/
function setRole(){
	var tok,pid,uid,rid;

	uid = selectedUid;
	rid = selectedRid;

	if(uid==null || rid==null)
		return;

	tok = getToken();
	pid = getCurProject();

	$.ajax({
		type: 'POST',
		url: '/plm/rest/rolemanage/p/'+pid+'/u/'+uid+'/r/'+rid,
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			token: tok
		}),
		success: function(data){
			$("#rolestatus").html(data.userName+" set as "+data.roleName);
		},
		error: function(data){
			alert("error");
		}
	});
}

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
		url: '/plm/rest/rolemanage',
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			token: tok
		}),
		success: function(data){
			var role1 = $("#rolesddl").data("kendoComboBox");

			for(x in data.roles){
				role1.dataSource.add(data.roles[x]);
			}
		},
		error: function(data){
			alert("error");
		}
	});
}

/************************************************************
Function name: getRole
Author: Christian Heckendorf
Created date: 10/14/2013
Purpose: Gets all roles in the system
************************************************************/
function getRole(){
	var tok;
	var uid = selectedUid;
	var pid;

	if(uid==null)
		return;

	tok = getToken();
	pid = getCurProject();

	$.ajax({
		type: 'POST',
		url: '/plm/rest/rolemanage/p/'+pid+'/u/'+uid,
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			token: tok
		}),
		success: function(data){
			$("#rolestatus").html(data.userName+" is a "+data.roleName);
		},
		error: function(data){
			alert("error");
		}
	});
}

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
			var users = $("#usersac").data("kendoComboBox");

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
		Function name: onready
		Author: Alan
		Created date: 09/30/2013
		Purpose: ready function invoked when page is rendered
**************************************************************/

$(document).ready(function () {
    $("#rolesddl").kendoComboBox({
        dataSource: [],
        dataTextField: "name",
        dataValueField: "id",
        select: function(e){
            if (e.item == null) return;
            var DataItem = this.dataItem(e.item.index());
            selectedRid = DataItem.id;
        }
    });

    $("#usersac").kendoComboBox({
        dataSource: [],
        dataTextField: "name",
        dataValueField: "id",
        select: function(e){
            if (e.item == null) return;
            var DataItem = this.dataItem(e.item.index());
            selectedUid = DataItem.id;
        }
    });

    getRoles();
    getUsersInProject();

    $("#getBtn").click(function(){ getRole(); });
    $("#setBtn").click(function(){ setRole(); });
    $("#clearBtn").click(function(){ /*resetFields();*/ });
});

/* TODO: Implement the more complex view
$(document).ready(function () {

    var projects = ["Select", "Employee Portal", "HR Portal", "SAP Integration"];
    var roles = ["Select", "Stake Holder", "Business Analyst", "Project Leader", "Developer", "QA Tester", "Release Manager"];
    var users = ["Alan", "Christian", "Manav", "Rachit", "Tandy", "Yuvraj"];
    var roleMapData = [
        { "Project": "Employee Portal", "User": "Christian", "Role": "Project Leader" },
        { "Project": "Employee Portal", "User": "Manav", "Role": "Developer" },
         { "Project": "Employee Portal", "User": "Tandy", "Role": "QA Tester" },
        { "Project": "Employee Portal", "User": "Yuvraj", "Role": "Release Manager" },
        { "Project": "HR Portal", "User": "Rachit", "Role": "Stake Holder" },
        { "Project": "HR Portal", "User": "Vipul", "Role": "Release Manager" },
        { "Project": "SAP Integration", "User": "Alan", "Role": "Business Analyst" },
    ];
    $("#projectddl").kendoComboBox({
        dataSource: projects,
        optionLabel: "select project"
    });
    $("#rolesddl").kendoComboBox({
        dataSource: roles,
        optionLabel: "select role"
    });
    $("#usersac").kendoAutoComplete({
        dataSource: users,
        filter: "startswith",
        placeholder: "select user...",
        separator: ", "
    });
    $("#newRolesddl").kendoComboBox({
        dataSource: roles,
        optionLabel: "select role"
    });
    $("#newUsersddl").kendoAutoComplete({
        dataSource: users,
        filter: "startswith",
        placeholder: "select user...",
        separator: ", "
    });

    $("#rolesGrid").kendoGrid({
        dataSource: {
            data: roleMapData,
            pageSize: 15
        },
        sortable: {
            mode: "multiple",
            allowUnsort: true
        },
        columns: [{
            field: "Edit",
            Title: "Edit",
            width: "5%",
            filterable:false,
            template: "<span style='align-content:center' style='text-align:center;' class='icon_24 delete' title='Delete User Role mapping'></span>"
        }, {
            field: "User",
            Title: "User",
            width: "80%"
        }, {
            field: "Role",
            Title: "Role",
            width: "10%"
        }
        ],
        reorderable: true,
        resizable: true,
        pageable: true,
        scrollable: false,
        filterable: {
            extra: false,
            operators: {
                string: {
                    startswith: "Starts with",
                    eq: "Is equal to",
                    neq: "Is not equal to"
                }
            }
        }
    });

    $("filterBtn").click(function (e) {
        var grid = $("#rolesGrid").data("kendoGrid").dataSource.filter(
            [{ field: "Project", operator: "eq", value: $("projectddl").val() },
            { field: "User", operator: "eq", value: $("usersac").val() },
            { field: "Role", operator: "eq", value: $("rolesddl").val() }]
         );
        grid.refresh();
    });
    $("clearBtn").click(function (e) {
        var grid = $("#rolesGrid").data("kendoGrid").dataSource.filter(
            []
         );
        grid.refresh();
    });
});
*/
