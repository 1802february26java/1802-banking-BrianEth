package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.model.BankAccount;
import com.revature.util.ConnectionUtil;

public class BankAccountRepositoryJdbc implements BankAccountRepository {

	private static Logger logger = Logger.getLogger(ConnectionUtil.class);

	private static BankAccountRepositoryJdbc repository = new BankAccountRepositoryJdbc();

	
	private BankAccountRepositoryJdbc() {}
	
	public static BankAccountRepository getInstance() {
		return repository;
	}
	
	
	@Override
	public boolean insert(BankAccount account) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			
			String sq1="INSERT INTO BANKACCOUNT(B_ID,B_USER, B_PASSWORD, B_BALANCE ) VALUES(?,?,?,?)";
		
			logger.trace("getting statement object in insert account");
			
			PreparedStatement statement = connection.prepareStatement(sq1);
			statement.setLong(++parameterIndex, account.getAccountId());
			statement.setString(++parameterIndex, account.getUsername());
			statement.setString(++parameterIndex, account.getPassword());
			statement.setDouble(++parameterIndex, account.getBalance());
			
			logger.trace("parameters for insertion of account set");
			
			if(statement.executeUpdate() > 0){
				logger.trace("account inserted succefully");
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("exception thrown while inserting ", e);
		}
		
		
		return false;
	}

	@Override
	public boolean insertProcedure(BankAccount account) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	

	@Override
	public Set<BankAccount> selectAll() {
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM BANKACCOUNT";
			
			logger.trace("getting statement obj in select all accounts");
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet result = statement.executeQuery();
			
			Set<BankAccount> set = new HashSet<>();
			while(result.next()) {
				set.add(new BankAccount(
						result.getLong("B_ID"),
						result.getString("B_USER"),
						result.getString("B_PASSWORD"),
						result.getDouble("B_BALANCE")
						));
			}
			
			return set;
			
		} catch (SQLException e) {
			
			logger.error("Error while selecting all accounts",e);
		}
		return null;
	}

	@Override
	public Set<String> selectUsers() {
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM BANKACCOUNT";
			
			logger.trace("getting statement obj in select all accounts");
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet result = statement.executeQuery();
			
			Set<String> set = new HashSet<>();
			while(result.next()) {
				set.add(new String(result.getString("B_USER")));
			}
			
			return set;
			
		} catch (SQLException e) {
			
			logger.error("Error while selecting all accounts",e);
		}
		return null;
	}

	@Override
	public BankAccount selectUserByUsername(String username) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql = "SELECT * FROM BANKACCOUNT WHERE B_USER = ?";
			
			logger.trace("getting statement obj in specific select accounts");
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++parameterIndex, username);
			ResultSet result = statement.executeQuery();
			
			
			if(result.next()) {
				return new BankAccount(
						result.getLong("B_ID"),
						result.getString("B_USER"),
						result.getString("B_PASSWORD"),
						result.getDouble("B_BALANCE"));
			}
			
			
			
		} catch (SQLException e) {
			
			logger.error("Error while selecting account by user name",e);
		}
		return null;
	}

	@Override
	public void updateAccountBalance(BankAccount account) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			
			String sq1="UPDATE BANKACCOUNT SET B_BALANCE = ? WHERE B_USER = ?";
		
			logger.trace("getting statement object in update account");
			
			PreparedStatement statement = connection.prepareStatement(sq1);
			statement.setDouble(++parameterIndex, account.getBalance());
			statement.setString(++parameterIndex, account.getUsername());
			
			logger.trace("parameters for update of account set");
			//System.out.println(statement.toString());
			
			
			if(statement.executeUpdate() > 0){
				logger.trace("account updated succefully");
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("exception thrown while updating ", e);
		}
		
		
		return;
	}
	
	//tested connection.
//	public static void main(String[] args) {
//		BankAccountRepository repository = new BankAccountRepositoryJdbc();
//		
//		logger.info(repository.insert(new BankAccount(1,"Admin", "p4ssw0rd", 100.00)));
//	}

}



