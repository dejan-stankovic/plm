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
			iterDates = new Array();
			iterList = new Array();
			for(x in data.releases){
                                iterDates.push(data.releases[x]);
			}

                        
                        for(var i=0;i<iterDates.length;i++)
                        {
				var sd = iterDates[i].startDate.split("-");
				var currentDate = new Date(sd[0],sd[1]-1,sd[2]);
				var ed = iterDates[i].endDate.split("-");
				var endDate = new Date(ed[0],ed[1]-1,ed[2]);
				while (currentDate <= endDate) {
					iterList.push(currentDate)
					currentDate.setDate(currentDate.getDate()+1);
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
	for(var i=0;i<iterDates.length;i++){
		var ad=new Date(iterDates[i].startDate);
		var bd=new Date(iterDates[i].endDate);
		if(date>=ad && date<=bd){
			return [false,"","Unavailable"];
		}
	}
	return [true,"","Available"];
    },
    minDate: 0});
});
            
$(function() {
    $("#end").datepicker({beforeShowDay: function(date){
	for(var i=0;i<iterDates.length;i++){
		var ad=new Date(iterDates[i].startDate);
		var bd=new Date(iterDates[i].endDate);
		if(date>=ad && date<=bd){
			return [false,"","Unavailable"];
		}
	}
	return [true,"","Available"];
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

$(document).ready(function () {
	displayList();
});
