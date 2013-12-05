function formValidation()
{
  
var uid = $("input#userid").val();
var passid = $("input#passid").val();
var uname = $("input#firstname").val();
var lastname = $("input#lastname").val();
var uemail = $("input#email").val();

if(allLetter(uname))
{
if(allLetter(lastname))
{
if(ValidateEmail(uemail))
{
if(allLetterUid(uid))
{
  regUser();
}
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

function allLetterUid(uname)
{ 
var letters = /^[A-Za-z0-9_]{3,20}+$/;
if(uname.value.match(letters))
{
return true;
}
else
{
document.getElementById('errorMsg').innerHTML = "Username can have only alphabets, numbers and _";
uname.focus();
return false;
}
}

function regUser(){
 
              var  user = $("input#userid").val();
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

function resetHandler() {
        $("input#userid").val("");
        $("input#passid").val("");
        $("input#firstname").val("");
        $("input#lastname").val("");
        $("input#email").val("");
        $("#errormsg").html("");
        $("#errorMsg").html("");
}