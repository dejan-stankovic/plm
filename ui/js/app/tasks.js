/*************************************************************************
		File name: tasks.js
		Author: Yuvaraj, Manav, Rachit & Christian
		Created date: 11/3/2013
		Purpose: client functionality associated with task page
**************************************************************************/
/*global variables */

// variable to persist the selected task id
var selectedTaskId = 0;
// data source instance to persist the list of statuses returned from service
var statusData = [];
// data source instance to persist the list of users returned from service
var usersData = [];
// data source instance to persist the list of user stories from service
var taskData = [];
// token instance to persist the session credential
var token = null;
//project id extracted from query string
var projectId = 1;
//project name extracted from query string
var projectName = 'Employee Portal';
var userStoryId = 0;
var userStoryName = "";

/************************************************************
Function name: onready
Author: Yuvaraj, Manav, Rachit
Created date: 11/3/2013
Purpose: document on ready function to load the page
************************************************************/
$(document).ready(function () {
    load();
});

/* load methods */

/************************************************************
Function name: load 
Author: Yuvaraj, Manav, Rachit
Created date: 11/2/2013
Purpose: global on load to load all the contents
************************************************************/
function load() {
    loadData();
    kendofy();
    loadTaskGrid();
    registerEvents();
}
/************************************************************
Function name: loadData
Author: Yuvaraj, Manav, Rachit
Created date: 11/3/2013
Purpose: load fetches the data from services and loads page
************************************************************/
function loadData() {
	token=getToken();
	projectId = getCurProject();
    userStoryId = isNull(readQueryString("uid")) ? 0 : readQueryString("uid");
    userStoryName = isNull(readQueryString("uname")) ? '' : readQueryString("uname");
    //loadDummyData();
    loadDataFromServices();
}
/************************************************************
Function name: loadDummyData
Author: Yuvaraj, Manav, Rachit
Created date: 11/2/2013
Purpose:load dummy data
************************************************************/
function loadDummyData() {
    statusData = [{ "id": 1, "name": "Initial" }, { "id": 2, "name": "Pending" }, { "id": 3, "name": "InProgress" }, { "id": 4, "name": "Complete" }];
    usersData = ["Alan", "Christian", "Manav", "Rachit", "Tandy", "Yuvraj"];
    taskData = [
           { "id": 1, "name": "Able to login", "description": "test", "assigned": "Manav" },
           { "id": 2, "name": "Able to enter time", "description": "test", "assigned": "Rachit" },
           { "id": 3, "name": "Able to view employee details", "description": "", "assigned": "Christian" },
           { "id": 4, "name": "Able to save employee information", "description": "", "assigned": "Yuvraj" },
           { "id": 5, "name": "Able to generate employee report", "description": "", "assigned": "Yuvraj" },
    ];
}
/************************************************************
Function name: loadDataFromServices
Author: Yuvaraj, Manav, Rachit
Created date: 11/3/2013
Purpose: invokes services to load the data
************************************************************/
function loadDataFromServices() {
    getStatuses();
    getUsersInProject();
    getTasks(userStoryId);
}
/************************************************************
Function name: kendofy 
Author: Yuvaraj, Manav, Rachit
Created date: 11/3/2013
Purpose: kendofy all the controls on the page
************************************************************/
function kendofy() {
    $("#userStoryLbl").text(userStoryName);
    $("#statusddl").kendoComboBox({ placeholder: "Select..." });
    $("#editStatusDdl").kendoComboBox({ placeholder: "Select..." });
    /*$("#assignddl").kendoAutoComplete({
        dataSource: usersData,
        		dataTextName: "name",
                dataTextValue: "id",
        filter: "startswith",
        placeholder: "select user...",
        separator: ", "
    });*/
    
    $("#createInfo").hide();
    $("#errorInfo").hide();
    $("#progressInfo").hide();
}
/************************************************************
Function name: loadTaskGrid
Author: Yuvaraj, Manav, Rachit
Created date: 11/3/2013
Purpose: load user story grid
************************************************************/
function loadTaskGrid() {
    $("#grid").kendoGrid({
        dataSource: {
            data: taskData,
            pageSize: 5
        },
        columns: [{
            field: "name",
            Title: "Name",
            width: "30%",
        },
        {
            field: "description",
            Title: "Description",
            width: "50%"
        },
        {
            field: "status",
            Title: "Status",
            width: "10%"
        }, {
            field: "assigned",
            Title: "Assigned",
            width: "10%"
        }
        ],
        sortable: {
            mode: "multiple",
            allowUnsort: true
        },
        reorderable: true,
        resizable: true,
        pageable: true,
        scrollable: false,
        selectable: "row",
        change: rowSelected,
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
    function rowSelected(e) {
        var item = this.dataItem(this.select()[0]);
        selectedTaskId = item.id;
        $("#titleTxt").val(item.name);
        $("#assignddl").val(item.assigned);
        $("#editStatusDdl").val(item.status);
        $("#descriptionTxt").val(item.description);
    }
}

/************************************************************
Function name: registerEvents 
Author: Yuvaraj, Manav, Rachit
Created date: 11/3/2013
Purpose: register all control events
************************************************************/
function registerEvents() {
    $("#btnFilter").click(function () {
        var userstory = $("#userstoryddl").data("kendoComboBox").text();
        var status = $("#statusddl").data("kendoComboBox").text();
        var filter = [];
        filter.push({ field: "Status", operator: "eq", value: status });
        $("#grid").data("kendoGrid").dataSource.filter(filter);
        $("#grid").data("kendoGrid").refresh();
    });
    $("#btnClearFilter").click(function () {
        $("#grid").data("kendoGrid").dataSource.filter([]);
        $("#grid").data("kendoGrid").refresh();
    });
    $("#btnSave").click(function () {
        $("#createInfo").hide();
        $("#progressInfo").hide();
        var errorMessage = validate();
        if (errorMessage != "") {
            $("#errorInfo").text(errorMessage);
            $("#errorInfo").slideDown("slow");
            return;
        }
        $("#errorInfo").hide();
        $("#progressInfo").slideDown();
        var item = update();
        if (selectedTaskId == -1) {
            createTask(item);
        }
        else {
            updateTask(item,selectedTaskId);
        }
        selectedTaskId = 0;
    });
    $("#cancelSave").click(function () {
        selectedTaskId = 0;
        $("#titleTxt").val("");
        $("#assignddl").val("");
        $("#editStatusDdl").val("");
        $("#descriptionTxt").val("");
    });
    $("#btnTask").click(function () {
        selectedTaskId = -1;
        $("#titleTxt").val("");
        $("#assignddl").val("");
        $("#editStatusDdl").val("Initial");
        $("#descriptionTxt").val("");

        $("#errorInfo").hide();
        $("#progressInfo").hide();
        $("#createInfo").slideDown("slow");
    });
}

/*private methods*/
/************************************************************
Function name: isNull 
Author: Manav
Created date: 11/3/2013
Purpose: helper method to check if value is null or not
************************************************************/
function isNull(value) {
    return (value == null || value == '');
}
/************************************************************
Function name: reqdQueryString 
Author: Manav
Created date: 11/3/2013
Purpose: read query string and fetch the value for the key passed
************************************************************/
function readQueryString(param) {
    hu = window.location.search.substring(1);
    gy = hu.split("&");
    for (i = 0; i < gy.length; i++) {
        ft = gy[i].split("=");
        if (ft[0] == param) {
            return ft[1];
        }
    }
}
/************************************************************
Function name: validate 
Author: Manav
Created date: 11/3/2013
Purpose: validate the ui before save
************************************************************/
function validate() {
    if (selectedTaskId == 0) {
        return ("Please select a task to save.");
    }
    if ($("#titleTxt").val() == "") {
        return ("* Please enter Title as it is mandatory");
    }
    if ($("#assignddl").val() == "") {
        return ("* Please enter Owner as it is mandatory");
    }
    return "";
}
/************************************************************
Function name: update 
Author: Manav
Created date: 11/3/2013
Purpose: update the model before save
************************************************************/
function update() {
    var dataSource = $("#grid").data("kendoGrid").dataSource.data();
    var item = null;
    if (selectedTaskId == -1) {
        item = {
            id: getNewId(dataSource),
            name: $("#titleTxt").val(),
            assigned: $("#assignddl").val(),
            status: $("#editStatusDdl").data("kendoComboBox").text(),
            description: $("#descriptionTxt").val()
        };
        dataSource.push(item);
        $("#grid").data("kendoGrid").refresh();
    }
    else {
        $.each(dataSource, function (index, ds1) {
            if (ds1.id == selectedTaskId) {
                item = ds1;
                item.name = $("#titleTxt").val(),
                item.assigned = $("#assignddl").val(),
                item.status = $("#editStatusDdl").data("kendoComboBox").text(),
                item.description = $("#descriptionTxt").val()
            }
        });
    } return (item);
}
/************************************************************
Function name: getNewId 
Author: Manav
Created date: 11/3/2013
Purpose: get the new User Storyid/ comment id
************************************************************/
function getNewId(dataSource) {
    var maxId = 0;
    $.each(dataSource, function (index, item) {
        if (item.Id > maxId) {
            maxId = item.Id;
        }
    });
    return (++maxId);
}
/************************************************************
Function name: loadStatusDropDown 
Author: Manav
Created date: 11/3/2013
Purpose: load status drop down
************************************************************/
function loadStatusDropDown(id, dataSource) {
    statusData = dataSource;
    $("#" + id).kendoComboBox({
        dataTextField: "name",
        dataValueField: "id",
        dataSource: dataSource
    });
}
function loadUsersData(id, dataSource) {
    usersData = dataSource;
    $("#" + id).kendoComboBox({
        dataTextField: "name",
        dataValueField: "id",
        dataSource: dataSource
    });
}
/************************************************************
Function name: loadTasks 
Author: Yuvaraj, Manav, Rachit
Created date: 11/3/2013
Purpose: load status drop down
************************************************************/
function loadTasks(tasks) {
    /* {"userStories":[{"id":1,"name":"NewRandom 0.028741610702127218","description":"Some description",
            "points":1,"owner":{"id":2,"name":"anotheruser"},"status":{"id":2,"name":"Pending"}},
             */
             taskData=[];
    $.each(tasks, function (index, task) {
        taskData.push({
            id: task.id,
            name: task.name,
            description: task.description,
            assigned: task.assigned.name,
            status: task.status.name,
        });
    });
    $("#grid").data("kendoGrid").dataSource.data(taskData);
    $("#grid").data("kendoGrid").refresh();
}

// service calls
/************************************************************
Function name: getUsersInProject
Author: Christian Heckendorf
Created date: 10/14/2013
Purpose: Gets all users in the project
************************************************************/
function getUsersInProject(){
	var pid,tok;

	tok = token;
	pid = projectId;

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
			loadUsersData("assignddl", data.users);
		},
		error: function(data){
			alert("error");
		}
	});
}

/************************************************************
Function name: updatesUserStory
Author: Christian Heckendorf
Created date: 10/26/2013
Purpose: Updates a user story
************************************************************/
function updateTask(item,tid) {
	var cb = $("#editStatusDdl").data("kendoComboBox");
	var sid = cb.dataItem().id;

	cb = $("#assignddl").data("kendoAutoComplete");
    var uid = cb.value();

    $.ajax({
        type: 'POST',
        url: '/plm/rest/task/update/t/'+ selectedTaskId,
        contentType: 'application/json; charset=UTF-8',
        accepts: {
            text: 'application/json'
        },
        dataType: 'json',
        data: JSON.stringify({
            token: {
                token: token
            },
            task: {
                name: item.name,
                description: item.description,
                status: {
                	id: sid
                    /*id: function(){
                        $.each(statusData, function (index, status) {
                            if (status.name == item.Status) {
                                return (status.id);
                            }
                        });
                        return (1);
                    }*/
                },
                assigned: {
                    // TODO : Should be getting the list of users along with id, name
                    id: uid
                }
            }
        }),
        success: function (data) {
            /* code: id or -1, message: success/error message */
            /* {"code":4,"message":"Success"} */
            alert(data.code + " " + data.message);
        },
        error: function (data) {
            alert("error");
        }
    });
}


/************************************************************
Function name: createTask
Author: Christian Heckendorf, Yuvaraj, Rachit
Created date: 10/26/2013
Purpose: Creates a new task
************************************************************/
function createTask(item) {
	var cb = $("#editStatusDdl").data("kendoComboBox");
	var sid = cb.dataItem().id;

	cb = $("#assignddl").data("kendoAutoComplete");
    var uid = cb.value();

    $.ajax({
        type: 'POST',
        url: '/plm/rest/task/create/us/'+ userStoryId,
        contentType: 'application/json; charset=UTF-8',
        accepts: {
            text: 'application/json'
        },
        dataType: 'json',
        data: JSON.stringify({
            token: {
                token: token
            },
            task: {
                name: item.name,
                description: item.description,
                status: {
                	id: sid
                    /*id: function () {
                        $.each(statusData, function (index, status) {
                            if (status.name == item.Status) {
                                return (status.id);
                            }
                        });
                        return (1);
                    }*/
                },
                assigned: {
                    // TODO : Should be getting the list of users along with id, name
                    id: uid
                }
            }
        }),
        success: function (data) {
            /* code: new id or -1, message: success/error message */
            /* {"code":4,"message":"Success"} */
            //updateTask();
            alert(data.code + " " + data.message);
        },
        error: function (data) {
            alert("error");
        }
    });
}


/************************************************************
Function name: getTasks
Author: Christian Heckendorf
Created date: 10/26/2013
Purpose: Displays a list of user stories under a release
************************************************************/
function getTasks(usid) {
   
    $.ajax({
        type: 'POST', //'GET',
        url:  '/plm/rest/task/us/'+userStoryId,          
        contentType: 'application/json; charset=UTF-8',
        accepts: {
            text: 'application/json'
        },
        dataType: 'json',
        data: JSON.stringify({
            token: token
        }),
        success: function (data) {
            loadTasks(data.tasks);
        },
        error: function (data) {
            alert("error");
        }
    });
}

function getStatuses() {
    $.ajax({
        type: 'POST',
        url: '/plm/rest/status', // '/plm/rest/status', //,
        contentType: 'application/json; charset=UTF-8',
        accepts: {
            text: 'application/json'
        },
        dataType: 'json',
        data: JSON.stringify({
            token: token
        }),
        success: function (data) {
            loadStatusDropDown("statusddl", data.statuses);
            loadStatusDropDown("editStatusDdl", data.statuses);
        },
        error: function (data) {
            alert("error");
        }
    });
}
