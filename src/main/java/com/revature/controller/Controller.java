package com.revature.controller;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import com.revature.exception.OverdrawException;
import com.revature.model.BankAccount;
import com.revature.repository.BankAccountRepository;
import com.revature.repository.BankAccountRepositoryJdbc;

public class Controller {

	private static final Controller controller= new Controller();
	BankAccountRepository repository = BankAccountRepositoryJdbc.getInstance();

	private HashMap<String, BankAccount> accounts;
	private BankAccount currentAccount;
	
	
	public static Controller getInstance(){
		return controller;
	}
	
	
	public void openBank(){
		//put all bank accounts in memory
		loadDatabase();
		Scanner input = new Scanner(System.in);
		while(input.hasNext()){
			String[] command = input.nextLine().split(" ");
			switch(command[0]){
			case "login":
				if(command.length==3){
					if(accounts.get(command[1]).getPassword()== command[2]){
						currentAccount= accounts.get(command[1]);
					} else {
						//TODO: invalid login
					}
				} else {
					//TODO: invalid login
				}
				break;
			case "deposit":
				
				break;
			case "withdraw":
				
				break;
				
			case "logout":
				currentAccount = null;
				break;
				
			case "register":
				//TODO: registration
				break;
			default:
				System.out.println("invalid request, please try again");
			}
		}
	}
	
	private void loadDatabase(){
		Set<BankAccount> allAccounts = repository.selectAll();
		for(BankAccount a: allAccounts){
			accounts.put(a.getUsername(),a);
		}
		
	}
	
	public boolean login(String username, String password){
//		try{
			BankAccount account = accounts.get(username);
			if(currentAccount == account){
				System.out.println("already logged into this account");
			} else if (account.authenticate(username, password)){
				currentAccount = account;
				System.out.println("Login successful");
				return true;
			}
			
			
//		} catch(OverdrawException e){
//			System.out.println(e.getMessage());
//		}
		
		
		return false;
	}
	
	public boolean logout() {
		if(currentAccount == null){
			System.out.println("Nobody is logged in");
			return false;
		} else {
			currentAccount = null;
			System.out.println("Logout successful");
			return true;
		}
	}
	
	
	public boolean deposit(double depo){
		if(currentAccount == null){
			System.out.println("You must login first");
			return false;
		} else if (depo <= 0) {
			System.out.println("Please enter a valid deposit amount");
		} else {
			try {
				currentAccount.withdraw(depo);
				System.out.println("deposit complete");
				return true;
				
			} catch (OverdrawException e) {
				System.out.println(e.getMessage());
			}
		}
		
		return false;
	}
	
	
}
