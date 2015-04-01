package controller;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

import model.AuthDao;
import model.User;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*---------declaration and initialisation---------*/
		String loggedIn = "false";
		String url = "/login.jsp";
		String msg = (String)request.getParameter("msg"); if(msg == null) msg = "";
		String name = (String)request.getParameter("username"); if(name == null) name = "";
		String pass = (String)request.getParameter("password"); if(pass == null) pass = "";
		
		/*--------------------field check------------------*/
		if((name.length()!=0) && (pass.length()!=0)){
			/*---------check username with password------------*/
			int userId = AuthDao.checkUserPass(name, pass);
			if(userId == -1)
				msg = "<p id=\"failure\">Username not found! Please register for an account.</p>";
			else if (userId == 0)
				msg = "<p id=\"failure\">Username and password combination error! Please try again.</p>";
			else{
				HttpSession session = request.getSession();
				User user = new User();
				user = AuthDao.getUserById(userId);
				msg = "<p id=\"success\">Welcome to your page, " + (String)user.getUserAtt("firstName") +
						" " + (String)user.getUserAtt("lastName") + "!</p>";
				loggedIn = "true";
				url = "/index.jsp";
				session.setAttribute("user", user);
				session.setAttribute("firstName", (String)user.getUserAtt("firstName"));
				session.setAttribute("loggedIn", loggedIn);
			}
			
		} else{		/*-------------check for empty fields-------------*/
			if((name.length()==0) && (pass.length()!=0))
				msg = "<p id=\"failure\">Sorry! Username field empty.</p>";
			else if((name.length()!=0) && (pass.length()==0))
				msg = "<p id=\"failure\">Sorry! Password field empty.</p>";
			else if((name.length()==0) && (pass.length()==0))
				msg = "<p id=\"failure\">Sorry! Both fields empty.</p>";			
		}
		
		request.setAttribute("msg", msg);
		try {
			AuthDao.DB_Close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		/*-------------forward parameters---------------*/
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);  
        dispatcher.include(request,response);  
        dispatcher.forward(request, response);
	}
}