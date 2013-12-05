/*************************************************************************
		File name: login.js
		Author: Rachit
		Created date: 09/30/2013
		Purpose: client functionality associated with login page
**************************************************************************/

/************************************************************
Function name: loginHandler
Author: Christian Heckendorf, Rachit
Created date: 10/13/2013
Purpose: Processes the login form
************************************************************/
function loginHandler() {
	var user, pass;

	$("#errormsg").html("");

	user = $("input#user").val();
	pass = $("input#pass").val();

	$.ajax({
		type: 'POST',
		url: '/plm/rest/login',
		contentType: 'application/json; charset=UTF-8',
		accepts: {
			text: 'application/json'
		},
		dataType: 'json',
		data: JSON.stringify({
			name: user,
			password: pass
		}),
		success: function(data) {
			/*the following code checkes whether the entered userid and password are matching*/
			if(data.code==0) {
				setToken(data.message);
				window.location = "dashboard.html"; /*opens the target page while Id & password matches*/
			}
			else if(data.code==1) {
				$("#errormsg").html("Invalid Username or Password"); /*displays error message*/
			}
			else { /* Usually internal error or other */
				$("#errormsg").html(data.message);
			}

		},
		error: function(data){
			alert("error");
		}
	});
}

/************************************************************
Function name: cancelHandler
Author: Christian Heckendorf
Created date: 10/13/2013
Purpose: Cancels the login form
************************************************************/
function cancelHandler() {
	$("input#user").val("");
	$("input#pass").val("");
	$("#errormsg").html("");
}

/************************************************************
		Function name: onready
		Author: Rachit
		Created date: 09/30/2013
		Purpose: ready function invoked when page is rendered
**************************************************************/
$(document).ready(function () {
    var window = $("#splashPopup");
    if (!window.data("kendoWindow")) {
        window.kendoWindow({
            width: "600px",
            modal: true,
            title: "Project Life Cycle Management 1.0",
            actions: [
                "Pin",
                "Minimize",
                "Maximize",
                "Close"
            ]
        });
    }
    window.data("kendoWindow").center().open();
    setTimeout(function () { window.data("kendoWindow").close(); document.getElementById("body").style.display = ""; }, 3000);

    $("a#loginbtn").click(function () {
        user = $("input#user").val();
        pass = $("input#pass").val();
        if (user == null || user == '') {
            $("#errormsg").html("Username is required");
            return;
        }
        if (pass == null || pass == '') {
            $("#errormsg").html("Password is required");
            return;
        }
		loginHandler();
	})

	$("a#cancelbtn").click(function(){
		cancelHandler();
	})
});


