// To validate email
function validateEmail(object)
{
	var email = document.getElementById(object.id).value;
	debugger;
	if ( email.length < 6 || email.indexOf("@") == 0 || email.indexOf("@") == email.length - 1 || email.indexOf("@") == -1 )
	{
		object.style.borderColor = "red";
		document.getElementById('emailError').innerHTML = "*Invalid Email!";
		return false;
	}
	var emailParts = email.split('@'),
        emailName = emailParts[0],
        emailDomain = emailParts[1],
        emailDomainParts = emailDomain.split('.'),
        validChars = '0123456789abcdefghijklmnopqrstuvwxyz._-';
		
	if(emailParts.length != 2) 
	{
		object.style.borderColor = "red";
		document.getElementById('emailError').innerHTML = "*Must contain only one '@' character!";
		return false;
	}
	if(emailDomain.length < 4 || emailDomain.indexOf('.') <= 0) 
	{
		object.style.borderColor = "red";
		document.getElementById('emailError').innerHTML = "*Invalid domain!";
		return false;
	}

    if(emailDomainParts[emailDomainParts.length - 1].length < 2) 
	{
        object.style.borderColor = "red";
		document.getElementById('emailError').innerHTML = "*Invalid domain name!";
		return false; 
    }
	
	for(i = 0; i < emailName.length; i++)
	{
		 if(validChars.indexOf(emailName[i]) < 0) 
		 {
            object.style.borderColor = "red";
			document.getElementById('emailError').innerHTML = "*Invalid character in username!";
            return false; 
		}
	}
	
	for(j = 0; j < emailDomain.length; j++)
	{
		 if(validChars.indexOf(emailDomain[j]) < 0)
		 {
            object.style.borderColor = "red";
			document.getElementById('emailError').innerHTML = "*Invalid character in domain name!";
            return false; 
		}
	}
	
	object.style.borderColor = "green";
	document.getElementById('emailError').innerHTML = "";
	return true;
}

//To validate password
function validatePassword(object)
{
	var password = document.getElementById(object.id).value;
	var result = true;
	var checkUpperCase = checkLowerCase = checkNumber = checkSpecialCase = false;
	
	if(password.length < 8)
	{
		object.style.borderColor = "red";
		document.getElementById('passwordError').innerHTML = "*Password must be of atleast 8 characters";
		result = false;
		return result;
	}
	for(i = 0; i < password.length; i++)
	{
		if(!checkUpperCase && (password.charCodeAt(i) >= 65 && password.charCodeAt(i) <= 90))
		{
			checkUpperCase = true;
		}

		if(!checkLowerCase && (password.charCodeAt(i) >= 97 && password.charCodeAt(i) <= 122))
		{
			checkLowerCase = true;
		}

		if(!checkNumber && (password.charCodeAt(i) >= 48 && password.charCodeAt(i) <= 57))
		{
			checkNumber = true;
		}

		if(!checkSpecialCase && ((password.charCodeAt(i) >= 33 && password.charCodeAt(i) <= 47) || (password.charCodeAt(i) >= 58 && password.charCodeAt(i) <= 64) || 
		   (password.charCodeAt(i) >= 91 && password.charCodeAt(i) <= 96) || (password.charCodeAt(i) >= 123 && password.charCodeAt(i) <= 126)))
		{
			checkSpecialCase = true;
		}
	}
	if(checkUpperCase && checkLowerCase && checkNumber && checkSpecialCase)
	{
		object.style.borderColor = "green";
		result = true;
	}
	else
	{
		object.style.borderColor = "red";
		result = false;
	}
	return result;
}