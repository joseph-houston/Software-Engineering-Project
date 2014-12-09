// We contain our app data here
CBWebApp = {};

// Initialize app properties with null so that they are consistent
CBWebApp.init = function(){
	this.data = {}; // We store our data here per report view
	this.data.productName = null;
	this.disablePdfButton();
}

CBWebApp.findProducts = function(productsUrl) {
	$.getJSON(productsUrl, {
		ajax : 'true'
	}, function(data) {
		var html = '<option value="">Select Product</option>';
		var len = data.length;
		for ( var i = 0; i < len; i++) {
			html += '<option value="' + data[i].name + '">'
					+ data[i].name + '</option>';
		}
		html += '</option>';
        //now that we have our options, give them to our select
		$('#products').html(html);
		              
	}).complete(function(){
		$('.select-themes').multiselect({
			//enableFiltering: true,
			includeSelectAllOption: true,
	        nonSelectedText: 'Select Themes',
	        buttonWidth: '140px',
	        disableIfEmpty: true,
	        maxHeight: 150,
	        nSelectedText: 'themes selected'
	    });		
	});	
}

// This is common to all reports
CBWebApp.disablePdfButton = function(){
	$('#export-pdf-btn').prop("disabled",true);
};

// we need this for datepicker plugin and use it where needed
CBWebApp.dateUtility = function() {
	$('.input-group.date').datepicker({
		clearBtn: true,
		orientation: "top",
		autoclose: true
	});	
};

CBWebApp.setStartDate = function(action) {
	$('#startDate').on('change', function(e){
		CBWebApp.data.startDate = $('#startDate').val();
		if(CBWebApp.data.endDate == undefined){
			$("#reportData").html("Select end date");
		} else {
			CBWebApp.submitData(action) 
		}		
		e.preventDefault();
	});
}

CBWebApp.setEndDate = function(action) {
	$('#endDate').on('change', function(e){
		CBWebApp.data.endDate = $('#endDate').val();
		if(CBWebApp.data.startDate == undefined){
			$("#reportData").html("Select start date");
		} else {
			CBWebApp.submitData(action) 
		}
		e.preventDefault();
	});	
}

CBWebApp.submitData = function(action) {
	$.ajax({
		url: action,
		data: CBWebApp.getFormData(),
		success: function(response) {
			$("#reportData").html(response);									       
			console.log("Success: " + response);
		}, 
		error: function(e) {
			console.log("Error: " + e)
		}
	});	
}

CBWebApp.getFormData = function(){
	var data = "";
	$.each( CBWebApp.data, function( key, value ) {
		if(value != undefined || value != null){
			if (key == 'productName'){
				data += key + "=" + value;
			} else {
				data += "&" +  key + "=" + value;
			}
		}
	});	
	return data;
}

CBWebApp.exportReport = function(reportName){
	$('#export-pdf-btn').click(function(e){
		var currentUrl = window.location;
		var lastChar =  String(currentUrl)[String(currentUrl).length-1];
		if (lastChar != "/"){
			currentUrl += "/report.pdf";;
		} else {
			currentUrl += "report.pdf";
		}
		currentUrl +="?" + CBWebApp.getFormData();
		window.open(currentUrl);
		e.preventDefault();
	});		
}

// Test 
CBWebApp.showDebugInfo = function(){
	console.log("Product Name = " + CBWebApp.data.productName);
	console.log("Theme Values = " + CBWebApp.data.themeValues);	
	console.log("User Name = " + CBWebApp.data.userName);
	console.log("Include History = " + CBWebApp.data.includeHistory);
	console.log("Start Date = " + CBWebApp.data.startDate);
	console.log("End Date = " + CBWebApp.data.endDate);
	console.log("Include Details = " + CBWebApp.data.includeDetails);	
}