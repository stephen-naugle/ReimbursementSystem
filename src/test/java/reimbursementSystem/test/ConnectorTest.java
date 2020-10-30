package reimbursementSystem.test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.reimbursement.util.PlainTextConnectionUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConnectorTest {

	@Test
	public void A_getsDBNAuthenticationProperties() {
		PlainTextConnectionUtil connector = PlainTextConnectionUtil.getInstance();
		
		assertNotNull(connector);
	}
	
	@Test
	public void B_authenticatesConnection() {
		boolean authenticates = false;
		
		try(Connection conn = PlainTextConnectionUtil.getInstance().getConnection()) {
			authenticates = true;
		} catch (SQLException e) {}
		
		assertTrue(authenticates);
	}
	
}
