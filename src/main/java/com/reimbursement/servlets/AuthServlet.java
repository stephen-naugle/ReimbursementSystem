package com.reimbursement.servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.reimbursement.model.User;
import com.reimbursement.model.UserRoleEnum;
import com.reimbursement.service.UserService;
import com.reimbursement.util.JWTConfig;
import com.reimbursement.util.JWTGenerator;
import com.web.exceptions.ConflictingUserException;
import com.web.exceptions.InvalidInputException;
import com.web.exceptions.UserNotFoundException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(AuthServlet.class);
	private UserService service = new UserService();
	private User user = new User();


	/**
	 * The doPut method handles login and registration verification
	 */

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String[] credentials = null;

		try {	
			credentials = mapper.readValue(req.getInputStream(), String[].class);
			System.out.println(credentials[0]);
			if(credentials[0].equals("login")) {
				log.info("logging in");
				user = service.getByCredentials(credentials[1], credentials[2]);
				resp.addHeader("status", "logged in");
				

			} else if(credentials[0].equals("register")){
				log.info("in registration");
				user.setFirstName(credentials[1]);
				user.setLastName(credentials[2]);
				user.setUsername(credentials[3]);
				user.setPassword(credentials[4]);
				user.setEmail(credentials[5]);
				user.setRole(UserRoleEnum.EMPLOYEE);
				user = service.add(user);
				resp.addHeader("status", "user created");
			}

				
				resp.setStatus(200);
				resp.addHeader(JWTConfig.HEADER, JWTConfig.PREFIX + JWTGenerator.createJwt(user));
				resp.addHeader("userId", String.valueOf(user.getUserId()));
				resp.addHeader("username", user.getUsername());
			
		}catch (ConflictingUserException e1) {
			resp.addHeader("status", "username taken");
			log.info(e1.getMessage());
			resp.setStatus(400);
		} catch (InvalidInputException e1) {
			resp.addHeader("status", "invalid input");
			log.info(e1.getMessage());
			resp.setStatus(400);
		} catch (UserNotFoundException e) {
			resp.addHeader("status", "user not found");
			log.info(e.getMessage());
			resp.setStatus(401);
		} catch (MismatchedInputException mie) {
			log.error(mie.getMessage());
			resp.addHeader("status", "mismatched input");
			resp.setStatus(400);
		} catch (Exception e) {
			log.error(e.getMessage());
			resp.addHeader("status", "server error");
			resp.setStatus(500);
		}
	}

}
