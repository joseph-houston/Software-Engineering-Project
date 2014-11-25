<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${title }</title>
	<link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.min.css" />" rel="stylesheet"  type="text/css" />		
	<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet"  type="text/css" />
	
	<script src="<c:url value="/resources/vendor/jquery/jquery.js" />"></script>
	<script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.min.js" />"></script>
	
	<!-- Include bootstrap-multiselect plugin's CSS and JS: -->
	<script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap-multiselect.js" />"></script>
	<link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap-multiselect.css" />" rel="stylesheet"  type="text/css" />
	
	<!--  Include  bootstrap-datepicker plugin's CSS and JS -->	
	<script src="<c:url value="/resources/vendor/bootstrap-datepicker/js/bootstrap-datepicker.js" />"></script>
	<link href="<c:url value="/resources/vendor/bootstrap-datepicker/css/datepicker.css" />" rel="stylesheet"  type="text/css" />
	
	<!-- Include our application  -->
	<script src="<c:url value="/resources/js/CBWebApp.js" />"></script>		
		

	<c:url var="findThemesForProductUrl" value="/themes" />
	<c:url var="findUsersForProductUrl" value="/users" />
	<c:url var="findProductsUrl" value="/products" />
	<c:url var="findAllProductsUrl" value="/allProducts" />
	<c:url var="findKanbanProductsUrl" value="/kanbanProducts" />
	<c:url var="scheduleEmailUrl" value="/scheduleEmail" />	
	<c:url var="kanbanWorkflowWarningsReportUrl" value="/getKanbanWorkflowWarningsReport" />
	<c:url var="kanbanActivityReportUrl" value="/getKanbanActivityReport" />		
	<c:url var="userActivityReportUrl" value="/getUserActivityReport" />
					
	<script type="text/javascript">
	/*
	$(document).ready(function() { 
		$('#products').change(
				function() {
					$.getJSON('${findThemesForProductUrl }', {
						productName : $(this).val(),
						ajax : 'true'
					}, function(data) {
						var html = '';
						var len = data.length;
						for ( var i = 0; i < len; i++) {
							html += '<option value="' + data[i].name + '">'
									+ data[i].name + '</option>';
						}
						html += '</option>';
	
						$('#karThemes').html(html);
					}).complete(function(){
						$('.select-themes').multiselect('rebuild') 
					});
					
				});
		
	});
	*/
	</script>		

	<script type="text/javascript">

	/*
	$(document).ready(function() { 
		$('#export-pdf-btn').click(function(e){
			var url = window.location;
			console.log(url);
			var lastChar = url[url.length - 1];
			//if(lastChar != "/"){
			//	url += "/"
			//}
			url += "report.pdf";
			url+="?name=test&id=3";
			window.open(url);
			e.preventDefault();
		});	
	});
	*/
	</script>	
	
	<script type="text/javascript">
	/*
	$(document).ready(function() { 
		$('#products').change(
				function() {
					$.getJSON('${findUsersForProductUrl }', {
						productName : $(this).val(),
						ajax : 'true'
					}, function(data) {
						var html = '<option value="">Select User</option>';
						var len = data.length;
						for ( var i = 0; i < len; i++) {
							html += '<option value="' + data[i].name + '">'
									+ data[i].name + '</option>';
						}
						html += '</option>';
	
						$('#karUsers').html(html);
					});
					
				});
		
	});
	*/
	</script>		
	
	<script type="text/javascript">
	$(document).ready(
			function() {
				$.getJSON('${findKanbanProductsUrl }', {
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
				
			});	
	/*
	$(document).ready(
			function() {
				$.getJSON('${findAllProductsUrl }', {
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
				
			});
	*/
	// This is for getting the report data
	/*
	$(document).ready(
			function() {
				$.getJSON('${findAllProductsUrl }', {
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
					              
				})
				
			});		
	*/
	
	</script>
</head>
<body>
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
		  <a class="navbar-brand" href="<c:url value="/" />">Commerce Bank</a>
        </div>
        <div class="navbar-collapse collapse">
			<div class="navbar-brand">
				<div class="report-title "> ${title } </div>
			</div>
        </div>
      </div>
    </div>
    
    <div class="container-fluid">
      <div class="row">
      
        <div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar">
            <li><a href="<c:url value="/" />">Kanban Workflow Warnings</a></li>
            <li><a href="<c:url value="/kanbanActivityReport" />">Kanban Activity Report</a></li>
            <li><a href="<c:url value="/userActivityReport" />">User Activity Report</a></li>

          </ul>
        </div>
        
        <div class="col-md-10 col-md-offset-2 main">
	        <div class="row controls">
		       <c:choose>
					<c:when test="${reportType == 'kanban-workflow-warnings'}">				
					 	<div class="col-md-12">
							<form id="kanban-workflow-warnings" class="form-inline pull-right" role="form">
								<div class="form-group">
									<select id="products" class="form-control" >
									</select>
 								</div>
							
								
								<div class="form-group custom-group">
									<button id="schedule-email-popup" href="#" class="btn btn-default enter-email">Schedule Email</button>
								</div>
							    
							    <div class="form-group custom-group">
							    	<button id="export-pdf-btn" class="btn btn-default>">Export PDF</button>	
							    </div>											  			  					  
							</form>
							
						    <!-- Modal HTML -->
						    <div id="email-popup" class="modal fade">
						        <div class="modal-dialog">
						            <div class="modal-content">
						                <div class="modal-header">
						                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						                    <h4 class="modal-title">Schedule Emails For This Report</h4>
						                </div>
						                <div class="modal-body">
						                    <p>Enter your email to receive a nightly email of this report.</p>
						                    <form id="schedule-email-form" method="get">
						                    	 <div class="form-group">
												    <div class="input-group">
												      <div class="input-group-addon">Email </div>
												      <input id="email" class="form-control" type="email" placeholder="Enter email">
												    </div>
												  </div>							                    
						                    </form>
						                </div>
						                <div class="modal-footer">
						                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
						                    <button type="button" class="btn btn-success" id="schedule-email">Schedule Email</button>
						                </div>
						            </div>
						        </div>
						    </div>						
					 	</div>
						<div class="clearfix"></div>	
						<hr> 
						<div class="report-data">
							${reportData }
							<div id="data">
								
							</div>
						</div>  
						
						<script type="text/javascript">
							$(document).ready(function () {
								CBWebApp.init();
								CBWebApp.processKanbanWorkflowWarnings();
								
								// Get the scheduling information for this report
								$('#schedule-email').click(function(){
									var email = $('#email').val();
									var product = CBWebApp.data.productValue;
									
									// Post the scheduling data
									$.ajax({
										type: "POST",
										url: "${scheduleEmailUrl }",
										data: "product=" + product + "&email=" + email,
										success: function(response) {
											console.log("Success: " + response);
										}, 
										error: function(e) {
											console.log("Error: " + e)
										}
									});
								});
								
								$('#export-pdf-btn').click(function(e){
									var url = window.location;
									url += "kanbanWorkflowWarningsReport.pdf";
									url+="?productName=" + CBWebApp.data.productValue;
									window.open(url);
									e.preventDefault();
								});									
							});						
						</script>
					</c:when>
					
					<c:when test="${reportType == 'kanban-activity-report'}">
						<div class="col-md-12">
							<form id="kanban-activity-report" class="form-inline pull-right" role="form">
								<div class="form-group">
									<select id="products" class="form-control" >
									</select>
								</div>					  

								<div id="themes" class="form-group">
									<select id="karThemes" multiple="multiple" placeholder="Select Themes" class="form-control select-themes" >							
									</select>								
								</div>
						  					  
								<div class="checkbox">
									<label>
									<input id="includeHistory" type="checkbox"> Include History
									</label>
								</div>
								  					  
								<div class="form-group custom-group">
									<div class="input-group date" id="datepicker">
										<span class="input-group-addon">Start Date</span>
									    <input id="startDate" type="text" class="small-control form-control" name="start" placeholder="mm/dd/yyyy" />
									    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
									</div>
								</div>	
								
								<div class="form-group custom-group">
									<div class="input-group date" id="datepicker">
									    <span class="input-group-addon">End Date</span>
									    <input id="endDate" type="text" class="small-control form-control" name="end" placeholder="mm/dd/yyyy"/>
									    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
									</div>									
								</div>	
							
							    <div class="form-group custom-group">
							    	<button id="export-pdf-btn" class="btn btn-default>" type="button">Export PDF</button>	
							    </div>										
							</form>		    		
						</div>

						<div class="clearfix"></div>	
						<hr> 
						<div class="report-data">
							${reportData }

						
						<script type="text/javascript">
							$(document).ready(function () {
								CBWebApp.init();
								CBWebApp.dateUtility();
								$("#includeHistory").prop("disabled", true);
								CBWebApp.data.includeHistory= false;
								
								$( "#products" ).change(function(e) {
									// Get our product name and store it to our global
									CBWebApp.data.productName = $( "#products option:selected" ).text();
									CBWebApp.data.productValue = $( this ).val();
									CBWebApp.data.themeNames = null;
									CBWebApp.data.themeValues = null;
									
									CBWebApp.showDebugInfo();
									
									if ( CBWebApp.data.productValue!=  null  && CBWebApp.data.productValue != "") {
										// Now enable our buttons since we have a product
										//$("#includeHistory").prop("disabled",false);
										
										// Get themes for this product
										$.getJSON('${findThemesForProductUrl }', {
											productName : CBWebApp.data.productValue,
											ajax : 'true'
										}, function(data) {
											var html = '';
											var len = data.length;
											for ( var i = 0; i < len; i++) {
												html += '<option value="' + data[i].name + '">'
														+ data[i].name + '</option>';
											}
											html += '</option>';
						
											$('#karThemes').html(html);
										}).complete(function(){
											$('.select-themes').multiselect('rebuild'); 
										});
	
										// Auto submit our form to get the report 
										/*
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
										*/
									} else {
										// if no active product, then these elements are inactive
								    	$("#export-pdf-btn").prop("disabled",true);
								    	$("#includeHistory").prop("disabled", true);
								    	CBWebApp.data.includeHistory = false;
								    	
								    	// set katThemes dropdown html to empty and rebuild the dropdown
								    	$('#karThemes').html("");
								    	$('.select-themes').multiselect('rebuild'); 
									}
									e.preventDefault();
								});								
								
								$( "#karThemes" ).change(function(e) {
									// Get our theme name and value
									CBWebApp.data.themeNames = $( "#karThemes option:selected" ).text();
									CBWebApp.data.themeValues = $( this ).val();
									CBWebApp.showDebugInfo();
									if ( CBWebApp.data.themeValues !=  null) {
										$("#export-pdf-btn").prop("disabled",false);
										$("#includeHistory").prop("disabled", false);
										CBWebApp.showDebugInfo();
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
								    	$("#export-pdf-btn").prop("disabled",true);	
								    	CBWebApp.data.includeHistory = false
								    	$("#includeHistory").prop("disabled", true);
								    	$("#includeHistory").checked = false;
									}
									e.preventDefault();
								});	

								$( "#includeHistory" ).on('change', function(e) {
									CBWebApp.data.includeHistory = this.checked? true: false;
									CBWebApp.showDebugInfo();
									
									
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
								
								
								//CBWebApp.processKanbanActivityReport('${findThemesForProductUrl }');
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
										    type:"GET",
										    data:CBWebApp.data,
										    url:"<c:url value="/kanbanActivityReport" />",
										    async: false,
										    //dataType: "json",
										    success: function(response){
										       //alert("success");
										       //$("#data").html(response);
										       var o = new Option(response);
												/// jquerify the DOM object 'o' so we can use the html method
												$(o).html("option text");
										       $("#karThemes").append(o);
										       alert(new Option(response));
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
							});					
						</script>								   
					</c:when>
					
					<c:otherwise>
						<div class="col-md-12">
							<form class="form-inline pull-right" role="form">
								<div class="form-group">
									<select id='products'  class="form-control" >
									</select>
								</div>
								  
								<div class="form-group">
									<select id='karUsers' placeholder="Select Users" class="form-control" >
										<option value="">Select User</option>
									</select>
								</div>
								  					  
								<div class="checkbox">
									<label>
									<input type="checkbox"> Include Details
									</label>
								</div>
								<div class="form-group custom-group">
									<div class="input-group date" id="datepicker">
										<span class="input-group-addon">Start Date</span>
									    <input type="text" class="small-control form-control" name="start" placeholder="mm/dd/yyyy" />
									    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
									</div>
								</div>	
								
								<div class="form-group custom-group">
									<div class="input-group date" id="datepicker">
									    <span class="input-group-addon">End Date</span>
									    <input type="text" class="small-control form-control" name="end" placeholder="mm/dd/yyyy"/>
									    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
									</div>									
								</div>		
								
							    <div class="form-group custom-group">
							    	<button id="export-pdf-btn" class="btn btn-default>" type="button">Export PDF</button>	
							    </div>					
							</form>		    		
						</div>
						<div class="clearfix"></div>		
						<hr> 
						<div class="report-data">
							${reportData }
						</div> 
						
						<script type="text/javascript"> 
							$(document).ready(function () {
								CBWebApp.init();

								$.getJSON('${findAllProductsUrl }', {
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
									              
								});
									
							
								
								
								//CBWebApp.processKanbanUserActivity();
															
							});
						</script>						   	       
					</c:otherwise> 
				</c:choose> 
				           
	          </div>  
	             
        </div>
      </div>
    </div>    
    



	<!--  Defer javascript files for faster page loading -->

</body>
</html>
