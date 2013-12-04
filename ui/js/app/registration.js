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