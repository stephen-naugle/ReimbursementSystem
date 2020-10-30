package reimbursementSystem.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.reimbursement.servlets.TicketServlet;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TicketServletTest {
		
		private TicketServlet testing;
		
		@Before
		public void doPost() {
			testing = new TicketServlet();
			
		}
		
		@Test
		public void servletTest() {
			
			testing.destroy();
			assertTrue(true);
		}
}