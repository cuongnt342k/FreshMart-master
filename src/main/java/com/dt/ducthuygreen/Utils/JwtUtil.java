package com.dt.ducthuygreen.Utils;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {

	//  @Value("${jwt.secret_key}")
	  private final String SECRET_KEY = "Zq4t7wzCFJaNdRfUjXn2r5u8xADGKbPeShVkYp3s6v9yBEHMcQfT";
	
//	  @Value("${jwt.time_expiration}")
	  private Integer TIME_EXPIRATION = 36000;
	
	  public String extractUsername(String token) {
	      return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	  }
	
	  public Date extractExpiration(String token) {
	      return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration();
	  }
	
	  public Boolean isTokenExpired(String token) {
	      return extractExpiration(token).before(new Date());
	  }
	
	  public Boolean validateToken(String token, UserDetails userDetails) {
	      final String username = extractUsername(token);
	      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	  }
	
	  public String generateToken(UserDetails userDetails) {
	      return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
	              .setExpiration(new Date(System.currentTimeMillis() + TIME_EXPIRATION))
	              .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	  }
}