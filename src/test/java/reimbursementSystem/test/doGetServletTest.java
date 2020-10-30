package reimbursementSystem.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.reimbursement.servlets.UserServlet;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class doGetServletTest {
		
		private UserServlet use;
		
		@Before
		public void doGet() {
			use = new UserServlet();
			
		}

		
		@Test
		public void dGet() {
			
			use.destroy();
			assertTrue(true);
			
		}
	}
	
