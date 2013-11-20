function regUser()
{
	var user;

                $("#errormsg").html("");

                var uid = document.registration.userid;
				var passid = document.registration.passid;
				var firstname = document.registration.firstname;
				var lastname = document.registration.lastname;
				var uemail = document.registration.email;

                                $.ajax({
                                        type: 'POST',
                                        url: '/plm/rest/register',
                                        contentType: 'application/json; charset=UTF-8',
                                        accepts: {
                                                text: 'application/json'
                                        },
                                        dataType: 'json',
                                        data: JSON.stringify({
                                                name: uid,
                                                password: passid
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
if(nameCheck(firstname))
{
if(nameCheck(lastname))
{
if(ValidateEmail(uemail))
{
}
}
}
return false;
} 

function nameCheck(firstname)
{ 
var letters = /^[A-Za-z]+$/;
if(uname.value.match(letters))
{
return true;
}
else
{
alert('Name must have alphabet characters only');
uname.focus();
return false;
}
}

function ValidateEmail(uemail)
{
var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
if(uemail.value.match(mailformat))
{
return true;
}
else
{
alert("You have entered an invalid email address!");
uemail.focus();
return false;
}
}