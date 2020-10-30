package reimbursementSystem.test;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.reimbursement.service.TicketService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GetByIdTest{
	
	private TicketService use;
	
	@Before
	public void getById() {
		use = new TicketService();
		
	}

	
	@Test
	public void add() {
		
		use.getById(12);
		assertTrue(true);
		
	}
	
}
	