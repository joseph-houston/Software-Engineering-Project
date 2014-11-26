// We contain our app data here
CBWebApp = {};

// Initialize app properties with null so that they are consistent
CBWebApp.init = function(){
	this.data = {}; // We store our data here per report view
	this.data.productName = null;
	this.data.productValue = null;


	//this.data.startDate = null;
	//this.data.endDate = null
	//this.data.includeDetails = null;
	//this.data.name = "cbwebapp";
	this.disablePdfButton();
	//alert(CBWebApp.data.name + CBWebApp.data.common);
}


// Process kanban-workflow-warnings
CBWebApp.processKanbanWorkflowWarnings = function() {
	// No product yet so disable schedule email pop up.
	$("#schedule-email-popup").prop("disabled", true);
		
	$( "#products" ).change(function(e) {
		CBWebApp.data.productName = $( "#products option:selected" ).text();
		CBWebApp.data.productValue = $( this ).val();
	
		if ( CBWebApp.data.productValue !=  null  && CBWebApp.data.productValue != "") {
			// Now enable our buttons since we have a product
			$("#export-pdf-btn").prop("disabled",false);
			$("#schedule-email-popup").prop("disabled",false);
			
			CBWebApp.showDebugInfo();		
			// Auto submit our form to get the report 
			/*
			$.ajax({
			    type:"GET",
			    data:CBWebApp.data,
			    url:"<c:url value="/" />",
			    async: false,
			    //dataType: "json",
			    success: function(response){
			       //alert("success");
			       $("#data").html(response);
			    },
			    error: function(e){
			    	alert("Error: " + e);
			    }
			});
			*/			
		} else {
	    	$("#export-pdf-btn").prop("disabled",true);
	    	$("#schedule-email-popup").prop("disabled",true);					
		}
		
		e.preventDefault();
	});		
	
	// Proces modal for email scheduling
	$("#schedule-email-popup").click(function(event){	
		$("#email-popup").modal('show');
		event.preventDefault();
	});	   	
	
};

//Process kanban-activity-report
CBWebApp.processKanbanActivityReport = function(){
	//this.dateUtility();
	
	//$("#karThemes").attr("disabled", true);
	/*$( "#products" ).change(function(e) {
		// Get our product name and store it to our global
		CBWebApp.data.productName = $( "#products option:selected" ).text();
		CBWebApp.data.productValue = $( this ).val();
		CBWebApp.data.themeNames = null;
		CBWebApp.data.themeValues = null;		
		CBWebApp.showDebugInfo();
		
		if ( CBWebApp.data.productValue!=  null  && CBWebApp.data.productValue != "") {
			// Now enable our buttons since we have a product
			$("#export-pdf-btn").prop("disabled",false);
			CBWebApp.findThemesForProduct(url);
			// Auto submit our form to get the report 
			
			$.ajax({
			    type:"POST",
			    data:CBWebApp.data,
			    url:"<c:url value="/kanbanActivityReport/" />",
			    async: false,
			    //dataType: "json",
			    success: function(response){
			       //alert("success");
			       $("#data").html(response);
			    },
			    error: function(e){
			    	alert("Error: " + e);
			    }
			});					
			
		} else {
	    	$("#export-pdf-btn").prop("disabled",true);				
		}
		e.preventDefault();
	});	*/	
	
		
	CBWebApp.setStartDate();
	CBWebApp.setEndDate();

	/*
	$('.select-themes').multiselect({
		//enableFiltering: true,
		includeSelectAllOption: true,
        nonSelectedText: 'Select Themes',
        buttonWidth: '140px',
        disableIfEmpty: true,
        maxHeight: 150,
        nSelectedText: 'themes selected'
    });		
	*/
}


// Process kanban-user-activity 
CBWebApp.processKanbanUserActivity = function() {
	this.dateUtility();
	
/*	$('.select-themes').multiselect({
		//enableFiltering: true,
		includeSelectAllOption: true,
        nonSelectedText: 'Select Themes',
        buttonWidth: '140px',
        disableIfEmpty: true,
        maxHeight: 150,
        nSelectedText: 'themes selected'
    });*/
};

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

CBWebApp.setStartDate = function() {
	$('#startDate').on('change', function(e){
		CBWebApp.data.startDate = $('#startDate').val();	
		CBWebApp.showData();
		e.preventDefault();
	});
}

CBWebApp.setEndDate = function() {
	$('#endDate').on('change', function(e){
		CBWebApp.data.endDate = $('#endDate').val();
		CBWebApp.showData();
		e.preventDefault();
	});	
}










// Test 
CBWebApp.showDebugInfo = function(){
	console.log("Product Name = " + CBWebApp.data.productName);
	console.log("Product Value = " + CBWebApp.data.productValue);
	console.log("Theme Names = " + CBWebApp.data.themeNames);
	console.log("Theme Values = " + CBWebApp.data.themeValues);		
	console.log("Include History = " + CBWebApp.data.includeHistory);
}
