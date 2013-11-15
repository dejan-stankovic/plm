/*************************************************************************
		File name: common.js
		Author: Manav
		Created date: 09/30/2013
		Purpose: common routines
**************************************************************************/

/************************************************************
Function name: setToken
Author: Christian Heckendorf
Created date: 10/13/2013
Purpose: Stores the sesssion token
************************************************************/
function setToken(tok){
	document.cookie = "token="+tok;
}

/************************************************************
Function name: setCurProject
Author: Christian Heckendorf
Created date: 10/14/2013
Purpose: Sets the current project
************************************************************/
function setCurProject(pid){
	if(pid){
		document.cookie = "project="+pid;
		getPermissions(pid);
	}
}

/************************************************************
Function name: getCookieValue
Author: Christian Heckendorf
Created date: 10/14/2013
Purpose: Returns the value of a cookie
************************************************************/
function getCookieValue(c_name){
	var c_value, c_start;

	c_value = document.cookie;
	c_start = c_value.indexOf(" " + c_name + "=");

	if (c_start == -1) {
		c_start = c_value.indexOf(c_name + "=");
	}
	if (c_start == -1) {
		c_value = "";
	}
	else {
		c_start = c_value.indexOf("=", c_start) + 1;
		var c_end = c_value.indexOf(";", c_start);
		if (c_end == -1) {
			c_end = c_value.length;
		}
		c_value = unescape(c_value.substring(c_start,c_end));
	}

	return c_value;
}

/************************************************************
Function name: getToken
Author: Christian Heckendorf
Created date: 10/13/2013
Purpose: Returns the stored session token
************************************************************/
function getToken(){
	return getCookieValue("token");
}

/************************************************************
Function name: getCurProject
Author: Christian Heckendorf
Created date: 10/14/2013
Purpose: Returns the active project id
************************************************************/
function getCurProject(){
	/* TODO: Get/Set from the url? */
	return getCookieValue("project");
}

/************************************************************
Function name: getCurProjects
Author: Christian Heckendorf
Created date: 10/14/2013
Purpose: Ajax to set the list of projects for a user
************************************************************/
function getCurProjects(){
	var tok;

	tok = getToken();

	$.ajax({
		type: 'POST',
		url: '/plm/rest/dashboard/projects',
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			token: tok
		}),
		success: function(data){
			var pid;
			var combobox;
			
			combobox = $("#cur-project").data("kendoComboBox");

			emptyComboBox(combobox);

			for(x in data.projects){
				combobox.dataSource.add(data.projects[x]);
			}

			pid = getCurProject();
			if(pid.length>0){
				combobox.select(function(dataItem){
						return dataItem.id==pid;
					}
				);
			}
		},
		error: function(data){
			alert("error");
		}
	});
}

function emptyComboBox(comboBox)
{
	var totalData = comboBox.dataSource.total();
	for(i=0;i<totalData;i++)
	{
		comboBox.dataSource.remove(comboBox.dataSource.at(0));
	}
}

function updateMenuOptions(perm){
	var menu;
	var items;
	var menuitems;
	
	menu = $("#panelbar-images").data("kendoPanelBar");
	/* Some default permissions */
	menuitems=[
		{
			text: "Views", imageUrl: "../css/Images/edit_enable.png",
			items: [
				{ text: "Dashboard", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/star.png" },
				{ text: "Notifications", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/photo.png" },
				{ text: "Pipeline", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/video.png" },
			]
		},
		{
			text: "Search", imageUrl: "../css/Images/addtask_enable.png",
			items: [
				{ text: "Search By Type", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/photo.png" },
			]
		},
	];

	if(perm.indexOf("EditRelease")>=0){
		menuitems.push({
			text: "Release Management", imageUrl: "../css/Images/download_enable.png",
			items: [
				{ text: "Manage Release", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/star.png" },
			]
		});
	}

	items = [];
	if(perm.indexOf("EditProject")>=0){
		items.push({ text: "Manage Projects", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/photo.png" });
	}
	if(perm.indexOf("CreateUserStory")>=0){
		items.push({ text: "Manage User Stories", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/photo.png" });
	}
	if(perm.indexOf("InviteUser")>=0){
		items.push({ text: "Manage Users", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/video.png" });
	}
	if(perm.indexOf("SetRole")>=0){
		items.push({ text: "Manage Roles", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/photo.png" });
	}

	if(items.length>0){
		menuitems.push({
			text: "Configuration", imageUrl: "../css/Images/gear-icon.png",
			items: items
		});
	}

	menuitems.push({
			text: "Reporting", imageUrl: "../css/Images/inactive_16.png",
			items: [
				{ text: "Resource Allocation", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/star.png" },
			]
		});

	menu.append(menuitems);
}

function getPermissions(pid){
	var tok;

	tok = getToken();

	$("#panelbar-images").empty();

	$.ajax({
		type: 'POST',
		url: '/plm/rest/permission/p/'+pid,
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			token: tok
		}),
		success: function(data){
			updateMenuOptions(data.permissions);
		},
		error: function(data){
			alert("error");
		}
	});
}

/**
 * to remove token after user hit signout link
 * author : tandhy
 * date : 11.14.13
 */
function userSignOut()
{
	setToken("");
	window.location = "login.html";
}

/************************************************************
		Function name: onready
		Author: Manav
		Created date: 09/30/2013
		Purpose: ready function invoked when any page is rendered
**************************************************************/
$(document).ready(function () {
	var pid = getCurProject();

    $("#vertical").kendoSplitter({
        orientation: "vertical",
        panes: [
            { collapsible: false },
            { collapsible: false, size: "100px" },
            { collapsible: false, resizable: false, size: "100px" }
        ]
    });

    $("#horizontal").kendoSplitter({
        panes: [
            { collapsible: true, size: "320px" },
            { collapsible: false },
            { collapsible: true, size: "320px" }
        ]
    });

    if($("#cur-project").length > 0){
        $("#cur-project").kendoComboBox({
            dataSource: [],
            dataTextField: "name",
            dataValueField: "id",
            select: function(e){
                if (e.item == null) return;
                var DataItem = this.dataItem(e.item.index());
                setCurProject(DataItem.id);
            }
        })
        getCurProjects();
    }

    $("#panelbar-images").kendoPanelBar({
        select : onitemSelected,
        dataSource: []
    });

    if($("#panelbar-images").length>0){
        if(pid.length>0){
            getPermissions(pid);
        }
        else{
            updateMenuOptions([]); // No project selected by default
        }
    }

    function onitemSelected(e) {
        var selectedItem = $(e.item).find("> .k-link").text();
        switch (selectedItem) {
            case ('Dashboard'):
                window.location = "dashboard.html";
                break;
            case ('Notifications'):
                window.location = "notifications.html";
                break;
            case ('Manage Users'):
                window.location = "manageroles.html";
                break;
            case ('Manage User Stories'):
                window.location = "userstory.html";
                break;
            case ('Manage Release'):
                window.location = "createIter.html";
                break;
            case ('Manage Projects'):
                window.location = "createProject.html";
                break;
        }
    }
	
});
