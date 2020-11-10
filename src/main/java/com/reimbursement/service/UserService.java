/**
 * Created by Stephen Naugle @ Revature
 */


package com.reimbursement.service;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.reimbursement.controller.ConflictingUserException;
import com.reimbursement.controller.InvalidInputException;
import com.reimbursement.controller.UserNotFoundException;
import com.reimbursement.model.User;
import com.reimbursement.repo.UserDAO;

public class UserService {
	
	private static Logger log = Logger.getLogger(UserService.class);
	private UserDAO userDao = new UserDAO();
	
	public Boolean checkForUsername(String username) {
		return userDao.checkForUsername(username);
	}
	
	public User getByCredentials(String username, String password) throws UserNotFoundException, InvalidInputException{
		log.info("in UserService.getByCredentials()");
		if(username == null || password == null) throw new InvalidInputException("Empty credentials");
		if(isValid(username) && isValid(password)) {
			 User gottenUser = userDao.getByCredentials(username, password);
			 if(gottenUser == null) {
				 throw new UserNotFoundException("No user exists with those credentials");
			 }
			 return gottenUser;
		} else {
			throw new InvalidInputException("Empty credentials");
		}
	}
	
	public ArrayList<User> getAllUsers(){
		log.info("in UserService.getAllUsers()");
		return userDao.getAll();
	}
	
	public User getById(int userId) {
		log.info("in UserService.getById()");
		if(userId <= 0)
			return null;
		return userDao.getById(userId);
	}
	
	private boolean isValid(String value) {
		if(value == null) return false;
		return (value.trim().length() > 1);
	}
	
	public User add(User newUser) throws ConflictingUserException, InvalidInputException{
		log.info("in UserService.add()");
		
		if(isValid(newUser.getUsername()) && 
				isValid(newUser.getEmail()) && 
				isValid(newUser.getFirstName()) &&
				isValid(newUser.getLastName()) &&
				isValid(newUser.getPassword())){
		}else {
			log.warn("InvalidInputException thrown in UserService.add()");
			throw new InvalidInputException("Input is invalid");
		}
		
		ArrayList<User> userList = userDao.getAll();
		for(User u : userList) {
			if(newUser.getUsername().equals(u.getUsername()) || newUser.getEmail().equals(u.getEmail())) {
				log.warn("ConflictingUserException thrown in UserService.add()");
				throw new ConflictingUserException("Username or Email already taken");
			}
		}
		return userDao.add(newUser);
	}
	
	public ArrayList<User> update(User UpdatedUser){
		log.info("in UserService.update()");
		return userDao.update(UpdatedUser);
	}
	
	public boolean delete(int userId) {
		log.info("in UserService.delete()");
		return userDao.delete(userId);
	}

}
