package com.revature.controller;

import java.util.HashMap;

import com.revature.exception.OverdrawException;
import com.revature.model.BankAccount;

public class Controller {

	private static final Controller controller= new Controller();
	private HashMap<String, BankAccount> accounts;
	private BankAccount currentAccount;
	
	
	public static Controller getInstance(){
		return controller;
	}
	
	
	public void openBank(){
		
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
