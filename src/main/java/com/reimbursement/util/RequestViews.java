/**
 * Created by Stephen Naugle @ Revature
 */


package com.reimbursement.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.reimbursement.model.Principal;

public class RequestViews {
	
private static Logger log = Logger.getLogger(RequestViews.class);
	
	private RequestViews() {
		super();
	}
	
	public static String process(HttpServletRequest request) {
		log.info("in RequestViews.process()");
		
		switch(request.getRequestURI()) {
		
		case "/ReimbursementSystem/login.view":
			log.info("Fetching login.html");
			
			return "partials/login.html";
		
		case "/ReimbursementSystem/register.view":
			log.info("Fetching register.html");
			return "partials/register.html";
		
		case "/ReimbursementSystem/dashboard.view":
			
			Principal principal = (Principal) request.getAttribute("principal");
			log.info("Fetching dashboard.html");
			if(principal == null) {
				log.warn("No principal attribute found on request object");
				return "partials/login.html";
			}
			if(principal.getRole().equalsIgnoreCase("manager")) {
				log.info("principal role is manager");
				return "partials/dashboard.html";
			}
			if(principal.getRole().equalsIgnoreCase("employee")) {
				log.info("principal role is employee");
				return "partials/dashboard.html";
			}								
		
		default: 
			log.info("Invalid view requested");
			return null;
		
		}
	}

}
