package reimbursementSystem.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.reimbursement.repo.TicketDAO;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TicketDAOTest {
	
	private TicketDAO use;
	
	@Before
	public void start() {
		use = new TicketDAO();
		
	}

	
	@Test
	public void getById() {
		
		use.getById(1);
		assertTrue(true);
		
	}
	
}
	
//}
//
//public ArrayList<Ticket> getAll(){
//	log.info("in TicketService.getAll()");
//	return ticketDao.getAll();
//}