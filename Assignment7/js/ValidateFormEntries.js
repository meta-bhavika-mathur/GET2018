// Map to store all error messages
var errorMap = new Map();

/**
* To validate entire from on click of submit button
*/
function validateForm()
{
	var firstName = document.getElementById("first_name");
	var lastName = document.getElementById("last_name");
	var email = document.getElementById("email");
	var phoneNumber = document.getElementById("phone");
	var city = document.getElementById("city");
	var state = document.getElementById("input_select");
	
	// If valid entries, save the data in local storage
	if(validateTextField(firstName) && validateEmail(email) && validatePhoneNumber(phoneNumber) && validateTextField(city) && validateState(state))
	{
		localStorage.clear();
        localStorage.setItem("First Name", firstName.value);
		if(lastName.value != "")
			localStorage.setItem("Last Name", lastName.value);
        localStorage.setItem("Email", email.value);
        localStorage.setItem("Phone #", phoneNumber.value);
		localStorage.setItem("City", city.value);
		localStorage.setItem("State", state.value);
		
		// If a state is selected from state drop down option
		if(state.value == "Rajasthan")
		{
			var website = document.getElementById("website_name").value;
			var projectDescription = document.getElementById("input_text_area").value;	
			if(website != "")
				localStorage.setItem("Website Name", website);
			localStorage.setItem("Project Description", projectDescription); 
		}
		else if(state.value == "Maharashtra")
		{
			var zipCode = document.getElementById("zipCode").value;
			var projectDescription = document.getElementById("input_text_area").value;
			localStorage.setItem("Zip Code", zipCode);
			localStorage.setItem("Project Description", projectDescription);
		}
		else 
			if(state.value = "Haryana")
			{
				var zipCode = document.getElementById("zipCode").value;
				localStorage.setItem("Zip Code", zipCode);
			}
		return true;
	}
	else
	{
		var errorMesage = "";
		// To get all the values of the map 
		var get_keys = errorMap.keys(); 
		for(var mapKey of get_keys) 
			var errorMesage = errorMesage + mapKey + ": " + errorMap.get(mapKey) + "\n";
		alert(errorMesage);
		return false;
	}	
}

/**
* To validate state
**/
function validateState(object)
{
	var state = document.getElementById("input_select");
	var result;
	if(state.value == "")
	{
		errorMap.set("State", "Please select an option!");
		return false;
	}	
	else
		if(state.value == "Rajasthan")
		{
			errorMap.delete("State");
			errorMap.delete("Project Description");
			errorMap.delete("Zip Code");
			var projectDescription = document.getElementById("input_text_area");
			if(projectDescription.value == "")
			{
				projectDescription.style.borderColor = "red";
				errorMap.set("Project Description", "Mandatory Field!"); 
				return false;
			}
				
		}
		else 
			if(state.value == "Maharashtra")
			{
				var zipCode = document.getElementById("zipCode");
				var projectDescription = document.getElementById("input_text_area");
				errorMap.delete("State");
				errorMap.delete("Project Description");
				errorMap.delete("Zip Code");
				if(zipCode.value == "")
				{
					projectDescription.style.borderColor = "red";
					errorMap.set("Zip Code", "Mandatory Field!");
					return false;
				}
				if(projectDescription.value == "")
				{
					projectDescription.style.borderColor = "red";
					errorMap.set("Project Description", "Mandatory Field!");
					return false;
				}
			}
			else 
				if(state.value = "Haryana")
				{
					errorMap.delete("State");
					errorMap.delete("Project Description");
					errorMap.delete("Zip Code");
					var zipCode = document.getElementById("zipCode");
					if(zipCode.value == "")
					{
						errorMap.set("Zip Code", "Mandatory Field!");
						return false;
					}
				}	
	result = true;
	object.style.borderColor = "green";
	errorMap.delete("Project Description");
	errorMap.delete("Zip Code");			
	return true;	
}
/**
 * To validate all text entries
 */
function validateTextField(object)
{
	var fieldValue = object.value;
	var placeholderValue = object.placeholder;
	var inputValue = fieldValue.toUpperCase();
	
	if(fieldValue == "")
	{
		object.style.borderColor = "red";
		errorMap.set(placeholderValue, 'Empty field!');
		return false;	
	}
	else
	{
		if (inputValue.length < 2) 
		{
			object.style.borderColor = "red";
			errorMap.set(placeholderValue, 'Length must be atleast 2 characters long!');
			return false;
		}
		for(i = 0; i < inputValue.length; i++)
		{
			if (!(inputValue.charAt(i) >= 'A' && inputValue.charAt(i) <= 'Z'))
			{
				object.style.borderColor = "red";
				errorMap.set(placeholderValue, 'Only alphabets are allowed!');
				return false;
			}
		}	
	}
	object.style.borderColor = "green";
	errorMap.delete(placeholderValue);
	return true;	
}

/**
*To validate email id 
*/
function validateEmail(object)
{
	var email = object.value;
	if(email == "")
	{
		object.style.borderColor = "red";
		errorMap.set("Email", 'Empty field!');
		return false;
	}
	else
	{
		if ( email.length < 6 || email.indexOf("@") == 0 || email.indexOf("@") == email.length - 1 )
		{
			object.style.borderColor = "red";
			errorMap.set("Email", 'Invalid Email!');
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
			errorMap.set("Email", '*Must contain only one \'@\' character!');
			return false;
		}
		if(emailDomain.length < 4 || emailDomain.indexOf('.') <= 0) 
		{
			object.style.borderColor = "red";
			errorMap.set("Email", '*Invalid domain!');
			return false;
		}

		if(emailDomainParts[emailDomainParts.length - 1].length < 2) 
		{
			object.style.borderColor = "red";
			errorMap.set("Email", '*Invalid domain name!');
			return false; 
		}
		
		for(i = 0; i < emailName.length; i++)
		{
			 if(validChars.indexOf(emailName[i]) < 0) 
			 {
				object.style.borderColor = "red";
				errorMap.set("Email", '*Invalid character in username!');
				return false; 
			}
		}		
		for(j = 0; j < emailDomain.length; j++)
		{
			 if(validChars.indexOf(emailDomain[j]) < 0)
			 {
				object.style.borderColor = "red";
				errorMap.set("Email", '*Invalid character in domain name!');
				return false; 
			}
		}	
	}	
	object.style.borderColor = "green";
	errorMap.delete("Email");
	return true;
}

/**
* To validate phone number
*/
function validatePhoneNumber(object)
{
	var phoneNumber = object.value;
	var result;
	
	if(phoneNumber == "")
	{
		object.style.borderColor = "red";
		errorMap.set("Phone Number", "Empty field!");
		result = false;	
	}
	else
	{
		if(phoneNumber < 7000000000 || phoneNumber > 9999999999 || isNaN(phoneNumber))
		{
			object.style.borderColor = "red";
			errorMap.set("Phone Number", "*Invalid phone number!");
			result = false;
		}
		else
		{
			object.style.borderColor = "green";
			errorMap.delete("Phone Number");
			result = true;
		}
	}
	return result;
}