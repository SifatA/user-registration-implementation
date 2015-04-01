<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
<link media="all" rel="stylesheet" type="text/css" href="lab03.css"/>
</head>
<body>
	<%
	String logInStatus = (String)session.getAttribute("loggedIn");
	String msg = (String)request.getAttribute("msg");
	if(msg == null) msg = "<p id=\"success\">Please log in! Or register for an account.</p>";
	if(logInStatus == null) logInStatus = "false";
	else if (logInStatus.equals("true")){
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
	<img width = "50%" src  = "http://img.photobucket.com/albums/v116/Jernau/Leaf-900-Transparent.png"/>
	</div>
	<div id="outer_div_right">
		<h1>root-wraith's repository</h1>
		<%@include file = "nav.jsp"%><br/>
		<h1>User Login</h1>
		<hr color = "#000000" size = "3"/>
		<%= msg %>
		<hr color = "#000000" size = "3"/>
		<div id="inner_top_div">
				<h2>Signin</h2>
		</div>
		<div id="inner_bottom_div">
			<form action="LoginServlet" method="post">
				<br/><input type="text" name="username" autocomplete="off" placeholder="Username">
				<br/><input type="password" name="password" autocomplete="off" placeholder="Password">
				<br/><input type="submit" value="Submit">
			</form>
		</div>
	</div>
	</div>
	</center>
</body>
</html>