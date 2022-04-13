package com.as.kasirapi.controller.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.as.kasirapi.model.ResponseModel;
import com.as.kasirapi.model.jwt.JwtRequest;
import com.as.kasirapi.model.jwt.JwtResponse;
import com.as.kasirapi.model.member.Member;
import com.as.kasirapi.repository.MemberRepository;
import com.as.kasirapi.util.JwtTokenUtil;
import com.google.gson.Gson;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@Controller
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil          jwtTokenUtil;

	@Autowired
	private UserDetailsService    jwtInMemoryUserDetailsService;

	@Autowired
	MemberRepository              memberRepository;

	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		try {
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

			final UserDetails userDetails = jwtInMemoryUserDetailsService
					.loadUserByUsername(authenticationRequest.getUsername());

			final String      token       = jwtTokenUtil.generateToken(userDetails);

			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseModel(true, "Success", new JwtResponse(token)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseModel(false, "Failed", e.getMessage()));
		}
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@RequestMapping(value = "/refresh-token", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthRefreshToken(HttpServletRequest request, @RequestBody Member memberReq) {
		try {
			HashMap<String, String> respons            = new HashMap<>();
			final String            requestTokenHeader = request.getHeader("Authorization");

			String                  jwtToken           = null;
			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer "))
				jwtToken = requestTokenHeader.substring(7);

			if (jwtTokenUtil.validateRefreshToken(jwtToken)) {
				Member member = memberRepository.findByEmail(memberReq.getEmail());

				if (member == null)
					throw new Exception("Member not valid");

				Map<String, Object> payload = new HashMap<String, Object>();

				payload.put("username", member.getUsername());
				payload.put("email", member.getEmail());

				Gson         gson        = new Gson();
				String       payloadStr  = gson.toJson(payload);
				final String accessToken = jwtTokenUtil.generateAccessToken(payloadStr);

				respons.put("accessToken", accessToken);
			}

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(true, "Success", respons));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseModel(false, "Failed", "Unable to get JWT Token"));
		} catch (ExpiredJwtException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseModel(false, "Failed", "JWT Token has expired"));
		} catch (SignatureException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseModel(false, "Failed", "Signature wrong"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseModel(false, "Failed", e.getMessage()));
		}
	}

}
