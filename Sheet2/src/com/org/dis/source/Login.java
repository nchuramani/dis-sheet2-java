/**
 * 
 */
package com.org.dis.source;
import com.org.dis.db2manage.*;
import java.sql.*;

/**
 * @author nikhilchuramani
 * 
 */
public class Login {
	
	/**
	 * Login class is used to authenticate the logged in Estate_Agent.
	 * The user enters the username and password in the Main.java class which is
	 * passed to the executeLogin method. 
	 * A database connection is made using the DBConManager class and the credentials
	 * of the user are verified. The result of the query is returned to the Main.java
	 * class.
	 */
	
	public Login() {
		// TODO Auto-generated constructor stub
	}
	
	public String executeLogin(String username, String password){
		/**
		 * executeLogin method is used to check for the credentials entered by the
		 * user to be present in the database.
		 */
		DBConManager con = new DBConManager();
		con.CreateConnection();
		String loggedin = null;
		try{
			/**
			 * Login credentials are checked in the Estate_Agent table and the 
			 * corresponding ID is returned.
			 */
			String sql;
			sql = "SELECT LOGIN FROM ESTATE_AGENT WHERE LOGIN = ? AND PASSWORD = ?";
			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
			pstm.setString(1, username);
			pstm.setString(2, password);
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){
				loggedin = rs.getString(1);		   
			}
			rs.close();
			pstm.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return loggedin;			
	}
}
