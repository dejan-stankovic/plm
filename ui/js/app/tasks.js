/*************************************************************************
		File name: tasks.js
		Author: Yuvaraj & Rachit
		Created date: 11/3/2013
		Purpose: client functionality associated with task page
**************************************************************************/
/*global variables */

// variable to persist the selected user story id
var selectedTaskId = 0;
// data source instance to persist the list of statuses returned from service
var statusData = [];
// data source instance to persist the list of users returned from service
var usersData = [];
// data source instance to persist the list of user stories from service
var TaskData = [];
// data souce instance to persist the list of comments returned from service
var commentsData = [];
// token instance to persist the session credential
var token = null;
//project id extracted from query string
var projectId = 1;
//project name extracted from query string
var projectName = 'Employee Portal';

/************************************************************
Function name: onready
Author: Rachit
Created date: 11/3/2013
Purpose: document on ready function to load the page
************************************************************/
$(document).ready(function () {
    load();
});

/* load methods */

/************************************************************
Function name: load 
Author: Rachit
Created date: 11/2/2013
Purpose: global on load to load all the contents
************************************************************/
function load() {
    loadData();
    kendofy();
    loadUserStoryGrid();
    loadCommentsGrid();
    registerEvents();
}
/************************************************************
Function name: loadData
Author: Rachit
Created date: 11/3/2013
Purpose: load fetches the data from services and loads page
************************************************************/
function loadData() {
    projectId = isNull(readQueryString("pid")) ? 1 : readQueryString("pid");
    projectName = isNull(readQueryString("pname")) ? 'Employee Portal' : readQueryString("pname");
    //loadDummyData();
    loadDataFromServices();
}
/************************************************************
Function name: loadDummyData
Author: Yuvaraj & Rachit
Created date: 11/2/2013
Purpose:load dummy data
************************************************************/
function loadDummyData() {
    statusData = [{ "id": 1, "name": "Initial" }, { "id": 2, "name": "Pending" }, { "id": 3, "name": "InProgress" }, { "id": 4, "name": "Complete" }];
    usersData = ["Alan", "Christian", "Manav", "Rachit", "Tandy", "Yuvraj"];
    userStoryData = [
           { "Id": 1, "Title": "Able to login", "Description": "test", "StoryPoint": "5", "Owner": "Manav", "Status": "Initial", "Priority": "High" },
           { "Id": 2, "Title": "Able to enter time", "Description": "test", "StoryPoint": "4", "Owner": "Rachit", "Status": "Approved", "Priority": "Medium" },
           { "Id": 3, "Title": "Able to view employee details", "Description": "", "StoryPoint": "2", "Owner": "Christian", "Status": "PendingApproval", "Priority": "Low" },
           { "Id": 4, "Title": "Able to save employee information", "Description": "", "StoryPoint": "2", "Owner": "Yuvraj", "Status": "InProgress", "Priority": "Low" },
           { "Id": 5, "Title": "Able to generate employee report", "Description": "", "StoryPoint": "1", "Owner": "Yuvraj", "Status": "Complete", "Priority": "Medium" },
    ];
    commentsData = [
        { UserStoryId : 1, "Id": 1, "Comments": "shall we use third party tool for login", "Creator": "Manav" },
        { UserStoryId: 1, "Id": 2, "Comments": "yes, but the validation should happen on services", "Creator": "Christian" },
        { UserStoryId: 1, "Id": 3, "Comments": "attaching schema for the login table<br/>http://www.google.com", "Creator": "Vipul" },
    ];
}
/************************************************************
Function name: loadDataFromServices
Author: Yuvaraj & Rachit
Created date: 11/3/2013
Purpose: invokes services to load the data
************************************************************/
function loadDataFromServices() {
    loginUser();
}
/************************************************************
Function name: kendofy 
Author: Manav
Created date: 11/3/2013
Purpose: kendofy all the controls on the page
************************************************************/
function kendofy() {
    $("#projectLbl").text(projectName);
    $("#releaseddl").kendoComboBox({ placeholder: "Select..." });
    $("#statusddl").kendoComboBox({ placeholder: "Select..." });
    $("#editStatusDdl").kendoComboBox({ placeholder: "Select..." });
    $("#editPriorityDdl").kendoComboBox();
    $("#ownerTxt").kendoAutoComplete({
        dataSource: usersData,
        filter: "startswith",
        placeholder: "select user...",
        separator: ", "
    });
    $("#storyPointTxt").kendoNumericTextBox({
        min: 1,
        max: 5,
        step: 1
    });
    $("#createInfo").hide();
    $("#errorInfo").hide();
    $("#progressInfo").hide();
}
/************************************************************
Function name: loadUserStoryGrid
Author: Yuvaraj & Rachit
Created date: 11/3/2013
Purpose: load user story grid
************************************************************/
function loadUserStoryGrid() {
    $("#grid").kendoGrid({
        dataSource: {
            data: userStoryData,
            pageSize: 5
        },
        columns: [{
            field: "Title",
            Title: "Title",
            template: "<a href=\\#>#=Title#</a>",
            width: "60%",
        }, {
            field: "StoryPoint",
            Title: "StoryPoint",
            width: "10%",
        }, {
            field: "Owner",
            Title: "Owner",
            width: "10%"
        },
        {
            field: "Status",
            Title: "Status",
            width: "10%"
        }, {
            field: "Priority",
            Title: "Priority",
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
        selectedUserStoryId = item.Id;
        $("#titleTxt").val(item.Title);
        $("#storyPointTxt").data("kendoNumericTextBox").value(parseInt(item.StoryPoint));
        $("#ownerTxt").val(item.Owner);
        $("#editStatusDdl").val(item.Status);
        $("#editPriorityDdl").val(item.Priority);
        $("#descriptionTxt").val(item.Description);

        $("#commentsGrid").data("kendoGrid").dataSource.filter({ field: "UserStoryId", operator: "eq", value: selectedUserStoryId });
        $("#commentsGrid").data("kendoGrid").refresh();
    }
}
/************************************************************
Function name: loadCommentsGrid 
Author: Yuvaraj & Rachit
Created date: 11/3/2013
Purpose: load comments grid
************************************************************/
function loadCommentsGrid() {
    $("#commentsGrid").kendoGrid({
        dataSource: {
            data: commentsData,
            pageSize: 5
        },
        columns: [{
            field: "Delete",
            Title: "Delete",
            width: "3%",
            template: "<span class='icon_16 delete' onclick='deleteComment(#=Id#);' style='text-align:right' title='Delete Comment'></span>",
            headerAttributes: {
                style: "display:none"
            }
        }, {
            field: "Comments",
            Title: "Comments",
            width: "70%",
            template: "<span>#=Comments#</span>",
            headerAttributes: {
                style: "display:none"
            }
        }, {
            field: "Creator",
            Title: "Created By",
            width: "25%",
            headerAttributes: {
                style: "display:none"
            }
        },
        ],
    });
    
}

/* event handlers */

/************************************************************
Function name: registerEvents 
Author: Yuvaraj & Rachit
Created date: 11/3/2013
Purpose: register all control events
************************************************************/
function registerEvents() {
    $("#btnFilter").click(function () {
        var release = $("#releaseddl").data("kendoComboBox").text();
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
        if (selectedUserStoryId == -1) {
            createUserStory(item);
        }
        else {
            updateUserStory(item);
        }
        selectedUserStoryId = 0;
    });
    $("#cancelSave").click(function () {
        selectedUserStoryId = 0;
        $("#titleTxt").val("");
        $("#storyPointTxt").data("kendoNumericTextBox").value("");
        $("#ownerTxt").val("");
        $("#editStatusDdl").val("");
        $("#editPriorityDdl").val("");
        $("#descriptionTxt").val("");
    });
    $("#btnCreateUserStory").click(function () {
        selectedUserStoryId = -1;
        $("#titleTxt").val("");
        $("#storyPointTxt").data("kendoNumericTextBox").value("");
        $("#ownerTxt").val("");
        $("#editStatusDdl").val("Initial");
        $("#editPriorityDdl").val("");
        $("#descriptionTxt").val("");
        $("#txtNewComment").val("");

        $("#errorInfo").hide();
        $("#progressInfo").hide();
        $("#createInfo").slideDown("slow");

        $("#commentsGrid").data("kendoGrid").dataSource.data([]);
        $("#commentsGrid").data("kendoGrid").refresh();
    });
    $("#btnAddComment").click(function () {
        var dataSource = $("#commentsGrid").data("kendoGrid").dataSource.data();
        var item = { UserStoryId : selectedUserStoryId, Id: getNewId(dataSource), Comments: $("#txtNewComment").val(), Creator: "currentuser" };
        dataSource.push(item);
        $("#commentsGrid").data("kendoGrid").refresh();
    });
}
/************************************************************
Function name: deleteComment 
Author: Yuvaraj & Rachit
Created date: 11/3/2013
Purpose: delete comment event handler
************************************************************/
function deleteComment(id) {
    var comments = $("#commentsGrid").data("kendoGrid").dataSource.data();
    $.each(comments, function (index, commentItem) {
        if (commentItem != null && commentItem.Id == id) {
            comments.splice(index, 1);
            return;
        }
    });
    $("#commentsGrid").data("kendoGrid").refresh();
}
/*private methods*/
/************************************************************
Function name: isNull 
Author: Yuvaraj & Rachit
Created date: 11/3/2013
Purpose: helper method to check if value is null or not
************************************************************/
function isNull(value) {
    return (value == null || value == '');
}
/************************************************************
Function name: reqdQueryString 
Author: Yuvaraj & Rachit
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
Author: Yuvaraj & Rachit
Created date: 11/3/2013
Purpose: validate the ui before save
************************************************************/
function validate() {
    if (selectedUserStoryId == 0) {
        return ("Please select a user story to save.");
    }
    if ($("#titleTxt").val() == "") {
        return ("* Please enter Title as it is mandatory");
    }
    if ($("#storyPointTxt").val() == "") {
        return ("* Please enter StoryPoint as it is mandatory");
    }
    if ($("#ownerTxt").val() == "") {
        return ("* Please enter Owner as it is mandatory");
    }
    return "";
}
/************************************************************
Function name: update 
Author: Yuvaraj & Rachit
Created date: 11/3/2013
Purpose: update the model before save
************************************************************/
function update() {
    var dataSource = $("#grid").data("kendoGrid").dataSource.data();
    var item = null;
    if (selectedUserStoryId == -1) {
        item = {
            Id: getNewId(dataSource),
            Title: $("#titleTxt").val(),
            StoryPoint: $("#storyPointTxt").val(),
            Owner: $("#ownerTxt").val(),
            Status: $("#editStatusDdl").data("kendoComboBox").text(),
            Priority: $("#editPriorityDdl").data("kendoComboBox").text(),
            Description: $("#descriptionTxt").val()
        };
        dataSource.push(item);
        $("#grid").data("kendoGrid").refresh();
    }
    else {
        $.each(dataSource, function (index, ds1) {
            if (ds1.Id == selectedUserStoryId) {
                item = ds1;
                item.Title = $("#titleTxt").val(),
                item.StoryPoint = $("#storyPointTxt").val(),
                item.Owner = $("#ownerTxt").val(),
                item.Status = $("#editStatusDdl").data("kendoComboBox").text(),
                item.Priority = $("#editPriorityDdl").data("kendoComboBox").text(),
                item.Description = $("#descriptionTxt").val()
            }
        });
    } return (item);
}
/************************************************************
Function name: getNewId 
Author: Yuvaraj & Rachit
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
Author: Yuvaraj & Rachit
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
/************************************************************
Function name: loaduserStories 
Author: Yuvaraj & Rachit
Created date: 11/3/2013
Purpose: load status drop down
************************************************************/
function loadUserStories(userStories) {
    /* {"userStories":[{"id":1,"name":"NewRandom 0.028741610702127218","description":"Some description",
            "points":1,"owner":{"id":2,"name":"anotheruser"},"status":{"id":2,"name":"Pending"}},
             */
    $.each(userStories, function (index, userStory) {
        userStoryData.push({
            Id: userStory.id,
            Title: userStory.name,
            Description: userStory.description,
            StoryPoint: userStory.points,
            Owner: userStory.owner.name,
            Status: userStory.status.name,
            Priority : "Medium"
        });
    });
    $("#grid").data("kendoGrid").dataSource.data(userStoryData);
    $("#grid").data("kendoGrid").refresh();
}
/************************************************************
Function name: loadComments 
Author: Yuvaraj & Rachit
Created date: 11/3/2013
Purpose: load comments grid
************************************************************/
function loadComments(comments) {
    $("#commentsGrid").data("kendoGrid").dataSource.data(comments);
}
/************************************************************
Function name: loadReleases
Author: Yuvaraj & Rachit
Created date: 11/3/2013
Purpose: load releases drop down
************************************************************/
function loadReleases(releases) {
    $("#releaseddl").kendoComboBox({
        dataSource: releases
    });
}
// service calls
/************************************************************
Function name: updatesUserStory
Author: Christian Heckendorf
Created date: 10/26/2013
Purpose: Updates a user story
************************************************************/
function updateUserStory(item) {
    $.ajax({
        type: 'POST',
        url: '/plm/rest/userstory/update/u/1',
        contentType: 'application/json; charset=UTF-8',
        accepts: {
            text: 'application/json'
        },
        dataType: 'json',
        data: JSON.stringify({
            token: {
                token: token
            },
            tasks: {
                name: item.Title,
                description: item.Description,
                points: item.StoryPoint,
                status: {
                    id: function(){
                        $.each(statusData, function (index, status) {
                            if (status.name == item.Status) {
                                return (status.id);
                            }
                        });
                        return (1);
                    }
                },
                owner: {
                    // TODO : Should be getting the list of users along with id, name
                    id: 2
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
    $.ajax({
        type: 'POST',
        url: '/plm/rest/task/create/r/1',
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
                name: item.Title,
                description: item.Description,
                points: item.StoryPoint,
                status: {
                    id: function () {
                        $.each(statusData, function (index, status) {
                            if (status.name == item.Status) {
                                return (status.id);
                            }
                        });
                        return (1);
                    }
                },
                owner: {
                    // TODO : Should be getting the list of users along with id, name
                    id: 2
                }
            }
        }),
        success: function (data) {
            /* code: new id or -1, message: success/error message */
            /* {"code":4,"message":"Success"} */
            updateUserStory();
        },
        error: function (data) {
            alert("error");
        }
    });
}


/************************************************************
Function name: getUserStories
Author: Christian Heckendorf
Created date: 10/26/2013
Purpose: Displays a list of user stories under a release
************************************************************/
function getUserStories() {
    $.ajax({
        type: 'POST', //'GET',
        url: '/plm/rest/userstory/r/1', //'../js/data/serviceResponse.js'
        contentType: 'application/json; charset=UTF-8',
        accepts: {
            text: 'application/json'
        },
        dataType: 'json',
        data: JSON.stringify({
            token: token
        }),
        success: function (data) {
            loadUserStories(data.userStories);
        },
        error: function (data) {
            alert("error");
        }
    });
}
function getStatuses() {
    $.ajax({
        type: 'GET',
        url: '/plm/rest/status', //../js/data/serviceResponse.js', //,
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
function getComments() {
    $.ajax({
        type: 'GET',
        url: '../js/data/serviceResponse.js',
        contentType: 'application/json; charset=UTF-8',
        accepts: {
            text: 'application/json'
        },
        dataType: 'json',
        data: JSON.stringify({
            token: token
        }),
        success: function (data) {
            loadComments(data.comments);
        },
        error: function (data) {
            alert("error");
        }
    });
}
/************************************************************
Function name: loginUser
Author: Christian Heckendorf
Created date: 09/30/2013
Purpose: Logs in a user
************************************************************/
function loginUser() {
    $.ajax({
        type: 'GET',
        url: '/plm/rest/login', //'../js/data/serviceResponse.js'
        contentType: 'application/json; charset=UTF-8',
        accepts: {
            text: 'application/json'
        },
        dataType: 'json',
        data: JSON.stringify({
            name: 'auser',
            password: 'apassword'
        }),
        success: function (data) {
            token = data.message;
            if (data.code == 0) {
                getReleases();
                getStatuses();
                getUserStories();
                getComments();
            }
        },
        error: function (data) {
            alert("error");
        }
    });
}
/************************************************************
Function name: getReleases
Author: Christian Heckendorf
Created date: 10/13/2013
Purpose: Updates a list of releases in a project
************************************************************/
function getReleases(pid) {
    $.ajax({
        type: 'GET',
        url: '/plm/rest/projectmanage/releases/p/' + projectId, // '../js/data/serviceResponse.js'
        contentType: 'application/json; charset=UTF-8',
        accepts: {
            text: 'application/json'
        },
        dataType: 'json',
        data: JSON.stringify({
            token: token
        }),
        success: function (data) {
            loadReleases(data.releases);
        },
        error: function (data) {
            alert("error");
        }
    });
}