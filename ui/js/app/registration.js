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
function regUser(){
		var user;

		$("#errormsg").html("");

		user = $("input#uname").val();


				$.ajax({
					type: 'POST',
					url: '/plm/rest/register',
					contentType: 'application/json; charset=UTF-8',
					accepts: {
						text: 'application/json'
					},
					dataType: 'json',
					data: JSON.stringify({
						name: user
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
						   alert("Invalid Username or username already exists") /*displays error message*/
	  					 }
					},
					error: function(data){
						alert("error");
					}
				});
		}

/************************************************************
		Function name: onready
		Author: Yuvaraj
		Created date: 09/30/2013
		Purpose: ready function invoked when page is rendered
**************************************************************/

$(document).ready(function () {

    $("input#pwd").click(function() {
			$( document ).tooltip();
		})
	$("input#datepicker").click(function() {
			$( document ).tooltip();
		})
	$(function() {
		    		$( "a#submitbtn" )
		     		.button()
		      		.click(function( event ) {
		      		event.preventDefault();
		     		});
		})
	$(function() {
    		$( "input#datepicker" ).datepicker();
  		});
 });