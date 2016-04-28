/**
 * 
 */
package com.org.dis.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.org.dis.db2manage.DBConManager;

/**
 * @author nikhilchuramani
 *
 */
public class AgentManagement {

	/**
	 * 
	 */
	private String name;
	private String address;
	private String login;
	private String password;
	private static String loggedinUser;
	
	public AgentManagement(String loggedin) {
		// TODO Auto-generated constructor stub
		AgentManagement.loggedinUser = loggedin;
		
	}

	public void agentMenu(){
		char choice = '0';
		System.out.println("-------------------------------------------------");
		System.out.println("------------AGENT MANAGEMENT MODULE--------------");
		System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
		System.out.println("-------------------------------------------------");
		System.out.println("Press 1 for Agent ID Creation");
		System.out.println("Press 2 for Update Agent Details");
		System.out.println("Press 3 for Delete an Agent");
		System.out.println("Press 4 to go back to Main Menu");

		try{
			 choice = (char) new BufferedReader(new InputStreamReader(System.in)).read();
		}catch(Exception e){
			e.printStackTrace();
		}
		if (choice == '1'){
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("------CREATE AGENT MANAGEMENT MODULE--------------");
			System.out.println("-------------------------------------------------");
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			
			try{
				System.out.println("Enter AGENT NAME:");
				this.setName(br.readLine());
				System.out.println("Enter AGENT ADDRESS:");
				this.setAddress(br.readLine());
				autoGenerate();	

			}
			catch(IOException io){
				io.printStackTrace();
			}
			createAgent();
		}
		else if(choice=='2'){
			char choiceupdate = '0';
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("-----UPDATE AGENT MANAGEMENT MODULE--------------");
			System.out.println("-------------------------------------------------");
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			
			try{
				System.out.println("Enter AGENT LOGIN TO VIEW DETAILS:");
				this.setLogin(br.readLine());
				
				readAgent();
				
				System.out.println("Name: "+ this.getName());
				System.out.println("Address: " + this.getAddress());
				System.out.println("Login: " + this.getLogin());
				System.out.println("Password: "+ this.getPassword());
				System.out.println("-------------------------------------------------");
				System.out.println("Press 1 for Edit Name");
				System.out.println("Press 2 for Edit Address");
				System.out.println("Press 3 for Edit Password");
				System.out.println("Note: Cannot edit LOGIN. Please contact DB Admin.");
				choiceupdate = (char) new BufferedReader(new InputStreamReader(System.in)).read();
				
				if(choiceupdate=='1'){
					System.out.println("Enter New Name: ");
					this.setName(br.readLine());
				}
				else if(choiceupdate =='2'){
					System.out.println("Enter New Address: ");
					this.setAddress(br.readLine());
				}
				else if(choiceupdate =='3'){
					System.out.println("Enter New Password: ");
					this.setPassword(br.readLine());
				}
				else{
					System.out.println("Invalid Choice! Try again!");
					agentMenu();
				}
				updateAgent();			
			}
			catch(IOException io){
				io.printStackTrace();
			}
			System.out.println("Agent details updated!");
			agentMenu();
		}

		else if(choice=='3'){
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("-----DELETE AGENT MANAGEMENT MODULE--------------");
			System.out.println("-------------------------------------------------");
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			
			try{
				System.out.println("Enter AGENT LOGIN TO DELETE:");
				this.setLogin(br.readLine());
				
				readAgent();
				
				System.out.println("CONFIRM AGENT DELETE (Y/N):");
				char choicedelete = (char) new BufferedReader(new InputStreamReader(System.in)).read();
				if(choicedelete == 'Y'){
					deleteAgent();
				}
				else if(choicedelete=='N'){
					System.out.println("Agent not deleted!");
					agentMenu();
				}
				else{
					System.out.println("Invalid Choice! Try Again!");
					agentMenu();
				}
			}
			catch(IOException io){
				io.printStackTrace();
			}
			System.out.println("Agent" + this.getLogin() + "deleted!");
			agentMenu();
		}
		else if(choice=='4'){
			Main.menuDisplay();
		}
		else{
			System.out.println("Invalid choice! Chose again!");
			agentMenu();
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getloggedinUser() {
		return loggedinUser;
	}
	public void setloggedinUser(String loggedinUser) {
		AgentManagement.loggedinUser = loggedinUser;
	}
	
	public void createAgent(){
		DBConManager con = new DBConManager();
		con.CreateConnection();
		String sql;
		PreparedStatement pstm;
		try{
			/**
			 * Login credentials are checked in the Estate_Agent table and the 
			 * corresponding ID is returned.
			 */
			sql = "INSERT INTO ESTATE_AGENT VALUES (?,?,?,?)";
			pstm = con.getConnection().prepareStatement(sql);
			pstm.setString(1, this.getName());
			pstm.setString(2, this.getAddress());
			pstm.setString(3, this.getLogin());
			pstm.setString(4, this.getPassword());

			
			pstm.executeUpdate();
			
			System.out.println("Agent " + this.getLogin() + " added.");
			
			pstm.close();
			con.getConnection().commit();
			con.closeConnection();;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		agentMenu();
	}
	public void autoGenerate(){
		DBConManager con = new DBConManager();
		con.CreateConnection();
		String loginid = null;
		try{
			/**
			 * Autogenerate is used to get the max login from Estate_Agent and then increment it by 1. Also it generates
			 * password for the user.
			 */
			String sql;
			sql = "SELECT MAX(LOGIN) FROM ESTATE_AGENT";
			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){
				loginid = rs.getString(1);		   
			}
			rs.close();
			pstm.close();
			con.closeConnection();;
		}
		catch(Exception e){
			e.printStackTrace();	
		}
		String regex = "(?<=[\\w&&\\D])(?=\\d)";
		String[] alphanumeric = loginid.split(regex);
		int numeric = Integer.parseInt(alphanumeric[1]);
		numeric = numeric+1;
		loginid = alphanumeric[0].concat(String.format("%03d", numeric));
		this.setLogin(loginid);
		this.setPassword("AGENT".concat(loginid));
	}
	
	public void readAgent(){
		DBConManager con = new DBConManager();
		con.CreateConnection();
		try{
			/**
			 * Read agent is used read the details of the user requested. 
			 */
			String sql;
			sql = "SELECT NAME, ADDR, LOGIN, PASSWORD FROM ESTATE_AGENT WHERE LOGIN =?";
			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
			pstm.setString(1, this.getLogin());
			
			ResultSet rs = pstm.executeQuery();
			if(!rs.next()){
				System.out.println("Invalid Login! Try again!");
				agentMenu();
			}
			while(rs.next()){
				System.out.println(rs.getString(1));
				this.setName(rs.getString(1));
				this.setAddress(rs.getString(2));
				this.setLogin(rs.getString(3));
				this.setPassword(rs.getString(4));
			}
			rs.close();
			pstm.close();
			con.closeConnection();;
		}
		catch(Exception e){
			e.printStackTrace();	
		}
	}
	public void updateAgent(){
		DBConManager con = new DBConManager();
		con.CreateConnection();
		try{
			/**
			 * Read agent is used read the details of the user requested. 
			 */
			String sql;
			sql = "UPDATE ESTATE_AGENT SET NAME=?, ADDR=?, PASSWORD=? WHERE LOGIN=?";
			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
			pstm.setString(1, this.getName());
			pstm.setString(2, this.getAddress());
			pstm.setString(3, this.getPassword());
			pstm.setString(4, this.getLogin());
			
			pstm.executeUpdate();
			pstm.close();
			con.getConnection().commit();
			con.closeConnection();;
		}
		catch(Exception e){
			e.printStackTrace();	
		}
	}
	public void deleteAgent(){
		DBConManager con = new DBConManager();
		con.CreateConnection();
		try{
			/**
			 * Read agent is used read the details of the user requested. 
			 */
			String sql;
			sql = "DELETE FROM ESTATE_AGENT WHERE LOGIN = ? ";
			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
			pstm.setString(1, this.getLogin());
			
			pstm.executeUpdate();
			pstm.close();
			con.getConnection().commit();
			con.closeConnection();;
		}
		catch(Exception e){
			e.printStackTrace();	
		}
	}
}
