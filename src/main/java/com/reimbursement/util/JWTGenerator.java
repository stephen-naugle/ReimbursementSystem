package com.reimbursement.util;

import java.util.Date;

import org.apache.log4j.Logger;

import com.reimbursement.model.User;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTGenerator {
	
private static Logger log = Logger.getLogger(JWTGenerator.class);
	
	public static String createJwt(User subject) {
		log.info("in JWTGenerator.createJwt()");
		log.info("Creating new JWT for: " + subject.getUsername());
		
		// The JWT Signature Algorithm used to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		long nowMillis = System.currentTimeMillis();
		
		// Configure the JWT and set its claims
		JwtBuilder builder = Jwts.builder()
				.setId(Integer.toString(subject.getUserId()))
				.setSubject(subject.getUsername())
				.setIssuer("revature")
				.claim("role", subject.getRole())
				.setExpiration(new Date(nowMillis + JWTConfig.EXPIRATION * 1000))
				.signWith(signatureAlgorithm, JWTConfig.signingKey);
		
		log.info("JWT successfully created");
		
		// Build the JWT and serialize it into a compact, URL-safe string
		return builder.compact();
	}
	
	private JWTGenerator() {
		super();
	}

}
