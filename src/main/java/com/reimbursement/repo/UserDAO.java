package com.reimbursement.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.reimbursement.model.User;
import com.reimbursement.model.UserRoleEnum;
//import com.reimbursement.util.PlainTextConnectionUtil;
//import com.reimbursement.util.ConnectionFactory;
import com.reimbursement.util.PlainTextConnectionUtil;

public class UserDAO implements DAO<User>{
	
private static Logger log = Logger.getLogger(UserDAO.class);
	
	public boolean checkForUsername(String username) {
		log.info("in UserDAO.checkForUsername()");
		
		
		try(Connection conn = PlainTextConnectionUtil.getInstance().getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ers_users WHERE ers_username = ?");
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			
			if (!rs.next()) {
				return true;
			}
			
		}catch(SQLException e) {
			log.error(e.getMessage());
		}
		
		return false;
	}
	
	public User getByCredentials(String username, String password){
		log.info("in UserDAO.getByCredentials()");
		
		User user = new User();
		
		try(Connection conn = PlainTextConnectionUtil.getInstance().getConnection()) {
			
			//getting errors when trying to login as a manager
			//Could try a hard-code here for testing purposes
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ers_users u JOIN ers_user_roles r ON u.user_role_id = r.ers_user_role_id WHERE ers_username = ? AND ers_password = ?");
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				user.setUserId(rs.getInt("ers_users_id"));
				user.setUsername(rs.getString("ers_username"));
				user.setPassword(rs.getString("ers_password"));
				user.setFirstName(rs.getString("user_first_name"));
				user.setLastName(rs.getString("user_last_name"));
				user.setEmail(rs.getString("user_email"));
				user.setRole(UserRoleEnum.valueOf(rs.getString("user_role")));
			}
			
			
		}catch(SQLException e) {
			log.error(e.getMessage());
		}
		
		if(user.getUserId() == 0)
			return null;
		return user;
	}
	
	// Basic CRUD methods from DAO -----------------------------------------------------------
	
	@Override
	public ArrayList<User> getAll() {
		log.info("in UserDAO.getAll()");
		
		ArrayList<User> users = new ArrayList<>();
		
		try(Connection conn = PlainTextConnectionUtil.getInstance().getConnection()) {
			
			Statement stmt = conn.createStatement();
			System.out.println("connection made!");
			ResultSet rs = stmt.executeQuery("SELECT * FROM ers_users u JOIN ers_user_roles r ON u.user_role_id = r.ers_user_role_id;");  //getting errors here; may need to take out the semicolon?? 
			
			while(rs.next()) {
				User temp = new User();
				temp.setUserId(rs.getInt("ers_users_id"));
				temp.setUsername(rs.getString("ers_username"));
				temp.setPassword(rs.getString("ers_password"));
				temp.setFirstName(rs.getString("user_first_name"));
				temp.setLastName(rs.getString("user_last_name"));
				temp.setEmail(rs.getString("user_email"));
				temp.setRole(UserRoleEnum.valueOf(rs.getString("user_role")));
				
				users.add(temp);
			}
			
		}catch(SQLException e) {
			log.error(e.getMessage());
		}
		
		return users;
	}

	@Override
	public User getById(int userId) {
		log.info("in UserDAO.getById()");
		
		User user = new User();
		
		try(Connection conn = PlainTextConnectionUtil.getInstance().getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ers_users u JOIN ers_user_roles r ON u.user_role_id = r.ers_user_role_id WHERE ers_users_id = ?");
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				user.setUserId(rs.getInt("ers_users_id"));
				user.setUsername(rs.getString("ers_username"));
				user.setPassword(rs.getString("ers_password"));
				user.setFirstName(rs.getString("user_first_name"));
				user.setLastName(rs.getString("user_last_name"));
				user.setEmail(rs.getString("user_email"));
				user.setRole(UserRoleEnum.valueOf(rs.getString("user_role")));
			}
			
			
		}catch(SQLException e) {
			log.error(e.getMessage());
		}
		
		if(user.getUserId() == 0)
			return null;
		return user;
	}

	
	@Override
	public User add(User newUser) {
		log.info("in UserDAO.add()");
		
		try(Connection conn = PlainTextConnectionUtil.getInstance().getConnection()) {          // testing this out, may have to change
			conn.setAutoCommit(false);
			
			String[] keys = new String[1];
			keys[0] = "ers_users_id";
			
			//the value for the Role_id is set to 1, for employee, as manager users must be created on the DB side
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ers_users VALUES (0, ?, ?, ?, ?, ?, 2)", keys);
			pstmt.setString(1, newUser.getUsername());
			pstmt.setString(2, newUser.getPassword());
			pstmt.setString(3, newUser.getFirstName());
			pstmt.setString(4, newUser.getLastName());
			pstmt.setString(5, newUser.getEmail());
			
			int rowsInserted = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if(rowsInserted != 0) {
				while(rs.next()) {
					newUser.setUserId(rs.getInt(1));
				}
				
				conn.commit();
			}
			
		}catch(SQLIntegrityConstraintViolationException sie){
			log.error(sie.getMessage());
		}catch(SQLException e) {
			log.error(e.getMessage());
		}
		
		if(newUser.getUserId() == 0) {
			return null;
		}
		return newUser;
	}

	public ArrayList<User> update(User updatedObj) {
		log.info("in UserDAO.update()");
		
		return null;
	}

	@Override
	public boolean delete(int id) {
		log.info("in UserDAO.delete()");
		
		return false;
	}

}
