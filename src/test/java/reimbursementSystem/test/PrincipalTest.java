package reimbursementSystem.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrincipalTest {
	
	private PrincipalTest use;
	
	@Before
	public void getId() {
		use = new PrincipalTest();
		
	}

	
	@Test
	public void getById() {
		
		use.getId();
		assertTrue(true);
		
	}
	
}
	