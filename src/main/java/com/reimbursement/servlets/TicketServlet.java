/**
 * Created by Stephen Naugle @ Revature
 */


package com.reimbursement.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimbursement.controller.NegativeAmountException;
import com.reimbursement.model.Principal;
import com.reimbursement.model.ReimbursementStatusEnum;
import com.reimbursement.model.ReimbursementTypeEnum;
import com.reimbursement.model.Ticket;
import com.reimbursement.service.TicketService;

@WebServlet("/ticket")
public class TicketServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(TicketServlet.class);
	private TicketService service = new TicketService();
	private Ticket ticket = new Ticket();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		
		
		try {
			PrintWriter pw = resp.getWriter();
			Principal principal = (Principal) req.getAttribute("principal");
			ArrayList<Ticket> tickets = new ArrayList<>();    
			
			if(principal.getRole().equalsIgnoreCase("manager")) {
				resp.setHeader("UserRole", "manager");
				System.out.println("in manager ticket try for id: " + principal.getId());
				tickets = service.getAll();
				for(Ticket t : tickets) {
					System.out.println(t);
				}
				
			}else if(principal.getRole().equalsIgnoreCase("employee")) {
				resp.setHeader("UserRole", "employee");
				System.out.println("in employee ticket try for id: " + principal.getId());
				tickets = service.getByAuthorId(Integer.parseInt(principal.getId()));
				for(Ticket t : tickets) {
					System.out.println(t);
				}				
		
			}
			resp.setHeader("Content-Type", "application/json");
			resp.setHeader("UserId", principal.getId());
			mapper.writeValue(pw, tickets);
			log.info("tickets have been sent");
			
		}catch(JsonProcessingException jpe) {
			log.error(jpe.getMessage());
			
		}catch(Exception e) {
			log.error(e.getMessage());
		}
		
	}

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("in TicketServlet doPost()");
		
		ObjectMapper mapper = new ObjectMapper();
		String[] ticketData = null;
		ArrayList<Ticket> tickets = new ArrayList<>();
		
		try {
			ticketData = mapper.readValue(req.getInputStream(), String[].class);
			for(String s : ticketData) {
				log.info(s);
			}
			
			if(ticketData[0].equals("add")) {
				log.info("adding ticket");
				ticket.setAuthorId(Integer.parseInt(ticketData[1]));
				ticket.setAmount(Double.parseDouble(ticketData[2]));
				ticket.setType(ReimbursementTypeEnum.valueOf(ticketData[3]));
				ticket.setTicketDescription(ticketData[4]);
				
				ticket = service.add(ticket);
				if(ticket != null) {
					resp.setStatus(200);
				}else {
					resp.setStatus(400);
				}
			} else if(ticketData[0].equals("update")) {
				log.info("updating ticket");
				
				ticket.setAuthorId(Integer.parseInt(ticketData[1]));
				ticket.setResolverId(Integer.parseInt(ticketData[2]));
				ticket.setReimbId(Integer.parseInt(ticketData[3]));
				ticket.setStatus(ReimbursementStatusEnum.valueOf(ticketData[4]));
				tickets.add(ticket);
				
				tickets = service.update(tickets);
				
				if(ticket != null) {
					resp.setStatus(200);
				} else {
					resp.setStatus(400);
				}
				
			}

			
			
		} catch(NegativeAmountException nae) {
			log.error(nae.getMessage());
			resp.setStatus(400);
		} catch(Exception e) {
			log.error(e.getMessage());
			resp.setStatus(400);
		}
	}
	

}
