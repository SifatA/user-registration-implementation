<%
ServletContext sc = getServletContext();
String path = sc.getContextPath();
%>
<ul id="navStl">
	<li><a href="<%=path %>/login.jsp">Login</a></li>
	<li><a href="<%=path %>/index.jsp">Home</a></li>
	<li><a href="<%=path %>/signup.jsp">Signup</a></li>	
</ul>

</body>
</html>