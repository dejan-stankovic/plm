function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function ltrim(stringToTrim) {
	return stringToTrim.replace(/^\s+/,"");
}
function rtrim(stringToTrim) {
	return stringToTrim.replace(/\s+$/,"");
}
function resetHandler() {
                                $("input#userid").val("");
                                $("input#passid").val("");
                                $("input#firstname").val("");
                                $("input#lastname").val("");
                                $("input#email").val("");
                                $("#errormsg").html("");
                                $("#errorMsg").html("");
}

function regUser(){
 	var user = $("input#userid").val();
	var pass = $("input#passid").val();

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

			/*the following code checks whether the entered userid is valid */
			if(data.code==0)
			{
				$("#errormsg").html("Registration Successful!! Welcome to PLM");
				window.location="login.html"; /*Redirect to the login page after succesful registration*/
			}
			else if(data.code==1)
			{
				$("#errormsg").html("Invalid Username or Password");/*displays error message*/
			}
			else 
			{ /* Usually internal error or other */
				$("#errormsg").html(data.message);
			}
		},
		error: function(data){
			alert("error");
		}
	});
}


$(document).ready(function () {

    $("#submitBtn").click(function(){ 
    	var fname = trim($("input#firstName").val());
		var lname = trim($("input#lastName").val());
		var emai = $("input#email").val();
		var un = trim($("input#userid").val());
		var pw = trim($("input#passid").val());
		// this function will execute if the button is being hit
		if($("input#firstName").val()=="")
		{
			$("#errormsg").html("One or more fields cannot be empty!");
		}
		else if($("input#lastName").val()=="")
		{
			$("#errormsg").html("One or more fields cannot be empty!");
		}
		else if($("input#email").val()=="")
		{
			$("#errormsg").html("One or more fields cannot be empty!");
		}
		else if($("input#userid").val()=="")
		{
			$("#errormsg").html("One or more fields cannot be empty!");
		}
		else if($("input#passid").val()=="")
		{
			$("#errormsg").html("One or more fields cannot be empty!");
		}
		else if(fname.match(/^[A-Za-z ]+$/) != null )
		{
			if(lname.match(/^[A-Za-z ]+$/) != null )
			{
			if(un.match(/^[A-Za-z0-9 ]+$/) != null )
			{
			regUser();
			}
		}
		else
			{
				$("#errormsg").html("One or more fields are invalid!");
			}
			return false;
		}
		else
		{	
			// var uid = $("input#userid").val();
			// var passid = $("input#passid").val();
			// var fname = $("input#firstName").val();
			// var lname = $("input#lastName").val();
			// var emai = $("input#email").val();

			// if(allLetter(fname))
			// {
			// if(allLetter(lname))
			// {
			// if(ValidateEmail(emai))
			// {
			// if(allLetterUid(uid))
			// {
			// regUser();
			// }
			// }
			// }
			// }
			// else
			// {
				$("#errormsg").html("One or more fields are invalid!");
			// }
			// return false;
		}
		return false;
	});
	
	$('#resetBtn').click( function() { 

	resetHandler();
	});
	
});