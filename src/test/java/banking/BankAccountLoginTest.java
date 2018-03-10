package banking;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.revature.model.BankAccount;
import com.revature.repository.BankAccountRepository;
import com.revature.repository.BankAccountRepositoryJdbc;
import com.revature.util.ConnectionUtil;

public class BankAccountLoginTest {

	private static Logger logger = Logger.getLogger(ConnectionUtil.class);
	BankAccountRepository repository;
	
	//testing obj
	private BankAccount dummyAccount;
	
	@Before
	public void setup(){
		repository = BankAccountRepositoryJdbc.getInstance();
	}
	
	//Unit Tests
	@Test
	public void testAdminBalance(){
		logger.trace("testing updating admin balance.");
		dummyAccount = new BankAccount(1,"admin","p4ssw0rd", 1000);
		assertTrue(repository.updateAccountBalance(dummyAccount));
	}
	
	@Test
	public void testGetBalance(){
		testAdminBalance();
		assertThat(repository.selectUserByUsername("admin").getBalance(), is(1000.00));
	}
}
