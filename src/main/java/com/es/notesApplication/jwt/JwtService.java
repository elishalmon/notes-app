package com.es.notesApplication.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import com.es.notesApplication.beans.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {
	
	private static String SECRET_KEY = "tnTshOt4wJplPa1QniZdeAjANFRubIxT9RBjUQ75UtiGMdh4v2le6clAJk0q4QH";

	public static String createJwt(User user) {
		
		SignatureAlgorithm sa = SignatureAlgorithm.HS256;
		
		//long nowMillis = System.currentTimeMillis();
		
		//Date now = new Date(nowMillis);
		
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		
		Key signinKey = new SecretKeySpec(apiKeySecretBytes, sa.getJcaName());
		
		JwtBuilder builder = Jwts.builder()
				//.setIssuedAt(now)
				.claim("userId", String.valueOf(user.getId()))
				.claim("email", user.getEmail())
				.claim("name", user.getName())
				.signWith(sa, signinKey);
		return builder.compact();
	}
	
	public static int decodeJWT(String auth) throws Exception {
		String[] splittedAuth = auth.split("Bearer ");
		if(splittedAuth.length <= 1 || splittedAuth[1] == null) {
			throw new Exception("Token is not exist");
		}
		
		String token = splittedAuth[1];
		Claims claims = Jwts.parser()
				.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
				.parseClaimsJws(token).getBody();
		
		Object claimId = claims.get("userId");
		
		if(claimId == null) {
			throw new Exception("Token is invalid");
		}
		
		return Integer.parseInt((String) claimId);
	}
}
