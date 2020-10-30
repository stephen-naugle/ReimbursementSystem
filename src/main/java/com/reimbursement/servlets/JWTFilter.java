package com.reimbursement.servlets;

import java.io.IOException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpFilter;

import org.apache.log4j.Logger;

import com.reimbursement.model.Principal;
import com.reimbursement.util.JWTConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

////// compare to see why error is popping up /////////////

@WebFilter("/*")
public class JWTFilter extends HttpFilter {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(JWTFilter.class);

	//@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
		log.info("Inside of JWTFilter.doFilter()");
		
		// 1. Get the HTTP header named "Authorization"
		String header = req.getHeader(JWTConfig.HEADER);
		log.info(header);
		
		// 2. Validate the header values and check the prefix
		if(header == null || !header.startsWith(JWTConfig.PREFIX)) {
			log.info("Request originates from an unauthenticated origin");
			
			// 2.1: If there is no header, or one that we provided, then go to the next step in the filter chain (target servlet)
			chain.doFilter(req, resp);
			return;
		}
		
		// 3. Get the token
		String token = header.replaceAll(JWTConfig.PREFIX, "");
		
		log.info("Request is authenteticated");
		try {
			
			// 4. Validate the token
			Claims claims = Jwts.parser()
					.setSigningKey(JWTConfig.signingKey)
					.parseClaimsJws(token)
					.getBody();
			
			// 5. Obtain the principal/subject stored in the JWT
			Principal principal = new Principal();
			principal.setId(claims.getId());
			principal.setRole(claims.get("role", String.class));
			
			// 6. Attach an attribute to the request indicating information about the principal
			req.setAttribute("principal", principal);
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		// 7. Send the request to the next filter in the chain (target servlet)
		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
