package com.revature.repository;

import java.util.Set;

import com.revature.model.BankAccount;

public interface BankAccountRepository {

	
	public boolean insert(BankAccount account);
	
	public boolean insertProcedure(BankAccount account);
	
	public Set<String> selectUsers();
	
	public Set<BankAccount> selectAll();
	
	
}
