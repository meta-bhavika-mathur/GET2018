/* Function to add zip code field */
function addZipCodeField()
{
	var element = document.createElement('div');
	element.className = "form_field additional_field";	
	element.innerHTML = '<label class = "field_label">Zip Code</label>\
						<div class = "input_field_container">\
						<div class = "input_field_icon"><i class="fas fa-home"></i></div>\
						<input id = "zipCode" name = "zip" placeholder = " Zip Code" class = "input_text_box" type = "number">\
						</div>';
	document.getElementById("form_entries").appendChild(element);					  
}
/* Function to add Project Description Field */
function addProjectDescriptionField()
{
	var element = document.createElement('div');
	element.className = "form_field additional_field";	
	element.innerHTML = ' <label class = "field_label">Project Description</label>\
							<div class = "input_field_container">\
							<div class = "input_field_icon"><i class="fas fa-pen"></i></div>\
							<textarea id = "input_text_area" name = "comment" placeholder = " Project Description"></textarea>\
							</div>';
	document.getElementById("form_entries").appendChild(element);
}
/* Function to add Do Youhave hosting field */
function addDoYouHaveHostingField()
{
	var element = document.createElement('div');
	element.className = "form_field additional_field";	
	element.innerHTML = '<label class = "field_label">Do you have hosting?</label>\
							<div id = "radio_button_container" class = "input_field_container">\
							<div class = "radio"><label><input type = "radio" name = "hosting" value = "yes" /> Yes </label></div>\
							<div class = "radio"><label><input type = "radio" name = "hosting" value = "no" /> No </label></div>\
							</div>';
	document.getElementById("form_entries").appendChild(element);
}
/* Function to add website or domain name field */
function addWebsiteDomainNameField()
{
	var element = document.createElement('div');
	element.className = "form_field additional_field";	
	element.innerHTML = '<label class = "field_label">Website or domain name:</label>\
							<div class = "input_field_container">\
							<div class = "input_field_icon"><i class="fas fa-globe-asia"></i></div>\
							<input id = "website_name" class = "input_text_box" type = "text" name = "website" placeholder =" Website or domain name">\
							</div>';
	document.getElementById("form_entries").appendChild(element);
}
/* To dynamically add form fields based on option selected from drop down list */
function addFormFields()
{
    var element = document.getElementById("input_select").value;

	// To first remove extra fields in the form 
	removeAdditionalFields(); 
	// To add required fields 
	if(element == 'Rajasthan')
	{
		addWebsiteDomainNameField();
		addProjectDescriptionField();	
	}
	else
		if(element == 'Haryana')
		{
			addZipCodeField();
			addDoYouHaveHostingField();
		}
		else
			if(element == 'Maharashtra')
			{
				addZipCodeField();
				addProjectDescriptionField();	
			}
}

/* To dynamically remove additional form fields */
function removeAdditionalFields()
{
    var additionalElements = document.getElementsByClassName("additional_field");
	var counter;
	
	while (additionalElements.length > 0) 
	{
		additionalElements[0].parentNode.removeChild(additionalElements[0]);
	}
}