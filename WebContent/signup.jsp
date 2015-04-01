<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Signup Page</title>
<link media="all" rel="stylesheet" type="text/css" href="lab03.css"/>
</head>
<body>
	<% 
	String logInStatus = (String)session.getAttribute("loggedIn"); if(logInStatus == null) logInStatus = "";
	String msg = (String)request.getAttribute("msg"); if(msg == null) msg = "";
	String username = (String)request.getAttribute("username"); if(username == null) username = "";
	String firstName = (String)request.getAttribute("firstName"); if(firstName == null) firstName = "";
	String lastName = (String)request.getAttribute("lastName"); if(lastName == null) lastName = "";
	
	if(logInStatus.length() == 0 && msg.length() == 0) msg = "<p id=\"success\">You will soon be \"one of us\". =]</p>";
	else if(logInStatus.equals("true")){
		msg = "<p id=\"success\">You are already logged in!</p>";
		request.setAttribute("msg", msg);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.include(request,response);
		dispatcher.forward(request, response);
	}
	%>
	
	<center>
	<div>
	<div id="outer_div_left">
	<img width="50%" src  = "http://img.photobucket.com/albums/v116/Jernau/Leaf-900-Transparent.png"/>
	</div>
	<div id="outer_div_right">
		<h1>root-wraith's repository</h1>
		<%@include file = "nav.jsp"%><br/>
		<h1>User Registration</h1>
		<hr color = "#000000" size = "3"/>
		<%= msg %>
		<hr color = "#000000" size = "3"/>
		<div id="inner_top_div">
			<h2>Registration Form</h2>
		</div>		
		<div id="inner_bottom_div">
		<form action="SignupServlet" method="post">
			<br/><input type="text" name="username" autocomplete="off" placeholder="Username" value="<%=username%>">
			<input id="clicked" name="check_user" type="submit" value = "&#x2714">
			<br/><input type="text" name="firstName" autocomplete="off" placeholder="First Name" value="<%=firstName%>">
			<br/><input type="text" name="lastName" autocomplete="off" placeholder="Last Name" value="<%=lastName%>">
			<br/><input type="password" name="password" autocomplete="off" placeholder="Password">
			<br/><input type="password" name="reenterpass" autocomplete="off" placeholder="Re-enter Password">
			<br/><input type="submit" name="signup_user" value="Register"><input type="reset" value="Reset">
		</form>
		</div>
	</div>
	</div>
	</center>
</body>
</html>