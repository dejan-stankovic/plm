function formValidation()
{
document.getElementById('errormsg').innerHTML = "";
document.getElementById('errorMsg').innerHTML = "";

var uid = document.registration.userid;
var passid = document.registration.passid;
var uname = document.registration.firstname;
var lastname = document.registration.lastname;
var uemail = document.registration.email;

if(allLetter(uname))
{
if(allLetter(lastname))
{
if(ValidateEmail(uemail))
{
	regUser();
}
}
}
return false;
} 

function allLetter(uname)
{ 
var letters = /^[A-Za-z]+$/;
if(uname.value.match(letters))
{
return true;
}
else
{
document.getElementById('errorMsg').innerHTML = "Name cannot be empty and should have only alphabets!";
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
document.getElementById('errorMsg').innerHTML = "You have entered an invalid email address!";
uemail.focus();
return false;
}
}
function regUser(){
 
                $("#errormsg").html("");

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
                                                  	document.getElementById('errorMsg').innerHTML = "Registration Successful!! Welcome to PLM";
                                                    window.location="login.html"; /*Redirect to the login page after succesful registration*/
                                                  }
                                                 else if(data.code==1)
                                                 {
                                                 	document.getElementById('errorMsg').innerHTML = "Invalid Username or Password";/*displays error message*/
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