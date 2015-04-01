package model;

public class User{
	private int userId;
	private String firstName;
	private String lastName;
	private String username;
	
	public User(){
		this.userId = -1;
		this.firstName = "";
		this.lastName = "";
		this.username = "";
	}
	public User(int userId, String firstName, String lastName, String username){
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
	}
	public void setUserAtt(int userId, String firstName, String lastName, String username){
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
	}
	public String getUserAtt(String ret){
		if(ret.equals("firstName")) {
			//System.out.println("Prtnt " + this.firstName +"new " + firstName + ret);
			return this.firstName;
		}
		else if(ret.equals("lastName")) return this.lastName;
		else if(ret.equals("username")) return this.username;
		else return "Incorrect call.";
	}
	public int getUserId(){
		return this.userId;
	}
}
 