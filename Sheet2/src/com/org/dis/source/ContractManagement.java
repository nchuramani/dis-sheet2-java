package com.org.dis.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.org.dis.db2manage.DBConManager;

public class ContractManagement {
	
	private String firstName;
	private String name;
	private String address;
	private static String loggedinUser;
	private String login;
	private char contract_type;
	private String contract_no;
	private Date contract_date;
	private String contract_place;
	private int installments;
	private float interest_rate;
	private String estate_id;
	private Date startDate;
	private int duration;
	private double add_cost;
	
	
	public ContractManagement(String loggedin) {
		// TODO Auto-generated constructor stub
		ContractManagement.loggedinUser = loggedin;
		
	}
	public void contractMenu(){
		char choice = '0';
		System.out.println("-------------------------------------------------");
		System.out.println("------------CONTRACT MANAGEMENT --------------");
		System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
		System.out.println("-------------------------------------------------");
		System.out.println("Press 1 for Inserting Persons");
		System.out.println("Press 2 for Creating Contracts");
		System.out.println("Press 3 for Viewing of Contracts");
		System.out.println("Press 4 to go back to Main Menu");
		try{
			 choice = (char) new BufferedReader(new InputStreamReader(System.in)).read();
		}catch(Exception e){
			e.printStackTrace();
		}
		if (choice == '1'){
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("------INSERT PERSONS MODULE--------------");
			System.out.println("-------------------------------------------------");
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			
			try{
				System.out.println("Enter PERSON NAME:");
				this.setName(br.readLine());
				System.out.println("Enter PERSON FIRST NAME:");
				this.setFirstName(br.readLine());				
				System.out.println("Enter PERSON ADDRESS:");
				this.setAddress(br.readLine());

			}
			catch(IOException io){
				io.printStackTrace();
			}
			readPerson();
		}
		else if (choice == '2'){
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("------CREATE CONTRACTS MODULE--------------");
			System.out.println("-------------------------------------------------");
			System.out.println("Press P for a new Purchase Contract!");
			System.out.println("Press T for a new Purchase Contract!");
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			try{
				char conty = (char) new BufferedReader(new InputStreamReader(System.in)).read();
				this.setContract_type(conty);
				
				System.out.println("Enter Place:");
				this.setContract_place(br.readLine());
				if(this.getContract_type()=='P'){
					System.out.println("Enter NAME:");
					this.setName(br.readLine());
					System.out.println("Enter PERSON FIRST NAME:");
					this.setFirstName(br.readLine());				
					System.out.println("Enter ESTATE ID:");
					this.setEstate_id(br.readLine());
					System.out.println("Enter Number of Installments:");
					this.setInstallments(Integer.parseInt(br.readLine()));
					System.out.println("Enter Interest Rate:");
					this.setInterest_rate(Float.parseFloat(br.readLine()));
					readContract();
					
				}
				else if(this.getContract_type()=='T'){
					System.out.println("Enter Start Date (YYYY-MM-DD):");
					
					DateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
					java.util.Date date1 = formatter.parse(br.readLine());
					this.setStartDate(new java.sql.Date(date1.getTime()));
					System.out.println("Enter Duration:");
					this.setDuration(Integer.parseInt(br.readLine()));				
					System.out.println("Enter Additional Cost:");
					this.setAdd_cost(Double.parseDouble(br.readLine()));
					System.out.println("Enter NAME:");
					this.setName(br.readLine());
					System.out.println("Enter PERSON FIRST NAME:");
					this.setFirstName(br.readLine());				
					System.out.println("Enter ESTATE ID:");
					this.setEstate_id(br.readLine());
					readContract();
				}
				else{
					System.out.println("Invalid Contract Type! Try again!");
					contractMenu();
				}
				contractMenu();
			}
			catch(IOException io){
				io.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		else if (choice == '3'){
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("------VIEW CONTRACTS MODULE--------------");
			System.out.println("-------------------------------------------------");
			viewPurchaseContracts();
			viewSaleContracts();
		}
		else if(choice=='4'){
			Main.menuDisplay();
		}
		else{
			System.out.println("Invalid choice! Choose again!");
			contractMenu();
		}
	}
	
	
	public void createPerson(){
		DBConManager con = new DBConManager();
		con.CreateConnection();
		try{
			
			String sql;
			sql = "INSERT INTO PERSON VALUES (?,?,?)";
			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
			pstm.setString(1, this.getFirstName());
			pstm.setString(2, this.getName());
			pstm.setString(3, this.getAddress());
			pstm.executeUpdate();
		
			System.out.println("Person" + this.getFirstName() +  " "+this.getName() +"added.");
			pstm.close();
			con.closeConnection();;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		contractMenu();
	}
	public void readPerson(){
		DBConManager con = new DBConManager();
		con.CreateConnection();
		try{
			/**
			 * Read agent is used read the details of the user requested. 
			 */
			String sql;
			sql = "SELECT FIRST_NAME, NAME, ADDRESS FROM PERSON WHERE FIRST_NAME =? AND NAME =?";
			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
			pstm.setString(1, this.getFirstName());
			pstm.setString(2, this.getName());
			
			ResultSet rs = pstm.executeQuery();
			if(!rs.next()){
				createPerson();
			}
			else{
				System.out.println("Person Already Exists! Try again!");
				contractMenu();
			
			}
			rs.close();
			pstm.close();
			con.closeConnection();;
		}
		catch(Exception e){
			e.printStackTrace();	
		}
	}
	public void readContract(){
		DBConManager con = new DBConManager();
		con.CreateConnection();
		try{
			/**
			 */
			String sql;
			PreparedStatement pstm;
			ResultSet rs;
			if(this.getContract_type()=='P'){
				sql = "SELECT CONTRACT_NO FROM PURCHASE_CONTRACT WHERE FIRST_NAME =? AND NAME =? AND ESTATE_ID=?";
				pstm = con.getConnection().prepareStatement(sql);
				pstm.setString(1, this.getFirstName());
				pstm.setString(2, this.getName());
				pstm.setString(3, this.getEstate_id());
			
				rs = pstm.executeQuery();
				if(!rs.next()){
					autoGenerate();
					createContract();
				}
				else{
					System.out.println("Contract for the person-estate combination Already Exists! Try again!");
					contractMenu();
				}
			}
			else{
				sql = "SELECT CONTRACT_NO FROM TENANCY_CONTRACT WHERE FIRST_NAME =? AND NAME =? AND ESTATE_ID=?";
				pstm = con.getConnection().prepareStatement(sql);
				pstm.setString(1, this.getFirstName());
				pstm.setString(2, this.getName());
				pstm.setString(3, this.getEstate_id());
			
				rs = pstm.executeQuery();
				if(!rs.next()){
					autoGenerate();
					createContract();
				}
				else{
					System.out.println("Contract for the person-estate combination Already Exists! Try again!");
					contractMenu();
				}
			}
			rs.close();
			pstm.close();
			con.closeConnection();;
		}
		catch(Exception e){
			e.printStackTrace();	
		}
	}
	
	public void createContract(){
		DBConManager con = new DBConManager();
		con.CreateConnection();
		try{
			/**
			 * Login credentials are checked in the Estate_Agent table and the 
			 * corresponding ID is returned.
			 */
			String sql;
			sql = "INSERT INTO CONTRACT VALUES (?,?,?)";
			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
			pstm.setString(1, this.getContract_no());
			java.util.Date today = new java.util.Date();
			this.setContract_date(new java.sql.Date(today.getTime()));
			pstm.setDate(2,this.getContract_date() );
			pstm.setString(3, this.getContract_place());
			
			pstm.executeUpdate();
			
			System.out.println("Contract " + this.getContract_no() + "added.");
			
			pstm.close();
			
			if(this.getContract_type()=='P'){
				
				sql = "INSERT INTO PURCHASE_CONTRACT VALUES (?,?,?,?,?,?)";
				pstm = con.getConnection().prepareStatement(sql);
				pstm.setInt(1, this.getInstallments());
				pstm.setFloat(2, this.getInterest_rate());
				pstm.setString(3, this.getContract_no());
				pstm.setString(4, this.getEstate_id());
				pstm.setString(5, this.getFirstName());
				pstm.setString(6, this.getName());

				
				pstm.executeUpdate();
				
				System.out.println("Purchase Contract " + this.getContract_no() + "added.");
				
				pstm.close();
				
			}
			else if(this.getContract_type()=='T'){
				
				sql = "INSERT INTO TENANCY_CONTRACT VALUES (?,?,?,?,?,?,?)";
				
				pstm = con.getConnection().prepareStatement(sql);
				pstm.setDate(1, this.getStartDate());
				pstm.setInt(2, this.getDuration());
				pstm.setDouble(3, this.getAdd_cost());
				pstm.setString(4, this.getContract_no());
				pstm.setString(5, this.getEstate_id());
				pstm.setString(6, this.getFirstName());
				pstm.setString(7, this.getName());
			
				System.out.println(this.getStartDate());
				System.out.println(this.getAdd_cost());
				System.out.println(this.getContract_no());
				System.out.println(this.getEstate_id());
				System.out.println(this.getFirstName());
				System.out.println(this.getName());
				
				
				pstm.executeUpdate();
				
				System.out.println("Tenancy Contract  " + this.getContract_no() + "added.");
				
				pstm.close();
			}
			con.closeConnection();;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		contractMenu();
	}
	public void autoGenerate(){
		DBConManager con = new DBConManager();
		con.CreateConnection();
		String c_no = null;
		try{
			/**
			 * Autogenerate is used to get the max estateid from Estate and then increment it by 1. 
			 */
			String sql;
			sql = "SELECT MAX(CONTRACT_NO) FROM CONTRACT";
			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){
				c_no = rs.getString(1);		   
			}
			rs.close();
			pstm.close();
			con.closeConnection();;
		}
		catch(Exception e){
			e.printStackTrace();	
		}
		String regex = "(?<=[\\w&&\\D])(?=\\d)";
		String[] alphanumeric = c_no.split(regex);
		int numeric = Integer.parseInt(alphanumeric[1]);
		numeric = numeric+1;
		c_no = alphanumeric[0].concat(String.format("%03d", numeric));
		this.setContract_no(c_no);
	}
	
	public void viewPurchaseContracts()
	{
		DBConManager con = new DBConManager();
		con.CreateConnection();
		try{
			
			String sql;
			sql = "SELECT * FROM TENANCY_CONTRACT";
			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
			System.out.println("-------------------------------------------------");
			System.out.println("------DISPLAYING PURCHASE CONTRACTS --------------");
			System.out.println("-------------------------------------------------");
			ResultSet rs = pstm.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			int rectno = 1;
			while(rs.next()) {
				System.out.println("-------------------------------------------------");
				System.out.println("---- Tenancy Contract S. No. " + rectno + "---------------");
				System.out.println("-------------------------------------------------");

				rectno++;
			    
			    for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
			        String columnValue = rs.getString(columnIndex);
					System.out.println(metaData.getColumnName(columnIndex) + ": " + columnValue);
			    }
			    System.out.printf("\n");
				System.out.println("-------------------------------------------------");

			}
			rs.close();
			pstm.close();
			con.closeConnection();;
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	
	}
	public void viewSaleContracts()
	{
		DBConManager con = new DBConManager();
		con.CreateConnection();
		try{
			
			String sql;
			sql = "SELECT * FROM PURCHASE_CONTRACT";
			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
			System.out.println("-------------------------------------------------");
			System.out.println("------DISPLAYING TENANCY CONTRACTS --------------");
			System.out.println("-------------------------------------------------");
			ResultSet rs = pstm.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			int recno = 1;;
			while(rs.next()) {
				System.out.println("-------------------------------------------------");
				System.out.println("---- Purchase Contract S. No. " + recno +  "---------------");
				System.out.println("-------------------------------------------------");
				recno++;
			    for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
			        String columnValue = rs.getString(columnIndex);

			        System.out.println(metaData.getColumnName(columnIndex) + ": " +columnValue);

			    }
			    System.out.printf("\n");
				System.out.println("-------------------------------------------------");

			}
			rs.close();
			pstm.close();
			con.closeConnection();;
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		contractMenu();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	public String getloggedinUser() {
		return loggedinUser;
	}
	public void setloggedinUser(String loggedinUser) {
		ContractManagement.loggedinUser = loggedinUser;
	}
	public char getContract_type() {
		return contract_type;
	}
	public void setContract_type(char contract_type) {
		this.contract_type = contract_type;
	}

	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
	public Date getContract_date() {
		return contract_date;
	}
	public void setContract_date(Date contract_date) {
		this.contract_date = contract_date;
	}
	public String getContract_place() {
		return contract_place;
	}
	public void setContract_place(String contract_place) {
		this.contract_place = contract_place;
	}
	public int getInstallments() {
		return installments;
	}
	public void setInstallments(int installments) {
		this.installments = installments;
	}
	public float getInterest_rate() {
		return interest_rate;
	}
	public void setInterest_rate(float interest_rate) {
		this.interest_rate = interest_rate;
	}
	public String getEstate_id() {
		return estate_id;
	}
	public void setEstate_id(String estate_id) {
		this.estate_id = estate_id;
	}

	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public double getAdd_cost() {
		return add_cost;
	}
	public void setAdd_cost(double add_cost) {
		this.add_cost = add_cost;
	}
}
