package reimbursementSystem.test;

import static org.junit.Assert.assertNotNull;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.reimbursement.controller.ConflictingUserException;
import com.reimbursement.controller.SelfResolverException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConflictingUserTest {
	
	private ConflictingUserException use;
	private SelfResolverException testing;

	@Test
	public void ConflictingUserException() {
		use = new ConflictingUserException(null);
		
		assertNotNull(use);
	}
	
	@Test
	public void SelfResolverException() {
		testing = new SelfResolverException(null);
		
		assertNotNull(testing);
	}
	
}
