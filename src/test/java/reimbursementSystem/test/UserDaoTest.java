package reimbursementSystem.test;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.reimbursement.repo.UserDAO;

public class UserDaoTest {
	
	private UserDAO use;
	
	
	@Before
	public void start() {
		use = new UserDAO();
		
	}

	
	@Test
	public void GetAllTest() {
		
		use.getAll();
		assertTrue(true);
		
	}
	
}
