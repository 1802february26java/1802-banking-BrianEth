package com.revature.model;

import com.revature.exception.OverdrawException;

public class BankAccount {
	
	
	/*
	 * CREATE TABLE BANKACCOUNT
(
  B_ID NUMBER,
  B_USER VARCHAR2(100) NOT NULL,
  B_PASSWORD VARCHAR2(100) NOT NULL,
  B_BALANCE NUMBER(38,3) DEFAULT 0,
  CONSTRAINT PK_BANKACCOUNT PRIMARY KEY (B_ID)
);
	 * 
	 */

	private long accountId;
	private String username;
	private String password;
	private double balance;
	
	public BankAccount(long accountId, String username, String password){
		super();
		this.accountId = accountId;
		this.username = username;
		this.password = password;
	}

	public BankAccount(long accountId, String username, String password, double balance) {
		super();
		this.accountId = accountId;
		this.username = username;
		this.password = password;
		this.balance = balance;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getBalance() {
		return balance;
	}

	public void deposit(double balance) {
		this.balance += balance;
	}
	
	public void withdraw(double balance) throws OverdrawException{
		this.balance -= balance;
		
		if(this.balance < 0) {
			throw new OverdrawException("Insufficient balance, you just over-drew");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (accountId ^ (accountId >>> 32));
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	//login check
	public boolean authenticate(String username, String password) {
		
		if(this.username.equals(username) && this.password.equals(password)){
			return true;
		}
		
		//add exception?
		return false;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccount other = (BankAccount) obj;
		if (accountId != other.accountId)
			return false;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BankAccount [accountId=" + accountId + ", username=" + username + ", password=" + password
				+ ", balance=" + balance + "]";
	};
	
	
	
	
}
