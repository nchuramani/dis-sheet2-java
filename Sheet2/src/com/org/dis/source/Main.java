package com.org.dis.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("----------------------------------------");
		System.out.println("----Welcome to Estate Management--------");
		System.out.println("----------------------------------------");
		login();
	}
	public static void login(){
		String username = null;
		String password = null;
		String loggedin = null;
		InputStreamReader ir = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(ir);
		try{
			System.out.println("Enter Login ID:");
			username = br.readLine();
			System.out.println("Enter password:");
			password = br.readLine();
		}
		catch(IOException io){
			io.printStackTrace();
		}
		Login lo = new Login();
		loggedin = lo.executeLogin(username, password);
		if(loggedin == null){
			System.out.println("Invalid Login. Try again!");
			login();
		}
		else{
			menuDisplay(loggedin);
		}
	}
	public static void menuDisplay(String userlogin){
		char choice = '0';
		System.out.println("-------------------------------------------------");
		System.out.println("--------------------WELCOME " + userlogin + "----------------");
		System.out.println("-------------------------------------------------");
		System.out.println("Press 1 for Agent Managemnt");
		System.out.println("Press 2 for Estate Managemnt");
		System.out.println("Press 3 for Contract Managemnt");
		System.out.println("Press 4 to exit");

		try{
			 choice = (char) new BufferedReader(new InputStreamReader(System.in)).read();
		}catch(Exception e){
			e.printStackTrace();
		}
		if (choice == '1'){
			AgentManagement am = new AgentManagement(userlogin);
			am.setLogin(userlogin);
			am.agentMenu();
		}
		else if(choice=='2'){
			System.out.println("Call Estate Management");
		}
		else if(choice=='3'){
			System.out.println("Call Contract Management");
		}
		else if(choice=='4'){
			System.out.println("Thank you!");
			System.exit(0);
		}
		else{
			System.out.println("Invalid choice! Chose again!");
			menuDisplay(userlogin);
		}
	}
	
}
