package com.demo.transactionapi.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JwtTokenUtil {

	public static final long JWT_TOKEN_VALIDITY = 60 * 60 * 60 * 24;
	
	private final String secret = "abcgdhds";

	public String generateToken(String subject) {
		String token = "";

		try {
			SignatureAlgorithm algo = SignatureAlgorithm.HS256;
			
			long time = System.currentTimeMillis();
			Date d = new Date(time);
			
			byte[] key = DatatypeConverter.parseBase64Binary(secret);
			Key signKey = new SecretKeySpec(key, algo.getJcaName());
			
			JwtBuilder builder = Jwts.builder().setIssuedAt(d).setSubject(subject).signWith(algo, signKey);
			
			long expiryTime = time + JWT_TOKEN_VALIDITY;
			Date expDate = new Date(expiryTime);
			builder.setExpiration(expDate);
			
			token = builder.compact();
			
		} catch(Exception e) {
			System.out.println(e);
			System.out.println("JWT token generation error");
		}
		
		return token;
	}
	
	public String getContact(String token) {
		String contact="";
		try {
			byte[] key = DatatypeConverter.parseBase64Binary(secret);
			Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
			contact = claims.getSubject();
			return contact;
		}catch(ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
			System.out.println(e);
			return "-1";
		}
	}
	
	
}