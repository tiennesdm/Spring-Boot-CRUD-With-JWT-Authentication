package com.crud.api.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.HashMap;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    @Value("${app.jwt-expiration-milliseconds}")
    private Integer jwtTokenValidity;

    @Value("${app.jwt-secret}")
    private String scretKey;

    @Value("${app.jwt.token-type}")
    private String tokenType;


    public Object generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        HashMap<String, Object> returnMap = new HashMap<>();
        returnMap.put("jwtKey", Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity))
        .signWith(SignatureAlgorithm.HS512, scretKey)
        .compact());
        returnMap.put("expirationTime", jwtTokenValidity);
        returnMap.put("tokenType", tokenType);
        return returnMap;
    }

    public String getUsernameFromToken(String jwtToken) {
		return getClaimFromToken(jwtToken, Claims::getSubject);
	}
	
	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = Jwts.parser().setSigningKey(scretKey).parseClaimsJws(token).getBody();
		return claimsResolver.apply(claims);
	}


	public boolean validateToken(String jwtToken, UserDetails userDetails) {
		
		final String username = getUsernameFromToken(jwtToken);
		
		return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
		
	}


	private boolean isTokenExpired(String jwtToken) {
		final Date expiration = getExpirationDateFromToken(jwtToken);
		return expiration.before(new Date());
	}


	private Date getExpirationDateFromToken(String jwtToken) {
		return getClaimFromToken(jwtToken, Claims::getExpiration);
	}







    
}
