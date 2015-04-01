<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
<link media="all" rel="stylesheet" type="text/css" href="lab03.css"/>
</head>
<body>
	<%	
	String name = (String)session.getAttribute("firstName"); if(name == null) name = "";
	String logInStatus = (String)session.getAttribute("loggedIn"); if(logInStatus == null) logInStatus = "false";
	String msg = (String)request.getAttribute("msg");
	if(logInStatus.equals("true") && msg == null) msg = "<p id=\"success\">Hey, " + name + ". How is?</p>";
	if(msg == null){ 
		msg = "<p id=\"failure\">You have not logged in yet! Please login or register for an account.</p>";
	}
	%>
	<center>
	<div>
	<div id="outer_div_left">
	<img width = "50%" src  = "http://img.photobucket.com/albums/v116/Jernau/Leaf-900-Transparent.png"/>
	</div>
	<div id="outer_div_right">
		<h1>root-wraith's repository</h1>
		<%@include file = "nav.jsp"%><br/>
		<h1>User Home</h1>
		
		<hr color = "#000000" size = "3"/>
		<%= msg %>
		<hr color = "#000000" size = "3"/>	
		<%if(logInStatus == "true"){%><%@include file = "logout.jsp"%><%}%>
	</div>
	</div>
	</center>
</body>
</html>