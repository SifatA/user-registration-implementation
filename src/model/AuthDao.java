package model;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthDao {
	private static Connection conn;
	private static PreparedStatement ps;
	private static ResultSet result;
	
	public static final String DB_URL = "jdbc:mysql://localhost:3306/lab03";
	public static final String DB_USER = "root";
	public static final String DB_PW = "50ftw4r3";
	public static final String DB_DRIVER = "com.mysql.jdbc.Driver";

	public static int checkUserPass(String username, String password){	//check username exists then check match
		int userId = -1;
		result = null;
		conn = null;
		try{
			Class.forName(DB_DRIVER);
		}
		catch(ClassNotFoundException ex){
			Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		if(!isUserNameAvailable(username)){
			try{
				conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
				String sql = "SELECT userId FROM user WHERE username LIKE ? and password LIKE ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, username);
				ps.setString(2, password);
				result = ps.executeQuery();
				if(result.next()) userId = result.getInt(1);
				else userId = 0;
				
				result.close();
				ps.close();
			}
			catch(SQLException ex){
				Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return userId;
	}
	public static User getUserById(int userId){		//user userId to find User
		User user = new User();
		result = null;
		conn = null;
		try{
			Class.forName(DB_DRIVER);
		}
		catch(ClassNotFoundException ex){
			Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		try{
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
			String sql = "SELECT user.userId, user_profile.firstName, user_profile.lastName, user.username "
					+ "FROM user, user_profile WHERE user.userId = user_profile.userId AND user.userId LIKE ?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			result = ps.executeQuery();
			if(result.next())
				user.setUserAtt(result.getInt(1), result.getString(2), result.getString(3), result.getString(4));

			result.close();
			ps.close();
		}
		catch(SQLException ex){
			Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		return user; 
	}
	public static int enterNewUser(String username, String password){	//new User returns UserId
		int userId = -1;
		result = null;
		conn = null;
		try{
			Class.forName(DB_DRIVER);
		}
		catch(ClassNotFoundException ex){
			Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		try{
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
			
			String sql = "INSERT INTO user(username, password) VALUES(?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1,username);
			ps.setString(2,password);
			ps.executeUpdate();
			
			sql = "SELECT userId FROM user WHERE username LIKE ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			result = ps.executeQuery();
			if(result.next()) userId = result.getInt(1);
			result.close();
			ps.close();
		}
		catch(SQLException ex){
			Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return userId;
	}
	public static boolean enterUserName(int userId, String firstName, String lastName){		//new User username
		boolean retCheck = false;
		result = null;
		conn = null;
		try{
			Class.forName(DB_DRIVER);
		}
		catch(ClassNotFoundException ex){
			Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		try{
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
			String sql = "INSERT INTO user_profile VALUES(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			if(ps.executeUpdate() != 0) retCheck = true;
			
			ps.close();
		}
		catch(SQLException ex){
			Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return retCheck;
	}
	public static boolean isUserNameAvailable(String username){		//username check
		conn = null;
		result = null;
		boolean available = true;
		try{
			Class.forName(DB_DRIVER);
		}
		catch(ClassNotFoundException ex){
			Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		try{
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
			String sql = "SELECT * FROM user WHERE   username LIKE ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,username);
			result = ps.executeQuery();
			if(result.next()) available = false;
		}
		catch(SQLException ex){
			Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return available;
	}
	public static void DB_Close() throws Throwable{			//close database
		try{
			if(conn!=null) conn.close();
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
}
