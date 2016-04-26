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
	
	public AgentManagement() {
		// TODO Auto-generated constructor stub
	}

	public void agentMenu(){
		char choice = '0';
		System.out.println("-------------------------------------------------");
		System.out.println("------------AGENT MANAGEMENT MODULE--------------");
		System.out.println("------------WELCOME " + this.getLogin() + "------------------------");
		System.out.println("-------------------------------------------------");
		System.out.println("Press 1 for Agent ID Creation");
		System.out.println("Press 2 for Update Agent Details");
		System.out.println("Press 3 for Delete and Agent");
		try{
			 choice = (char) new BufferedReader(new InputStreamReader(System.in)).read();
		}catch(Exception e){
			e.printStackTrace();
		}
		if (choice == '1'){
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getLogin() + "------------------------");
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
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getLogin() + "------------------------");
			System.out.println("-----UPDATE AGENT MANAGEMENT MODULE--------------");
			System.out.println("-------------------------------------------------");
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			
			try{
				System.out.println("Enter AGENT NAME:");
				this.setName(br.readLine());
				System.out.println("Enter AGENT ADDRESS:");
				this.setAddress(br.readLine());
				System.out.println("Enter AGENT LOGIN:");
				this.setLogin(br.readLine());
				System.out.println("Enter AGENT PASSWORD:");
				this.setPassword(br.readLine());
			}
			catch(IOException io){
				io.printStackTrace();
			}
			createAgent();		}
		else if(choice=='3'){
			System.out.println("Call Delete");
		}
		else{
			System.out.println("Invalid choice! Chose again!");
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
	
	public void createAgent(){
		DBConManager con = new DBConManager();
		con.CreateConnection();
		try{
			/**
			 * Login credentials are checked in the Estate_Agent table and the 
			 * corresponding ID is returned.
			 */
			String sql;
			sql = "INSERT INTO ESTATE_AGENT VALUES (?,?,?,?)";
			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
			pstm.setString(1, this.getName());
			pstm.setString(2, this.getAddress());
			pstm.setString(3, this.getLogin());
			pstm.setString(4, this.getPassword());

			
			pstm.executeUpdate();
			
			System.out.println("Agent" + this.getLogin() + "added.");
			
			pstm.close();
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
			 * Login credentials are checked in the Estate_Agent table and the 
			 * corresponding ID is returned.
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
}
