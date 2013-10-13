/*************************************************************************
		File name: dashboard.js
		Author: Manav
		Created date: 09/30/2013
		Purpose: client functionality associated with dashboard page
**************************************************************************/

/************************************************************
Function name: getTasks
Author: Christian Heckendorf
Created date: 10/13/2013
Purpose: Updates the grid with a user's tasks
************************************************************/
function getTasks(){
	var tok;
	tok = getToken();
	$.ajax({
		type: 'POST',
		url: '/plm/rest/dashboard/tasks',
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			token: tok
		}),
		success: function(data){
			var grid = $("#grid").data("kendoGrid");
			for(x in data.tasks){
				data.tasks[x].Type="Task";
				grid.dataSource.add(data.tasks[x]);
			}
		},
		error: function(data){
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
function getReleases(pid){
	var tok;

	tok = getToken();
	//pid = $("select#projectddl option:selected").val();

	$("select#releaseddl").html("");

	$.ajax({
		type: 'POST',
		url: '/plm/rest/projectmanage/releases/p/'+pid,
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			token: tok
		}),
		success: function(data){
			var combobox, results;
			combobox = $("select#releaseddl").data("kendoComboBox");

			for(x in data.releases){
				combobox.dataSource.add(data.releases[x]);
			}
		},
		error: function(data){
			alert("error");
		}
	});
}

/************************************************************
Function name: getProjects
Author: Christian Heckendorf
Created date: 10/13/2013
Purpose: Updates the list of projects a user in involved in
************************************************************/
function getProjects(){
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
			var combobox, results;
			combobox = $("select#projectddl").data("kendoComboBox");
			results = 0;

			for(x in data.projects){
				results = 1;
				combobox.dataSource.add(data.projects[x]);
			}
			
			if(results>0){
				getReleases(combobox.dataItem().id);
			}
		},
		error: function(data){
			alert("error");
		}
	});
}

/************************************************************
		Function name: onready
		Author: Manav, Christian
		Created date: 09/30/2013
		Purpose: ready function invoked when page is rendered
 **************************************************************/

$(document).ready(function () {
    $("#projectddl").kendoComboBox({
		dataSource: [],
		dataTextField: "name",
		dataValueField: "id",
		select: function(){
			getReleases(this.value());
		}
	});
    $("#releaseddl").kendoComboBox({
		dataSource: [],
		dataTextField: "version",
		dataTextValue: "id"
	});
    $("#statusddl").kendoMultiSelect();

    getProjects();

    var dashdata = [];
    getTasks();

    $("#grid").kendoGrid({
        dataSource: {
            data: dashdata,
            group: {
                field: "Type"
            },
            pageSize: 5
        },
        columns: [{
            field: "Type",
            Title: "Type",
            width: "4%",
            template: function (item) {
                if (item.Type == "Bug") {
                    return "<span style='align-content:center' class='icon_24 bug' title='Bug'></span>";
                }
                else if (item.Type == "User Story") {
                    return "<span style='align-content:center' class='icon_24 br' title='User Story'></span>";
                }
                else {
                    return "<span style='align-content:center' class='icon_24 task' title='Task'></span>";
                }
            } 
        }, {
            field: "name",
            Title: "Title",
            width: "55%",
            template : "<a href='\\#${id}'>${name}</a>"
        }, {
            field: "category",
            Title: "Category",
            width: "10%"
        },
        {
            field: "priority",
            Title: "Priority",
            width: "10%"
        }, {
            field: "risk",
            Title: "Risk",
            width: "10%"
        },
        {
            field: "status",
            Title: "Status",
            width: "10%",
            template: function (item) {
                if (item.status == "Initial") {
                    return "<span style='text-align:center' class='icon_24 initial' title='Initial'></span>";
                }
                else if (item.status == "Pending") {
                    return "<span style='text-align:center' class='icon_24 warning' title='Pending'></span>";
                }
                else if (item.status == "InProgress") {
                    return "<span style='text-align:center' class='icon_24 inprogress' title='InProgress'></span>";
                }
                else if (item.status == "Complete") {
                    return "<span style='text-align:center' class='icon_24 complete' title='Complete'></span>";
                }
                else {
                    return "";
                }
            }
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
});

