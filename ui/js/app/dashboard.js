/*************************************************************************
		File name: dashboard.js
		Author: Manav, Christian
		Created date: 09/30/2013
		Purpose: client functionality associated with dashboard page
**************************************************************************/

/************************************************************
Function name: clearTasks
Author: Christian Heckendorf
Created date: 10/13/2013
Purpose: Removes all tasks from the grid
************************************************************/
function clearTasks(){
	var g;
	g = $("#grid").data("kendoGrid");
	g.setDataSource(new kendo.data.DataSource({
		data: [],
		group: {
			field: "Type"
		},
		pageSize: 5
	}));
}

/************************************************************
Function name: dashboardTaskAjax
Author: Christian Heckendorf
Created date: 10/13/2013
Purpose: Runs the dashboard ajax for some task service
************************************************************/
function dashboardTaskAjax(tok, field, tail, type){
	$.ajax({
		type: 'POST',
		url: '/plm/rest/dashboard/'+field+tail,
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
			for(x in data[field]){
				data[field][x].Type=type;
				grid.dataSource.add(data[field][x]);
			}
		},
		error: function(data){
			alert("error");
		}
	});

}

/************************************************************
Function name: genericTaskFetch
Author: Christian Heckendorf
Created date: 10/13/2013
Purpose: Wrapper to fetch tasks with optional service filter
************************************************************/
function genericTaskFetch(tok, tail){
	dashboardTaskAjax(tok,"bugs",tail,"Bug");
	dashboardTaskAjax(tok,"tasks",tail,"Task");
}

/************************************************************
Function name: filterTasks
Author: Christian Heckendorf
Created date: 10/13/2013
Purpose: Sets the list of tasks based on the filter
************************************************************/
function filterTasks(){
	var g, tok, rid, cb;

	tok = getToken();

	clearTasks();

	cb = $("select#releaseddl").data("kendoComboBox");
	rid = cb.dataItem().id;

	var grid = $("#grid").data("kendoGrid");
	var multiselect = $("#statusddl").data("kendoMultiSelect");
	var validStatus = multiselect.value();
	var statusFilter = [];
	for(x in validStatus){
		statusFilter.push({
			field: "status",
			operator: "eq",
			value: validStatus[x]
		});
	}
	grid.dataSource.filter({
		logic: "or",
		filters: statusFilter
	});

	genericTaskFetch(tok,"/release/"+rid);
}

/************************************************************
Function name: getTasks
Author: Christian Heckendorf
Created date: 10/13/2013
Purpose: Updates the grid with a user's tasks
************************************************************/
function getTasks(){
	var g, tok;

	tok = getToken();

	clearTasks();

	genericTaskFetch(tok,"");
}

/************************************************************
Function name: getReleases
Author: Christian Heckendorf
Created date: 10/13/2013
Purpose: Updates a list of releases in a project
************************************************************/
function getReleases(pid){
	var cb, tok;

	tok = getToken();

	cb = $("select#releaseddl").data("kendoComboBox");
	cb.setDataSource(new kendo.data.DataSource({ data: [] })); // Empty it first

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
	var cb, tok;

	tok = getToken();

	cb = $("select#projectddl").data("kendoComboBox");
	cb.setDataSource(new kendo.data.DataSource({ data: [] })); // Empty it first

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
	$("#filtertasks").click(function(){
		filterTasks();
	});
	$("#resettasks").click(function(){
		getTasks();
	});

    getProjects();

    var dashdata = [];

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
            title: "Type",
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
            title: "Title",
            width: "55%",
            template : "<a href='\\#${id}'>${name}</a>"
        }, {
            field: "category",
            title: "Category",
            width: "10%"
        },
        {
            field: "priority",
            title: "Priority",
            width: "10%"
        }, {
            field: "risk",
            title: "Risk",
            width: "10%"
        },
        {
            field: "status",
            title: "Status",
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

    getTasks();
});

