/************************************************************
Filename: createIter.js
Author: Alan Tang, Christian Heckendorf
Date last updated: 11/5/2013
Purpose: Add a new release/iteration for a current project
************************************************************/

//Global variable of current iterations in a project

var IterList = new Array();

/************************************************************
Function name: displayList
Author: Christian Heckendorf
Created date: 10/13/2013
Purpose: Displays a list of releases in a project
************************************************************/
function displayList(){
	var cb, tok;

	tok = getToken();
        pid = getCurProject();

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
                                $('curIters').append("<h1>"+data.releases[x]+"</h1>");
                                IterList.push(data.releases[x]);
			}
		},
		error: function(data){
			alert("error");
		}
	});
}


/***************************************************************************************
                Function name: getDates
                Author: Alan Tang
                Created date: 11/05/2013
                Purpose: Retrieve unformatted start and end date of iteration from user
***************************************************************************************/
$(function() {
    $("#start").datepicker({beforeShowDay: function(date){
        var string = jQuery.datepicker.formatDate('yy-mm-dd', date);
        return [ iterList.indexOf(string) === -1 ];
    },
    minDate: 0});
});
            
$(function() {
    $("#end").datepicker({beforeShowDay: function(date){
        var string = jQuery.datepicker.formatDate('yy-mm-dd', date);
        return [ iterList.indexOf(string) === -1 ];
    },
    minDate: 1, maxDate: "+6m"});
});
           
/***************************************************************************************
                Function name: createIter
                Author: Alan Tang
                Created date: 11/05/2013
                Purpose: Generates a new release under current project given start
                         and end dates + version name
***************************************************************************************/



function createIter(info){
    /*Formats the dates selected by user + current project's ID*/
    var formatStart = $.datepicker.formatDate("yy-mm-dd", new Date(info.elements.startDate.value));
    var formatEnd = $.datepicket.formatDate("yy-mm-dd", new Date(info.elements.endDate.value));
    var projectID = getCurProject();
    var version = info.elements.version.value;
    tok = getToken();
                
    $.ajax({
        type: 'POST',
        url: '/plm/rest/projectmanage/release/p/' + projectID,
        contentType: 'application/json; charset=UTF-8',
        accepts: {
            text: 'application/json'
        },
        dataType: 'json',
        data: JSON.stringify({
        token: {
            token: tok
        },
        release: {
            version: version,
            startDate: formatStart,
            endDate: formatEnd
        }
    }),
    success: function(data){
    // Returns success message
        $("div#updateMessage").html("New iteration: " + version + " created for project " + projectID);
    },
    error: function(data){
        alert("error");
    }
    });             
}