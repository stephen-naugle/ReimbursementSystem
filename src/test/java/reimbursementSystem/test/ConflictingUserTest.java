package reimbursementSystem.test;

import static org.junit.Assert.assertNotNull;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.web.exceptions.ConflictingUserException;
import com.web.exceptions.SelfResolverException;

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
