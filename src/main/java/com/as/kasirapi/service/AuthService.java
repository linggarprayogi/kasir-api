package com.as.kasirapi.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.as.kasirapi.model.auth.UserApi;
import com.as.kasirapi.repository.AuthRepository;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	AuthRepository authRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserApi user = authRepository.findByName(username);

		if (user != null && user.getUsername().equals(username)) {
			return new User(username, user.getPassword().trim(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}
