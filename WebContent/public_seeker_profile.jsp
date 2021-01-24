<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="logic.presentation.bean.UserBean"
		 import="logic.presentation.bean.AccountBean"
		 import="logic.application.control.SeekerAccountControl"
		 import="logic.application.control.ManageAccountControl"
		 import="java.io.InputStream"
		 import="java.io.File"
		 import="java.io.FileOutputStream"
		 import="java.io.FileInputStream"
		 import="java.net.URL"
		 import="logic.exceptions.NoResultFoundException"%>
		 
<!DOCTYPE html>

<jsp:useBean id="userBean" class="logic.presentation.bean.UserBean" scope="session"/>
<jsp:setProperty name="userBean" property="*"/>

<jsp:useBean id="accountBean" class="logic.presentation.bean.AccountBean" scope="session"/>
<jsp:setProperty name="accountBean" property="*"/>

<jsp:useBean id="cvBean" class="logic.presentation.bean.CVBean" scope="session"/>
<jsp:setProperty name="cvBean" property="*"/>

<%Class.forName("com.mysql.cj.jdbc.Driver");%>

<%accountBean = ManageAccountControl.getInstance().retrieveAccount(accountBean.getId());%>

<html lang="en">
	<head>
		<meta charset="ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<link rel="icon" href="icons/main_icon.png">
	    <link href="css/style.css" rel="stylesheet">
	
		<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<title>WorldWideJob - <%=accountBean.getUser().getFirstName() + " " + accountBean.getUser().getLastName()%></title>
	</head>
	<body>
		<jsp:include page="WEB-INF/toolbar.jsp"/>
		<div id="main" style="height:680px;background-color:#8ecae6;border:1px solid blue">
			<form action="seeker_profile.jsp" name="seekerProfileform" method="POST">
				<button name="cv_file" id="cv_file" value="" style="display:none"></button>
				
	    		<div class="profile_pic" id="profile"></div>
	    		<%if(accountBean.getPic() != null){%>
	    			<script>
	    			var reader = new FileReader();
	    			reader.onloadend = function (e) {
	    				document.getElementById("profile").style.backgroundImage = "url(" + e.target.result + ")";
	    	      	};
	    		    reader.readAsDataURL(<%=accountBean.getPic()%>);
	    		    </script>
	    		<%}%>
	    		
				<div class="name">
	    			<h2><%=accountBean.getUser().getFirstName() + " " + accountBean.getUser().getLastName()%></h2>
	    			<h3>Job Seeker</h3>
	    		</div>
	    		
	    		<div id="container">
					<ul id="griglia">
						<li><button class="cv_btn" type="button" name="cv" value="">Curriculum Vitae</button></li>						
						<%if(request.getParameter("cv") != null){
							try {
								SeekerAccountControl.getInstance().retrieveCV(cvBean, accountBean.getId());
							} catch (NoResultFoundException re) {%>
								<script>noResultDialog()</script>
								<%return;
						 	}%>
						
							<script>resultDialog()</script>
							<%return;
						}%>
						<li><button class="id_btn" type="button" onClick="javascript:window.location='personalInfo.jsp';"><br>Personal Info</button></li>
					</ul>
				</div>
	    	</form>
	    </div>
	</body>
	<script src="js/toolbar.js"></script>
	<script src="js/files.js"></script>
</html>