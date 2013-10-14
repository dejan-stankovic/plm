/*************************************************************************
		File name: registration.js
		Author: Yuvaraj
		Created date: 09/30/2013
		Purpose: client functionality associated with registration page
**************************************************************************/

/************************************************************
		Function name: onready
		Author: Yuvaraj
		Created date: 09/30/2013
		Purpose: Processes the registration form
**************************************************************/

$(document).ready(function () {
    
function regUser(){
		var user;

		$("#errormsg").html("");

		user = $("input#uname").val();
		pass = $("input#pass").val();


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
});
