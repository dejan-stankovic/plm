/************************************************************
Filename: createIter.js
Author: Alan Tang, Christian Heckendorf
Date last updated: 11/5/2013
Purpose: Add a new release/iteration for a current project
************************************************************/

//Global variable of current iterations in a project
//Dates is the start and end dates, while list is the range
//of dates that should be greyed out on the datepicker

var iterDates = new Array();
var iterList = new Array();

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
                                iterDates.push(data.releases[x]);
			}
                        
                        for(var i=0;i<iterDates.length;i+=2)
                        {
                            var pointer = iterDates[i];
                            while(pointer<iterDates[i+1])
                            {
                                iterList.push(pointer);
                                pointer++;
                            }
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
        var string = $.datepicker.formatDate('yy-mm-dd', date);
        return [ iterList.indexOf(string) === -1 ];
    },
    minDate: 0});
});
            
$(function() {
    $("#end").datepicker({beforeShowDay: function(date){
        var string = $.datepicker.formatDate('yy-mm-dd', date);
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
    var formatEnd = $.datepicker.formatDate("yy-mm-dd", new Date(info.elements.endDate.value));
    var projectID = getCurProject();
    var version = info.elements.version.value;
    tok = getToken();
    
    if (formatStart>formatEnd){
        $("div#updateMessage").html("Iteration end date cannot be earlier than start date. Please check your inputs and try again.");
        return false;
    }
                
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
    // Returns success message, resets the array of lists that should be greyed out
    // Calls on displayList to repopulate the current iteration list + greyed out ranges
        iterList = new Array();
        $("div#updateMessage").html("New iteration: " + version + " created for project " + projectID);
        displayList();
    },
    error: function(data){
        alert("error");
    }
    });             
}