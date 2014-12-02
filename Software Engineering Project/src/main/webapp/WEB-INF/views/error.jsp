

<body>
    <center>
        <h2>Sorry, the email was not sent because of the following error:</h2>
        <h3>${exception.message}</h3>
    </center>
</body>
</html>

<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Error</title>
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
			    <center>
			        <h2>Sorry, your request could not be processed because of the following error:</h2>
			        <h3>${exception.message}</h3>
			    </center>
				           
	        </div>  
	           
        </div>
      </div>
    </div>    
    



	<!--  Defer javascript files for faster page loading -->

</body>
</html>