package com.reimbursement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.reimbursement.repo.TicketDAO;

public class PlainTextConnectionUtil {
	
//		Class.forName("org.postgresql.Driver");
	
	private static Logger log = Logger.getLogger(TicketDAO.class);
	
	{
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
	}
	
	//may have to adjust url/////////////////////
		private final String url = "jdbc:postgresql://naugledatabase.ct9itnphbbno.us-east-2.rds.amazonaws.com:5432/postgres?currentSchema=public";
		private final String usr = "nauglesteve";
		private final String pwd = "s123456N";

		// to make a singleton
		private static PlainTextConnectionUtil instance;

		private PlainTextConnectionUtil() {
			super();
			// TODO Auto-generated constructor stub
		}

		public static PlainTextConnectionUtil getInstance() {
			log.info("in PlainTextConnectionUtil.getInstance()");
			if (instance == null) {
				instance = new PlainTextConnectionUtil();
			}
			return instance;
		}

		
		// to create a connection to the db
		public Connection getConnection() throws SQLException {
			log.info("in PlainTextConnectionUtil.getConnection()");
			return DriverManager.getConnection(url, usr, pwd);
		}

}
