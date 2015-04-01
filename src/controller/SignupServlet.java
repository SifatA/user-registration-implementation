package controller;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AuthDao;
import model.User;

public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*---------------declaration and initialisation--------------*/
		String loggedIn = "false";
		String url = "/signup.jsp";
		String msg = (String)request.getParameter("msg"); if(msg == null) msg = "";
		String check_user = (String)request.getParameter("check_user"); if(check_user == null) check_user = "";
		String signup_user = (String)request.getParameter("signup_user"); if(signup_user == null) signup_user = "";
		String firstName = (String)request.getParameter("firstName"); if(firstName == null) firstName = "";
		String lastName = (String)request.getParameter("lastName"); if(lastName == null) lastName= "";
		String username = (String)request.getParameter("username"); if(username == null) username = "";
		String password = (String)request.getParameter("password"); if(password == null) password = "";
		String reEnterPassword = (String)request.getParameter("reenterpass"); if(reEnterPassword == null) reEnterPassword = "";
		
		if(check_user.length() != 0){
			if(username.length() != 0){
				if(AuthDao.isUserNameAvailable(username)){
					msg = "<p id=\"success\">You're in luck! Selected username is available.</p>";
				}
				else msg = "<p id=\"failure\">Sorry! Username is unavailable.</p>";
			}
			else{
				msg = "<p id=\"failure\">Please type a username to continue.</p>";
			}
		} else if (signup_user.length() != 0){
			/*-------------------check empty fields------------------------*/
			if(username.length()==0)
				msg = "<p id=\"failure\">Fields cannot be empty! Enter Username.</p>";
			else if(firstName.length()==0)
				msg = "<p id=\"failure\">Fields cannot be empty! Enter First Name.</p>";
			else if(lastName.length()==0)
				msg = "<p id=\"failure\">Fields cannot be empty! Enter Last Name.</p>";
			else if(password.length()==0)
				msg = "<p id=\"failure\">Fields cannot be empty! Enter Password.</p>";
			else if(reEnterPassword.length()==0)
				msg = "<p id=\"failure\">You must re-enter Password as confirmation. Try again.</p>";
			/*-------------------username availability----------------------*/
			else if (!(AuthDao.isUserNameAvailable(username)))
				msg = "<p id=\"failure\">Sorry! Username is unavailable.</p>";
			/*-------------------password match------------------------*/
			else if(!(password.equals(reEnterPassword)))
				msg = "<p id=\"failure\">Passwords don't match!</p>";
			/*-------------------all fields accepted------------------------*/
			else{
				User user = new User();
				int userId = AuthDao.enterNewUser(username, password);
				/*-------------------succesful registration--------------------*/
				if(AuthDao.enterUserName(userId, firstName, lastName)){
					user = AuthDao.getUserById(userId);
					HttpSession session = request.getSession();
					loggedIn = "true";
					url = "/index.jsp";
					msg = "<p id=\"success\">Signup Successful! Welcome to your page, " +
							user.getUserAtt("firstName") + " " + user.getUserAtt("lastName") + "!</p>";
					session.setAttribute("user", user);
					session.setAttribute("firstName", (String)user.getUserAtt("firstName"));
					session.setAttribute("loggedIn", loggedIn);
				} else{	/*-------------------some error------------------------*/
					msg = "<p id=\"failure\">Signup Error! Try again, please.</p>";
				}
			}
		}
		
		/*---------------------usability enhancement----------------------*/
		request.setAttribute("username", username);
		request.setAttribute("firstName", firstName);
		request.setAttribute("lastName", lastName);
		
		/*-------------------registration results------------------------*/
		request.setAttribute("msg", msg);
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);  
        dispatcher.include(request,response);  
        dispatcher.forward(request, response);
	}
}