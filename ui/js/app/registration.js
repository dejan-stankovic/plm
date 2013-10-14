/*************************************************************************
		File name: registration.js
		Author: Yuvaraj
		Created date: 10/13/2013
		Purpose: client functionality associated with registration page
**************************************************************************/

/************************************************************
		Function name: regUser
		Author: Yuvaraj
		Created date: 10/13/2013
		Purpose: Processes the registration form
**************************************************************/

function regUser(){
		var user;

		$("#errormsg").html("");

		user = $("input#uname").val();
		pass = $("input#pwd").val();


				$.ajax({
					type: 'POST',
					url: '/plm/rest/register',
					contentType: 'application/json; charset=UTF-8',
					accepts: {
						text: 'application/json'
					},
					dataType: 'json',
					data: JSON.stringify({
						name: user,
						password: pass
					}),
					success: function(data){

						/*the following code checkes whether the entered userid is valid */
						 if(data.code==0)
						  {
						  $("h1").html("Registration Successful!! Welcome to PLM");
						  window.location="login.html"; /*Redirect to the login page after succesful registration*/
						  }
						 else if(data.code==1)
						 {
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
Function name: resetHandler
Author: Yuvaraj
Created date: 10/13/2013
Purpose: Cancels the registration form
************************************************************/
function resetHandler() {
	$("input#uname").val("");
	$("input#pwd").val("");
	$("#errormsg").html("");
}
/************************************************************
		Function name: onready
		Author: Yuvaraj
		Created date: 10/13/2013
		Purpose: ready function invoked when page is rendered
**************************************************************/
$(document).ready(function () {

$("a#submitbtn").click(function(){
		regUser();
	})
	
$("a#resetbtn").click(function(){
		regHandler();
	})
	    
});
