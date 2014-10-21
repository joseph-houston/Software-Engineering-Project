<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
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
	
	<script type="text/javascript">
	    $(document).ready(function () {
	    	$('.select-themes').multiselect({
	    		//enableFiltering: true,
	    		includeSelectAllOption: true,
	            nonSelectedText: 'Select Themes',
	            buttonWidth: '160px',
	            disableIfEmpty: true,
	            maxHeight: 150,
	            nSelectedText: 'themes selected'
	        });
	    	
	    	$('.input-group.date').datepicker({
	    		clearBtn: true,
	    		orientation: "top",
	    		autoclose: true
	    	});
	    	
	    	$(".enter-email").click(function(){
	    		$("#enter-email").modal('show');
	    	});	    	
	    	
	    });

	
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
					 	<div class="col-md-10">
							<form class="form-inline pull-right" role="form">
								<div class="form-group">
									<select  class="form-control" >
										<option>Select Product</option>
										<option>Product1</option>
										<option>Product2</option>
										<option>Product3</option>
									</select>
								</div>
								
								
							    <a href="#" class="btn btn-default enter-email">Schedule Email</a>
							    
							    <!-- Modal HTML -->
							    <div id="enter-email" class="modal fade">
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
													      <input class="form-control" type="email" placeholder="Enter email">
													    </div>
													  </div>							                    
							                    </form>
							                </div>
							                <div class="modal-footer">
							                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
							                    <button type="button" class="btn btn-success">Schedule Email</button>
							                </div>
							            </div>
							        </div>
							    </div>								
													  			  					  
							</form>	
					 	</div>
						<div class=col-mid-2">
							<a class="btn btn-default" href="<c:url value="" />">Export PDF</a>
						</div>
						<div class="clearfix"></div>	
						<hr> 
						<div class="report-data">
							${reportData }
						</div>  
					</c:when>
					
					<c:when test="${reportType == 'kanban-activity-report'}">
						<div class="col-md-10">
							<form class="form-inline pull-right" role="form">
								<div class="form-group">
									<select  class="form-control" >
										<option>Select Product</option>
										<option>Product1</option>
										<option>Product2</option>
										<option>Product3</option>
									</select>
								</div>					  

								<div class="form-group">
									<select multiple="multiple" placeholder="Select Themes" class="form-control select-themes" >
										<option value="1">Theme 1</option>
										<option value="2">Theme 2</option>
										<option value="3">Theme 3</option>
										<option value="4">Theme 4</option>
										<option value="5">Theme 5</option>										
									</select>								
								</div>
						  					  
								<div class="checkbox">
									<label>
									<input type="checkbox"> Include History
									</label>
								</div>
								  					  
								<div class="form-group">
									<div class="input-group small-control date" id="datepicker">
										<span class="input-group-addon">Start Date</span>
									    <input type="text" class="small-control form-control" name="start" placeholder="mm/dd/yyyy" />
									    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
									</div>
								</div>	
								
								<div class="form-group">
									<div class="input-group date" id="datepicker">
									    <span class="input-group-addon">End Date</span>
									    <input type="text" class="small-control form-control" name="end" placeholder="mm/dd/yyyy"/>
									    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
									</div>									
								</div>	
							</form>		    		
						</div>
						<div class=col-mid-2">
							<a class="btn btn-default" href="<c:url value="" />">Export PDF</a>
						</div>	
						<div class="clearfix"></div>	
						<hr> 
						<div class="report-data">
							${reportData }
						</div>  						   
					</c:when>
					
					<c:otherwise>
						<div class="col-md-10">
							<form class="form-inline pull-right" role="form">
								<div class="form-group">
									<select  class="form-control" >
										<option>Select Product</option>
										<option>Product1</option>
										<option>Product2</option>
										<option>Product3</option>
									</select>
								</div>
								  
								<div class="form-group">
									<select  class="form-control" >
										<option>Select User</option>
										<option>User 1</option>
										<option>USer 2</option>
										<option>User 3</option>
									</select>
								</div>
								  					  
								<div class="checkbox">
									<label>
									<input type="checkbox"> Include Details
									</label>
								</div>
								<div class="form-group">
									<div class="input-group date" id="datepicker">
										<span class="input-group-addon">Start Date</span>
									    <input type="text" class="small-control form-control" name="start" placeholder="mm/dd/yyyy" />
									    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
									</div>
								</div>	
								
								<div class="form-group">
									<div class="input-group date" id="datepicker">
									    <span class="input-group-addon">End Date</span>
									    <input type="text" class="small-control form-control" name="end" placeholder="mm/dd/yyyy"/>
									    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
									</div>									
								</div>		
		
							</form>		    		
						</div>
						
						<div class=col-mid-2">
							<a class="btn btn-default" href="<c:url value="" />">Export PDF</a>
						</div>	
						<div class="clearfix"></div>		
						<hr> 
						<div class="report-data">
							${reportData }
						</div>    	       
					</c:otherwise> 
				</c:choose> 
				           
	          </div>  
	             
        </div>
      </div>
    </div>    
    



	<!--  Defer javascript files for faster page loading -->

</body>
</html>
