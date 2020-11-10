/**
 * Created by Stephen Naugle @ Revature
 */


package com.reimbursement.service;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.reimbursement.controller.NegativeAmountException;
import com.reimbursement.controller.SelfResolverException;
import com.reimbursement.model.Ticket;
import com.reimbursement.repo.TicketDAO;

public class TicketService {
	
	private static Logger log = Logger.getLogger(TicketService.class);
	private TicketDAO ticketDao = new TicketDAO();
	
	public ArrayList<Ticket> getByAuthorId(int authorId){
		log.info("in TicketService.getByAuthorId()");
		return ticketDao.getByAuthorId(authorId);
	}
	
	public ArrayList<Ticket> getAll(){
		log.info("in TicketService.getAll()");
		return ticketDao.getAll();
	}
	
	public Ticket getById(int ticketId) {
		log.info("in TicketService.getById()");
		if(ticketId <= 0)
			return null;
		return ticketDao.getById(ticketId);
	}
	
	public Ticket add(Ticket newTicket) throws NegativeAmountException{
		log.info("in TicketService.add()");
		if(newTicket.getAmount() <= 0 ) {
			throw new NegativeAmountException("Unable to create ticket with a negative or 0 amount. Please try again.");
		}
		return ticketDao.add(newTicket);
	}
	
	public ArrayList<Ticket> update(ArrayList<Ticket> updatedTickets) throws SelfResolverException{
		log.info("in TicketService.update()");
		for(Ticket t : updatedTickets) {
			if(t.getAuthorId() == t.getResolverId()) {
				log.warn("SelfResolverException thrown from TicketService.update()");
				throw new SelfResolverException("Manager cannot approve/deny their own tickets");
			}
		}
		return ticketDao.update(updatedTickets);
	}
	
	public boolean delete(int ticketId) {
		log.info("in TicketService.delete()");
		return ticketDao.delete(ticketId);
	}

}
