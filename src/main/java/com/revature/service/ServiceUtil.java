package com.revature.service;

import org.apache.log4j.Logger;

import com.revature.exception.OverdrawException;
import com.revature.model.BankAccount;
import com.revature.repository.BankAccountRepository;
import com.revature.repository.BankAccountRepositoryJdbc;

public class ServiceUtil {

	private static ServiceUtil service = new ServiceUtil();

	private BankAccountRepository repository = BankAccountRepositoryJdbc.getInstance();

	private static Logger logger = Logger.getLogger(ServiceUtil.class);

	private BankAccount currentAccount = null;
	
	
	private ServiceUtil(){}
	
	public static ServiceUtil getInstance(){
		return service;
	}
	
	public BankAccount getAccount() {
		return currentAccount;
	}
	
	
	public BankAccount login(String user, String password){
		
		BankAccount account = repository.selectUserByUsername(user);
		if(account.getPassword().equals(password)){
			currentAccount = account;
			System.out.println("You are now logged in");
			return account;
		}
		System.out.println("Login failed, please ensure you enter the correct credentials.");
		return null;
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
	
	public boolean register(String user, String password){
		
		if(repository.insert(new BankAccount(0, user, password, 0))) {
			return true;
		}
		
		return false;
	}
	
	public double getBalance(){
		return currentAccount.getBalance();
		
	}
	
	
	public boolean deposit(double depo)  {
		if(currentAccount == null){
			System.out.println("You must login first");
			return false;
		} else if (depo <= 0) {
			System.out.println("Please enter a valid deposit amount");
		} else {
				currentAccount.deposit(depo);
				repository.updateAccountBalance(currentAccount);
				//System.out.println("deposit complete");
				printRecipt();
				//TODO: recipt?
				return true;
		}
		
		return false;
	}
	
	public boolean withdraw(double withd) throws OverdrawException{
		if(currentAccount == null){
			System.out.println("You must login first");
			return false;
		} else if (withd <= 0) {
			System.out.println("Please enter a valid withdraw amount");
		} else {
			try {
				currentAccount.withdraw(withd);
				repository.updateAccountBalance(currentAccount);
				//System.out.println("withdrawl complete");
				//TODO:recipt?
				printRecipt();
				return true;
				
			} catch (OverdrawException e) {
				System.out.println(e.getMessage());
			}
		}
		
		return false;
	}
	
	private void printRecipt(){
		System.out.println("----------------------------------------");
		System.out.println("| Recipt for account "+ currentAccount.getUsername() + " |");
		System.out.println("| Current Balance is: " + currentAccount.getBalance() + " |");
		System.out.println("----------------------------------------");
	}

	
}