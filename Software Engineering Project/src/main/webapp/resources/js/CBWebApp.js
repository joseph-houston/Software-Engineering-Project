// We contain our app data here
CBWebApp = {};

// Initialize app properties with null so that they are consistent
CBWebApp.init = function(){
	this.data = {}; // We store our data here per report view
	this.data.productName = null;
	this.data.productValue = null;
	//this.data.themeNames = null;
	//this.data.themeValues = null;
	//this.data.includeHistory = false;
	//this.data.startDate = null;
	//this.data.endDate = null
	//this.data.includeDetails = null;
	//this.data.name = "cbwebapp";
	//this.disablePdfButton();
	//alert(CBWebApp.data.name + CBWebApp.data.common);
}


// Process kanban-workflow-warnings
CBWebApp.processKanbanWorkflowWarnings = function() {
	// on ready disable schedule email button since 
	// we don't have a product yet.
	//$('#schedule-email').prop("disabled",true);
	
	$( "#products" ).change(function() {
		CBWebApp.data.productName = $( "#product option:selected" ).text();
		CBWebApp.data.productValue = $( this ).val();
		CBWebApp.showData();
		
		if ( CBWebApp.data.productName !=  null  && CBWebApp.data.productName != "Select Product") {
			// Now enable our buttons since we have a product
			$("#export-pdf-btn").prop("disabled",false);
			$("#schedule-email").prop("disabled",false);
			
			// Auto submit our form to get the report 
			$.ajax({
			    type:"POST",
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
		} else {
	    	//$("#export-pdf-btn").prop("disabled",true);
	    	//$("#schedule-email").prop("disabled",true);					
		}
	});		
	
	// Proces modal for email scheduling
	$(".enter-email").click(function(event){
		event.preventDefault();
		$("#enter-email").modal('show');
	});	   
	
	
};

//Process kanban-activity-report
CBWebApp.processKanbanActivityReport = function(){
	this.dateUtility();
	
	//$("#karThemes").attr("disabled", true);
/*	
	$( "#karProduct" ).change(function(e) {
		// Get our product name and store it to our global
		CBWebApp.data.productName = $( "#karProduct option:selected" ).text();
		CBWebApp.data.productValue = $( this ).val();
		//CBWebApp.showData();
		
		if ( CBWebApp.data.productName !=  null  && CBWebApp.data.productName != "Select Product") {
			// Now enable our buttons since we have a product
			$("#export-pdf-btn").prop("disabled",false);
			
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
	});		
	*/
	$( "#karThemes" ).change(function(e) {
		// Get our theme name and value
		CBWebApp.data.themeNames = $( "#karThemes option:selected" ).text();
		CBWebApp.data.themeValues = $( this ).val();
		CBWebApp.showData();
		if ( CBWebApp.data.productThemes !=  null  && CBWebApp.data.productName != "Select Themes") {		
			// Auto submit our form to get the report 
			/*$.ajax({
			    type:"POST",
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
			});	*/				
			
		} else {
	    	$("export-pdf-btn").prop("disabled",true);				
		}
		e.preventDefault();
	});	
	
	$( "#includeHistory" ).on('change', function(e) {
		CBWebApp.data.includeHistory = this.checked? true: false;
		CBWebApp.showData();
		
		
		/*if ( CBWebApp.data.productThemes !=  null  && CBWebApp.data.productName != "Select Themes") {		
			// Auto submit our form to get the report 
			/*$.ajax({
			    type:"POST",
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
			});	*/				
		/*	
		} else {
	    	$("export-pdf-btn").prop("disabled",true);				
		}*/
		e.preventDefault();
	});		
	
	CBWebApp.setStartDate();
	//$('#startDate').on('change', function(e){
	//	CBWebApp.data.startDate = $('#startDate').val();	
	//	CBWebApp.showData();
	//	e.preventDefault();
	//});

	CBWebApp.setEndDate();
	//$('#endDate').on('change', function(e){
	//	CBWebApp.data.endDate = $('#endDate').val();
	//	CBWebApp.showData();
	//	e.preventDefault();
	//});	
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
	
	$('.select-themes').multiselect({
		//enableFiltering: true,
		includeSelectAllOption: true,
        nonSelectedText: 'Select Themes',
        buttonWidth: '140px',
        disableIfEmpty: true,
        maxHeight: 150,
        nSelectedText: 'themes selected'
    });
};

// This is common to all reports
CBWebApp.disablePdfButton = function(){
	// pdf-export button is common to all reports but we can't use it untill 
	// we have a product so disable it here.
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
CBWebApp.showData = function(){
	alert( "Data Contains\n" 
			+ "Product Name: " + CBWebApp.data.productName + "\n Product Value: " 
			+ CBWebApp.data.productValue + "\n Theme Names: " + CBWebApp.data.themeNames 
			+ "\n Theme Values: " + CBWebApp.data.themeValues 
			+ "\n Include History: " + CBWebApp.data.includeHistory
			+ "\n Start Date:" + CBWebApp.data.startDate 
			+ "\n End Date: " + CBWebApp.data.endDate);	
}
