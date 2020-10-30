package com.reimbursement.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;


import org.apache.log4j.Logger;

import com.reimbursement.model.ReimbursementStatusEnum;
import com.reimbursement.model.ReimbursementTypeEnum;
import com.reimbursement.model.Ticket;
import com.reimbursement.util.PlainTextConnectionUtil;


public class TicketDAO implements DAO<Ticket> {
	
private static Logger log = Logger.getLogger(TicketDAO.class);

////////////////////// Resolve getInstance() below ////////////////////////////
	
	public ArrayList<Ticket> ticketMaker(ResultSet results) throws SQLException{
		log.info("in TicketDAO.ticketMaker()");
		ArrayList<Ticket> ticketList = new ArrayList<>();
		
//		Timestamp ts = Timestamp.valueOf(LocalDateTime.now("UTC"));
	
		
		while(results.next()) {
			
			Ticket temp = new Ticket();
			temp.setReimbId(results.getInt("reimb_id"));
			temp.setAuthorId(results.getInt("author_id"));
			temp.setAmount(results.getDouble("reimb_amount"));			
			temp.setStatus(ReimbursementStatusEnum.valueOf(results.getString("reimb_status")));
			temp.setType(ReimbursementTypeEnum.valueOf(results.getString("reimb_type")));
			temp.setResolverId(results.getInt("resolver_id"));
			temp.setTicketDescription(results.getString("reimb_description"));
			
			
			if(results.getTimestamp("reimb_resolved") == null) {
				temp.setTimeResolved(LocalDateTime.now());
			}else {
				temp.setTimeResolved(LocalDateTime.of(2020, 10, 28, 15, 33));
			}
			
			if(results.getTimestamp("reimb_submitted") == null) {                   //Testing getObject instead of getString
				temp.setTimeSubmitted(LocalDateTime.now());
			}else {
				temp.setTimeSubmitted(LocalDateTime.of(2020, 10, 29, 14, 22));
			}
			
			ticketList.add(temp);
		}
		return ticketList;
	}
	
	public ArrayList<Ticket> getByAuthorId(int authorId){
		log.info("in TicketDAO.getByAuthorId()");
		
		String sql = "select * from reimb_view";           //changed from reimb_view to reimb_status_view
		
		try(Connection conn = PlainTextConnectionUtil.getInstance().getConnection()) {
			
				PreparedStatement cstmt = conn.prepareStatement(sql);
				ResultSet rs = cstmt.executeQuery();
				while(rs.next()) {
		//			tickets.add(new Ticket(0, rs.getString("reimb_id"), rs.getInt("reimb_amount"), rs.getTimestamp("reimb_submitted"), rs.getString("reimb_description"), rs.getBlob("reimb_receipt"), rs.getString("reimb_status"), rs.getString("reimb_type"), rs.getString("author_fn"), rs.getString("author_ln")));
				
						return ticketMaker(rs);
					}
						rs.close();
						cstmt.close();
		
//			Commenting out to test another method as Callable is giving me issues			
//			CallableStatement cstmt = conn.prepareCall("{CALL get_reimbursement_by_id(?, ?)}"); //call from SQL not working - troubleshoot
//			cstmt.setInt(1, authorId);
//
//			cstmt.registerOutParameter(2, Types.REF_CURSOR);           //testing the ref_cursor out, may have to change
//			if(!cstmt.execute()) {
//				return ticketMaker((ResultSet)cstmt.getObject(2));
//			}
			
		}catch(SQLException e) {
			log.error(e.getMessage());
		}
		
		
		return null;
	}

	@Override
	public ArrayList<Ticket> getAll() {
		log.info("in TicketDAO.getAll()");
		ArrayList<Ticket> tickets = new ArrayList<>();
	
		String sql = "select * from reimb_view";             //changed from reimb_view to reimb_status_view
		
		try(Connection conn = PlainTextConnectionUtil.getInstance().getConnection()) {
			
			PreparedStatement cstmt = conn.prepareStatement(sql);
			ResultSet rs = cstmt.executeQuery();
			while(rs.next()) {
	//			tickets.add(new Ticket(0, rs.getString("reimb_id"), rs.getInt("reimb_amount"), rs.getTimestamp("reimb_submitted"), rs.getString("reimb_description"), rs.getBlob("reimb_receipt"), rs.getString("reimb_status"), rs.getString("reimb_type"), rs.getString("author_fn"), rs.getString("author_ln")));
			
					return ticketMaker(rs);
				}
					rs.close();
					cstmt.close();
			
//			reimb_id, reimb_amount, reimb_submitted, reimb_resolved , reimb_description, reimb_receipt, s.reimb_status, t.reimb_type,
//	        u.user_first_name
//			CallableStatement cstmt = conn.prepareCall("{CALL get_all_reimbursements(?)}"); //call from SQL not working - troubleshoot
//			
//
//			cstmt.registerOutParameter(1,  Types.REF_CURSOR);         // testing the ref_cursor out, may have to change
//			
			//we want .execute to return false
//			if(!cstmt.execute()) {
//				ResultSet rs = (ResultSet)cstmt.getObject(1);
//		
//				return ticketMaker(rs);
//			}
			
		}catch(SQLException e) {
			log.error(e.getMessage());
		}
		
		return tickets;   //originally it is return null;
	}

	@Override
	public Ticket getById(int ticketId) {
		log.info("in TicketDAO.getBuId()");
		
		Ticket ticket = new Ticket();
		

		
		try(Connection conn = PlainTextConnectionUtil.getInstance().getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ers_reimbursement WHERE reimb_id = ?");
			pstmt.setInt(1, ticketId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ticket.setReimbId(rs.getInt("reimb_id"));
				ticket.setAmount(rs.getDouble("reimb_amount"));
				ticket.setTimeSubmitted(rs.getTimestamp("reimb_submitted").toLocalDateTime());
				ticket.setTimeResolved(rs.getTimestamp("reimb_resolved").toLocalDateTime());
				ticket.setTicketDescription(rs.getString("reimb_description"));
				ticket.setAuthorId(rs.getInt("reimb_author"));
				ticket.setResolverId(rs.getInt("reimb_resolver"));
				
				switch(rs.getInt("reimb_status_id")) {
				case 0:
					ticket.setStatus(ReimbursementStatusEnum.PENDING);
					break;
				case 1:
					ticket.setStatus(ReimbursementStatusEnum.APPROVED);
					break;
				case 2:
					ticket.setStatus(ReimbursementStatusEnum.DENIED);
					break;
				}
				
				switch(rs.getInt("reimb_type_id")) {
				case 1:
					ticket.setType(ReimbursementTypeEnum.LODGING);
					break;
				case 2:
					ticket.setType(ReimbursementTypeEnum.TRAVEL);
					break;
				case 3:
					ticket.setType(ReimbursementTypeEnum.FOOD);
					break;
				case 4:
					ticket.setType(ReimbursementTypeEnum.OTHER);
					break;
				}
			}
			
		}catch(SQLException e) {
			log.error(e.getMessage());
		}
		
		return ticket;
	}

	@Override
	public Ticket add(Ticket obj) {
		log.info("in TicketDAO.add()");
		
		
		try(Connection conn = PlainTextConnectionUtil.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			
			String [] keys = new String[1];
			keys[0] = "reimb_id";
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ers_reimbursement VALUES (0,?,null,null,?,null,?,null,0,?)", keys);
			pstmt.setDouble(1, obj.getAmount());
			pstmt.setString(2, obj.getTicketDescription());
			pstmt.setInt(3, obj.getAuthorId());
			switch(obj.getType()) {
				case LODGING:
					pstmt.setInt(4, 1);
					break;
				case TRAVEL:
					pstmt.setInt(4, 2);
					break;
				case FOOD:
					pstmt.setInt(4, 3);
					break;
				case OTHER:
					pstmt.setInt(4, 4);
					break;
				default:
					return null;
			}
			
			int rowsInserted = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rowsInserted != 0) {
				while(rs.next()) {
					obj.setReimbId(rs.getInt(1));
				}
				conn.commit();
			}
		}catch(SQLException e) {
			log.error(e.getMessage());
		}
		
		if(obj.getReimbId() == 0) {
			return null;
		}
		return obj;
	}

	public ArrayList<Ticket> update(ArrayList<Ticket> updatedTickets) {
		log.info("in TicketDAO.update()");
		ArrayList<Ticket> newTicketList = new ArrayList<>();
		
		
		try(Connection conn = PlainTextConnectionUtil.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "UPDATE ers_reimbursement SET reimb_status_id = ?, reimb_resolver = ? WHERE reimb_id = ?";
			
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for(Ticket t : updatedTickets) {
				pstmt.setInt(1, t.getStatusId());
				pstmt.setInt(2, t.getResolverId());
				pstmt.setInt(3, t.getReimbId());
				
				if(pstmt.executeUpdate() == 0) {
					return null;
				}
				newTicketList.add(t);
			}
			conn.commit();
			return newTicketList;
			
		}catch(SQLException e) {
			log.error(e.getMessage());
		}
		
		return null;
	}

	@Override
	public boolean delete(int id) {
		log.info("in TicketDAO.delete()");
		return false;
	}

}
