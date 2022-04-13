package com.as.kasirapi.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.as.kasirapi.service.AuthService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtTokenUtil implements Serializable {

	@Autowired
	private AuthService       jwtUserDetailsService;

	private static final long serialVersionUID   = -2550185165626007488L;

	public static final long  JWT_TOKEN_VALIDITY = 100000000;

	private static final long ACCESS_TOKEN_EXP   = 300;

	private static final long REFRESH_TOKEN_EXP  = 86400;

	@Value("${jwt.secret}")
	private String            secret;

	@Value("${jwt.secret.access}")
	private String            secretAccess;

	@Value("${jwt.secret.refresh}")
	private String            secretRefresh;

	public String getPayloadFromToken(String token, String secret) {
		return getClaimFromToken(token, secret, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token, String secret) {
		return getClaimFromToken(token, secret, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token, String secret) {
		return getClaimFromToken(token, secret, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, String secret, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token, secret);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token, String secret) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token, String secret) {
		final Date expiration = getExpirationDateFromToken(token, secret);
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateToken(UserDetails userDetails) throws ParseException {
		SimpleDateFormat    sdf    = new SimpleDateFormat("dd-MM-yyyy");
		Date                date   = sdf.parse("31-12-2070");
//		Date                date   = sdf.parse("07-04-2022");
		Map<String, Object> claims = new HashMap<>();
		String              token  = Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date()).setExpiration(date).signWith(SignatureAlgorithm.HS512, secret).compact();
		return token;
	}

	public String generateAccessToken(String payload) {
		Map<String, Object> claims      = new HashMap<>();
		String              accessToken = Jwts.builder().setClaims(claims).setSubject(payload)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXP * 1000))
				.signWith(SignatureAlgorithm.HS512, secretAccess).compact();
		return accessToken;
	}

	public String generateRefreshToken(String payload) {
		Map<String, Object> claims       = new HashMap<>();
		String              refreshToken = Jwts.builder().setClaims(claims).setSubject(payload)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXP * 1000))
				.signWith(SignatureAlgorithm.HS512, secretRefresh).compact();
		return refreshToken;
	}

	public Boolean canTokenBeRefreshed(String token, String secret) {
		return (!isTokenExpired(token, secret) || ignoreTokenExpiration(token));
	}

	public Boolean validateToken(String token) throws Exception {
		try {
			final String username    = getPayloadFromToken(token, secret);
			UserDetails  userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

			return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, secret));
		} catch (IllegalArgumentException e) {
			throw new Exception("Unable to get JWT Token");
		} catch (ExpiredJwtException e) {
			throw new Exception("JWT Token has expired");
		} catch (SignatureException e) {
			throw new Exception("Signature wrong");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public Boolean validateRefreshToken(String token) throws Exception {
		try {
			final String payload = getPayloadFromToken(token, secretRefresh);

			return (payload != null && !isTokenExpired(token, secretRefresh));
		} catch (IllegalArgumentException e) {
			throw new Exception("Unable to get JWT Token");
		} catch (ExpiredJwtException e) {
			throw new Exception("JWT Token has expired");
		} catch (SignatureException e) {
			throw new Exception("Signature wrong");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public Boolean validateAccessToken(String token) throws Exception {
		try {
			final String payload = getPayloadFromToken(token, secretAccess);

			return (payload != null && !isTokenExpired(token, secretAccess));
		} catch (IllegalArgumentException e) {
			throw new Exception("Unable to get JWT Token");
		} catch (ExpiredJwtException e) {
			throw new Exception("JWT Token has expired");
		} catch (SignatureException e) {
			throw new Exception("Signature wrong");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}
}
