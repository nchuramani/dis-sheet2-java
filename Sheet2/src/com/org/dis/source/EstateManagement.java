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
public class EstateManagement{

	private String estate_id;
	private String city;
	private String postcode;
	private String street;
	private int street_no;
	private float sq_area;
	private String agent_login;
	private static String loggedinUser;
	private int floors;
	private double price;
	private char garden;
	private int aFloor;
	private double rent;
	private int rooms;
	private char balcony;
	private char kitchen;
	private char estate_type;
	
	public EstateManagement(String loggedin) {
		// TODO Auto-generated constructor stub
		EstateManagement.loggedinUser = loggedin;
	}
	
	public void estateMenu(){
		char choice = '0';
		System.out.println("-------------------------------------------------");
		System.out.println("------------ESTATE MANAGEMENT MODULE--------------");
		System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
		System.out.println("-------------------------------------------------");
		System.out.println("Press 1 for Estate Creation");
		System.out.println("Press 2 for Update Estate Details");
		System.out.println("Press 3 for Delete an Estate");
		System.out.println("Press 4 to go back to Main Menu");

		try{
			 choice = (char) new BufferedReader(new InputStreamReader(System.in)).read();
		}catch(Exception e){
			e.printStackTrace();
		}
		if (choice == '1'){
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("------CREATE ESTATE MANAGEMENT MODULE------------");
			System.out.println("-------------------------------------------------");
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			
			try{
				System.out.println("Enter STREET NUMBER:");
				this.setStreet_no(Integer.parseInt(br.readLine()));
				System.out.println("Enter STREET:");
				this.setStreet(br.readLine());
				System.out.println("Enter CITY:");
				this.setCity(br.readLine());
				System.out.println("Enter POSTAL CODE:");
				this.setPostcode(br.readLine());
				System.out.println("Enter Sq. AREA:");
				this.setSq_area(Float.parseFloat(br.readLine()));
				this.setAgent_login(this.getloggedinUser());
				System.out.println("Estate Type House (H) of Apartment (A):");
				char h_a = (char) new BufferedReader(new InputStreamReader(System.in)).read();
				this.setEstate_type(h_a);
				if(this.getEstate_type()=='H'){
					System.out.println("Enter number of floors for House:");
					this.setFloors(Integer.parseInt(br.readLine()));
					System.out.println("Enter price for House:");
					this.setPrice(Double.parseDouble(br.readLine()));
					System.out.println("Garder (Y/N):");
					this.setGarden((char) br.read());					
				}
				else if(this.getEstate_type()=='A'){
					System.out.println("Enter floor number for Apartment:");
					this.setaFloor(Integer.parseInt(br.readLine()));
					System.out.println("Enter Rent for Apartment:");
					this.setRent(Double.parseDouble(br.readLine()));
					System.out.println("Enter number of rooms for Apartment:");
					this.setRooms(Integer.parseInt(br.readLine()));	
					System.out.println("Balcony (Y/N):");
					this.setBalcony((char) br.read());	
					System.out.println("Kitchen (Y/N):");
					this.setKitchen((char) br.read());	
				}
				else{
					System.out.println("Invalid Choice! Try again!");
					estateMenu();
				}
				autoGenerate();	
			}
			catch(IOException io){
				io.printStackTrace();
			}
			createEstate();
		}
		else if(choice=='2'){
			char choiceupdate = '0';
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("-----UPDATE ESTATE MANAGEMENT MODULE-------------");
			System.out.println("-------------------------------------------------");
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			
			try{
				System.out.println("Enter ESTATE ID TO VIEW DETAILS:");
				this.setEstate_id(br.readLine());
				
				readEstate();
				
				System.out.println("EstateID: "+ this.getEstate_id());
				System.out.println("City: " + this.getCity());
				System.out.println("Post Code: " + this.getPostcode());
				System.out.println("Street: "+ this.getStreet());
				System.out.println("Street No: "+ this.getStreet_no());
				System.out.println("Sq. Area: "+ this.getSq_area());
				System.out.println("Agent Login: "+ this.getAgent_login());
				
				if(this.getEstate_type()=='H'){
					
					System.out.println("Floors: "+ this.getFloors());
					System.out.println("Price: " + this.getPrice());
					System.out.println("Garden: " + this.getGarden());					
				}
				else{
					
					System.out.println("Floor No: "+ this.getaFloor());
					System.out.println("Rent: " + this.getRent());
					System.out.println("Number of Rooms: " + this.getRooms());
					System.out.println("Balcony: " + this.getBalcony());
					System.out.println("Kitchen: " + this.getKitchen());
				}

				System.out.println("-------------------------------------------------");
				System.out.println("Press 1 for Edit City");
				System.out.println("Press 2 for Edit PostCode");
				System.out.println("Press 3 for Edit Street");
				System.out.println("Press 4 for Edit Street No.");
				System.out.println("Press 5 for Edit Sq. Area");
				System.out.println("Press 6 for Edit Agent Login");
				
				if(this.getEstate_type()=='H'){
					
					System.out.println("Press 7 for Edit House Floors");
					System.out.println("Press 8 for Edit House Price");
					System.out.println("Press 9 for Edit Garden");
								
				}
				else{
					
					System.out.println("Press 7 for Edit Floor No.");
					System.out.println("Press 8 for Edit Rent.");
					System.out.println("Press 9 for Edit Number of Rooms");
					System.out.println("Press B for Edit Balcony");
					System.out.println("Press K for Edit Kitchen");

				}
				

				System.out.println("Note: Cannot edit Estate ID. Please contact DB Admin.");
				choiceupdate = (char) new BufferedReader(new InputStreamReader(System.in)).read();
				
				if(choiceupdate=='1'){
					System.out.println("Enter New City: ");
					this.setCity(br.readLine());
				}
				else if(choiceupdate =='2'){
					System.out.println("Enter New Post Code: ");
					this.setPostcode(br.readLine());
				}
				else if(choiceupdate =='3'){
					System.out.println("Enter New Street: ");
					this.setStreet(br.readLine());
				}
				else if(choiceupdate =='4'){
					System.out.println("Enter New Street No: ");
					this.setStreet_no(Integer.parseInt(br.readLine()));
				}
				else if(choiceupdate =='5'){
					System.out.println("Enter New Sq. Area: ");
					this.setSq_area(Float.parseFloat(br.readLine()));
				}
				else if(choiceupdate =='6'){
					System.out.println("Enter New Agent ID: ");
					this.setAgent_login(br.readLine());
				}
				else if(choiceupdate =='7'){
					if(this.getEstate_type()=='H'){
						System.out.println("Enter New number of House Floors: ");
						this.setFloors(Integer.parseInt(br.readLine()));
					}
					else{
						System.out.println("Enter Floor Number for Apartment: ");
						this.setaFloor(Integer.parseInt(br.readLine()));
					}
				}
				else if(choiceupdate =='8'){
					if(this.getEstate_type()=='H'){
						System.out.println("Enter New House Price: ");
						this.setPrice(Double.parseDouble(br.readLine()));
					}
					else{
						System.out.println("Enter New Rent for Apartment: ");
						this.setRent(Double.parseDouble(br.readLine()));
					}
				}
				else if(choiceupdate =='9'){
					if(this.getEstate_type()=='H'){
						System.out.println("Garden (Y/N): ");
						this.setGarden((char)(br.read()));
					}
					else{
						System.out.println("Enter Number of rooms for Apartment: ");
						this.setRooms(Integer.parseInt(br.readLine()));
					}
				}
				else if(choiceupdate =='B'){
					if(this.getEstate_type()=='H'){
						System.out.println("Invalid Choice! Try Again! ");
						estateMenu();
					}
					else{
						System.out.println("Balcony (Y/N): ");
						this.setBalcony((char)br.read());
					}
				}
				else if(choiceupdate =='K'){
					if(this.getEstate_type()=='H'){
						System.out.println("Invalid Choice! Try Again! ");
						estateMenu();
					}
					else{
						System.out.println("Kitchen (Y/N): ");
						this.setKitchen((char)br.read());
					}
				}
				else{
					System.out.println("Invalid Choice! Try again!");
					estateMenu();
				}
				updateEstate();			
			}
			catch(IOException io){
				io.printStackTrace();
			}
			System.out.println("Estate details updated!");
			estateMenu();
		}

		else if(choice=='3'){
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("-----DELETE ESTATE MANAGEMENT MODULE-------------");
			System.out.println("-------------------------------------------------");
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			
			try{
				System.out.println("Enter ESTATE ID TO DELETE:");
				this.setEstate_id(br.readLine());
				
				readEstate();
				
				System.out.println("CONFIRM AGENT DELETE (Y/N):");
				char choicedelete = (char) new BufferedReader(new InputStreamReader(System.in)).read();
				if(choicedelete == 'Y'){
					deleteEstate();
				}
				else if(choicedelete=='N'){
					System.out.println("Estate not deleted!");
					estateMenu();
				}
				else{
					System.out.println("Invalid Choice! Try Again!");
					estateMenu();
				}
			}
			catch(IOException io){
				io.printStackTrace();
			}
			System.out.println("ESTATE " + this.getEstate_id() + " deleted!");
			estateMenu();
		}
		else if(choice=='4'){
			Main.menuDisplay();
		}
		else{
			System.out.println("Invalid choice! Chose again!");
			estateMenu();
		}
	}

	public void createEstate(){
		DBConManager con = new DBConManager();
		con.CreateConnection();
		try{
			/**
			 * Login credentials are checked in the Estate_Agent table and the 
			 * corresponding ID is returned.
			 */
			String sql;
			sql = "INSERT INTO ESTATE VALUES (?,?,?,?,?,?,?)";
			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
			pstm.setString(1, this.getEstate_id());
			pstm.setString(2, this.getCity());
			pstm.setString(3, this.getPostcode());
			pstm.setString(4, this.getStreet());
			pstm.setInt(5, this.getStreet_no());
			pstm.setFloat(6, this.getSq_area());
			pstm.setString(7, this.getAgent_login());
			
			pstm.executeUpdate();
			
			System.out.println("Estate" + this.getEstate_id() + "added.");
			
			pstm.close();
			
			if(this.getEstate_type()=='H'){
				
				sql = "INSERT INTO HOUSE VALUES (?,?,?,?)";
				pstm = con.getConnection().prepareStatement(sql);
				pstm.setInt(1, this.getFloors());
				pstm.setDouble(2, this.getPrice());
				pstm.setString(3, String.valueOf(this.getGarden()));
				pstm.setString(4, this.getEstate_id());
				
				pstm.executeUpdate();
				
				System.out.println("House " + this.getEstate_id() + "added.");
				
				pstm.close();
				
			}
			else if(this.getEstate_type()=='A'){
				
				sql = "INSERT INTO APARTMENT VALUES (?,?,?,?,?,?)";
				
				pstm = con.getConnection().prepareStatement(sql);
				pstm.setInt(1, this.getaFloor());
				pstm.setDouble(2, this.getRent());
				pstm.setInt(3, this.getRooms());
				pstm.setString(4, String.valueOf(this.getBalcony()));
				pstm.setString(5, String.valueOf(this.getKitchen()));
				pstm.setString(6, this.getEstate_id());
				
				pstm.executeUpdate();
				
				System.out.println("Apartment " + this.getEstate_id() + "added.");
				
				pstm.close();
			}
			con.closeConnection();;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		estateMenu();
	}
	public void autoGenerate(){
		DBConManager con = new DBConManager();
		con.CreateConnection();
		String e_id = null;
		try{
			/**
			 * Autogenerate is used to get the max estateid from Estate and then increment it by 1. 
			 */
			String sql;
			sql = "SELECT MAX(ESTATE_ID) FROM ESTATE";
			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){
				e_id = rs.getString(1);		   
			}
			rs.close();
			pstm.close();
			con.closeConnection();;
		}
		catch(Exception e){
			e.printStackTrace();	
		}
		String regex = "(?<=[\\w&&\\D])(?=\\d)";
		String[] alphanumeric = e_id.split(regex);
		int numeric = Integer.parseInt(alphanumeric[1]);
		numeric = numeric+1;
		e_id = alphanumeric[0].concat(String.format("%03d", numeric));
		this.setEstate_id(e_id);
	}
	
	public void readEstate(){
		DBConManager con = new DBConManager();
		con.CreateConnection();
		try{
			/**
			 * Read Estate is used read the details of the Estate requested. 
			 */
			String sql;
			sql = "SELECT ESTATE_ID, CITY, POSTAL_CODE, STREET, STREET_NUMBER, SQUARE_AREA, AGENT_LOGIN  FROM ESTATE WHERE ESTATE_ID =?";
			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
			pstm.setString(1, this.getEstate_id());
			
			ResultSet rs = pstm.executeQuery();
			if(!rs.next()){
				System.out.println("Invalid Estate ID! Try again!");
				estateMenu();
			}
			while(rs.next()){
				this.setEstate_id(rs.getString(1));
				this.setCity(rs.getString(2));
				this.setPostcode(rs.getString(3));
				this.setStreet(rs.getString(4));
				this.setStreet_no(rs.getInt(5));
				this.setSq_area(rs.getFloat(6));
				this.setAgent_login(rs.getString(7));
				
			}
			rs.close();
			pstm.close();
			
			sql = "SELECT FLOORS, PRICE, GARDEN FROM HOUSE WHERE ESTATE_ID =?";
			pstm = con.getConnection().prepareStatement(sql);
			pstm.setString(1, this.getEstate_id());
			
			rs = pstm.executeQuery();
			if(!rs.next()){
				
				rs.close();
				pstm.close();
				sql = "SELECT FLOOR, RENT, ROOMS, BALCONY, KITCHEN FROM APARTMENT WHERE ESTATE_ID =?";
				pstm = con.getConnection().prepareStatement(sql);
				pstm.setString(1, this.getEstate_id());
				rs = pstm.executeQuery();
				if(!rs.next()){
					System.out.println("Invalid Estate ID! Try again!");
					estateMenu();
				}
				while(rs.next()){
					this.setFloors(rs.getInt(1));
					this.setRent(rs.getDouble(2));
					this.setRooms(rs.getInt(3));
					this.setBalcony(rs.getString(4).charAt(0));
					this.setKitchen(rs.getString(5).charAt(0));
				}
				this.setEstate_type('A');
			}
			else{
				while(rs.next()){
					this.setFloors(rs.getInt(1));
					this.setPrice(rs.getDouble(2));
					this.setGarden(rs.getString(3).charAt(0));
				
				}
				this.setEstate_type('H');

			}
			rs.close();
			pstm.close();
			
			con.closeConnection();;
		}
		catch(Exception e){
			e.printStackTrace();	
		}
	}
	
	public void updateEstate(){
		DBConManager con = new DBConManager();
		con.CreateConnection();
		try{
			/**
			 * Update Estate is used update the details of the estate requested. 
			 */
			String sql;
			sql = "UPDATE ESTATE SET CITY=?, POSTAL_CODE=?, STREET=?, STREET_NUMBER =?, SQUARE_AREA = ?, AGENT_LOGIN = ? WHERE ESTATE_ID=?";
			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
			pstm.setString(1, this.getCity());
			pstm.setString(2, this.getPostcode());
			pstm.setString(3, this.getStreet());
			pstm.setInt(4, this.getStreet_no());
			pstm.setFloat(5, this.getSq_area());
			pstm.setString(6, this.getAgent_login());
			pstm.setString(7, this.getEstate_id());

			
			pstm.executeUpdate();
			pstm.close();
			con.closeConnection();;
		}
		catch(Exception e){
			e.printStackTrace();	
		}
	}
	
	public void deleteEstate(){
		DBConManager con = new DBConManager();
		con.CreateConnection();
		try{
			/**
			 * Read agent is used read the details of the user requested. 
			 */
			String sql;
			PreparedStatement pstm;
			if(this.getEstate_type()=='H'){
				sql = "DELETE FROM HOUSE WHERE ESTATE_ID = ? ";
				pstm = con.getConnection().prepareStatement(sql);
				pstm.setString(1, this.getEstate_id());
				
				pstm.executeUpdate();
				pstm.close();
			}
			else{
				sql = "DELETE FROM APARTMENT WHERE ESTATE_ID = ? ";
				pstm = con.getConnection().prepareStatement(sql);
				pstm.setString(1, this.getEstate_id());
				
				pstm.executeUpdate();
				pstm.close();
				
			}
			sql = "DELETE FROM ESTATE WHERE ESTATE_ID = ? ";
			pstm = con.getConnection().prepareStatement(sql);
			pstm.setString(1, this.getEstate_id());
			
			pstm.executeUpdate();
			pstm.close();
			con.closeConnection();;
		}
		catch(Exception e){
			e.printStackTrace();	
		}
	}
	
	public String getEstate_id() {
		return estate_id;
	}
	public void setEstate_id(String estate_id) {
		this.estate_id = estate_id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getStreet_no() {
		return street_no;
	}
	public void setStreet_no(int street_no) {
		this.street_no = street_no;
	}
	public float getSq_area() {
		return sq_area;
	}
	public void setSq_area(float sq_area) {
		this.sq_area = sq_area;
	}
	public String getAgent_login() {
		return agent_login;
	}
	public void setAgent_login(String agent_login) {
		this.agent_login = agent_login;
	}
	public String getloggedinUser() {
		return loggedinUser;
	}
	public void setloggedinUser(String loggedinUser) {
		EstateManagement.loggedinUser = loggedinUser;
	}
	
	public static String getLoggedinUser() {
		return loggedinUser;
	}

	public static void setLoggedinUser(String loggedinUser) {
		EstateManagement.loggedinUser = loggedinUser;
	}

	public int getFloors() {
		return floors;
	}

	public void setFloors(int floors) {
		this.floors = floors;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public char getGarden() {
		return garden;
	}

	public void setGarden(char garden) {
		this.garden = garden;
	}

	public int getaFloor() {
		return aFloor;
	}

	public void setaFloor(int aFloor) {
		this.aFloor = aFloor;
	}

	public double getRent() {
		return rent;
	}

	public void setRent(double rent) {
		this.rent = rent;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	public char getBalcony() {
		return balcony;
	}

	public void setBalcony(char balcony) {
		this.balcony = balcony;
	}

	public char getKitchen() {
		return kitchen;
	}

	public void setKitchen(char kitchen) {
		this.kitchen = kitchen;
	}


	
	
	public char getEstate_type() {
		return estate_type;
	}

	public void setEstate_type(char estate_type) {
		this.estate_type = estate_type;
	}

	
}
