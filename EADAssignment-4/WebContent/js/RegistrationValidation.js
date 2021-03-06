// To validate name	
function validateName(object)
{
	var nm = document.getElementById(object.id).value;
	var name = nm.toUpperCase();
		
	if (name.length < 2) 
	{
		if(object.id == 'first_name')
				document.getElementById('firstNameError').innerHTML = "*Invalid First Name!";
		else if(object.id == 'last_name')
				document.getElementById('secondNameError').innerHTML = "*Invalid Last Name!";
		object.style.borderColor = "red";
		return false;
	}
	for(i = 0; i < nm.length; i++)
	{
		if (!(name.charAt(i) >= 'A' && name.charAt(i) <= 'Z'))
		{
			object.style.borderColor = "red";
			if(object.id == 'first_name')
				document.getElementById('firstNameError').innerHTML = "*Invalid First Name!";
			else if(object.id == 'last_name')
				document.getElementById('secondNameError').innerHTML = "*Invalid Last Name!";
			return false;
		}
	}
	object.style.borderColor = "green";
	if(object.id == 'first_name')
		document.getElementById('firstNameError').innerHTML = "";
	else if(object.id == 'last_name')
		document.getElementById('secondNameError').innerHTML = "";
	return true;	
}

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

// To validate phone number
function validatePhoneNumber(object)
{
	var phoneNumber = document.getElementById(object.id).value;
	var result;
	
	if(phoneNumber.length < 9 || isNaN(phoneNumber))
	{
		object.style.borderColor = "red";
		document.getElementById('mobileNumberError').innerHTML = "*Should contain only digits with length not less than 9!";
		result = false;
	}
	else
	{
		object.style.borderColor = "green";
		document.getElementById('mobileNumberError').innerHTML = "";
		result = true;
	}
	return result;
}

// To validate password
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

// To check if text entered in confirm password field matches the text in the password field
function validateConfirmPassword(object)
{
	var password = document.getElementById('password').value;
	var confirm = document.getElementById('confirm_password').value;
	var result;

	if((confirm.length < 8) || (password != confirm))
    {     
		object.style.borderColor = "red";
		document.getElementById('confirmPasswordError').innerHTML = "Invalid password!";
		result = false;
    }
    else
    {
       object.style.borderColor = "green";
	   document.getElementById('confirmPasswordError').innerHTML = "";
	   result = true;
    }
    return result;
}

// To validate complete form on click of button
function validateForm()
{
	var firstName = document.getElementById("first_name");
	var lastName = document.getElementById("last_name");
	var contact = document.getElementById("contact_number");
	var email = document.getElementById("email");
	var password = document.getElementById("password");
	var confirmPassword = document.getElementById("confirm_password");
	
	if(validateName(firstName) && validateName(lastName) && validateEmail(email) 
		&& validatePhoneNumber(contact) && validatePassword(password) && validateConfirmPassword(confirmPassword))
		return true;
	else
	{
		alert("Invalid input field!");
		return false;
	}	
}

// To authenticate valid user in login form
function validateUser()
{
	var email = document.getElementById("userEmail");
	var password = document.getElementById("userPassword"); 
	
	if(validateEmail(email) && validatePassword(password))
	{
		if(email.value == "bhavika.mathur@metacube.com" && password.value == "Abcdefg12#")
		{
			return true;
		}
		else
		{
			alert("Incorrect email id or password!");
			return false;
		}
	}	
	else
	{
		alert("Invalid username or password!");
		return false;
	}	
}

// To validate age of user
function validateAge(object)
{
	var age = document.getElementById(object.id).value;
	
	if(age < 18 || age > 80)
	{
		object.style.borderColor = "red";
		document.getElementById('ageError').innerHTML = "*Age not allowed!";
		return false;
	}
	else
	{
		object.style.borderColor = "green";
		document.getElementById('ageError').innerHTML = "";
		return true;
	}
}

