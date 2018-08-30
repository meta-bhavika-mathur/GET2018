/**
 * To validate name of employee
 */
function validateName(object)
{
	var nm = document.getElementById(object.id).value;
	var name = nm.toUpperCase();
		
	if (name.length < 2) 
	{
		if(object.id == 'firstName')
				document.getElementById('firstNameError').innerHTML = "*Invalid First Name!";
		else if(object.id == 'lastName')
				document.getElementById('lastNameError').innerHTML = "*Invalid Last Name!";
		object.style.borderColor = "red";
		return false;
	}
	for(i = 0; i < nm.length; i++)
	{
		if (!(name.charAt(i) >= 'A' && name.charAt(i) <= 'Z'))
		{
			object.style.borderColor = "red";
			if(object.id == 'firstName')
				document.getElementById('firstNameError').innerHTML = "*Invalid First Name!";
			else if(object.id == 'lastName')
				document.getElementById('lastNameError').innerHTML = "*Invalid Last Name!";
			return false;
		}
	}
	object.style.borderColor = "green";
	if(object.id == 'firstName')
		document.getElementById('firstNameError').innerHTML = "";
	else if(object.id == 'lastName')
		document.getElementById('lastNameError').innerHTML = "";
	return true;	
}

/**
*To validate email id of employee
*/
function validateEmail(object)
{
	var email = document.getElementById(object.id).value;
	
	if ( email.length < 6 || email.indexOf("@") == 0 || email.indexOf("@") == email.length - 1 )
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

/**
* To validate age of employee
*/
function validateAge(object)
{
	var age = document.getElementById(object.id).value;
	var result;
	
	if(age < 18 || age > 70 || isNaN(age))
	{
		object.style.borderColor = "red";
		document.getElementById('ageError').innerHTML = "*Invalid age!";
		result = false;
	}
	else
	{
		object.style.borderColor = "green";
		document.getElementById('ageError').innerHTML = "";
		result = true;
	}
	return result;
}

/**
* To validate entire from on click of submit button
*/
function validateForm(object)
{
	var firstName = document.getElementById("firstName");
	var lastName = document.getElementById("lastName");
	var email = document.getElementById("email");
	var age = document.getElementById("age");
	
	if(validateName(firstName) && validateName(lastName) && validateEmail(email) && validateAge(age))
		return true;
	else
	{
		alert("Invalid input field!");
		return false;
	}	
}

function validateSearchForm(object)
{
	var firstName = document.getElementById("firstName");
	var lastName = document.getElementById("lastName");
	if(validateName(firstName) && validateName(lastName))
		return true;
	else
	{
		alert("Invalid input field!");
		return false;
	}	
	
}