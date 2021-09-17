package io.gophygital.test.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.gophygital.test.Models.JWTRequest;
import io.gophygital.test.Models.JWTResponse;
import io.gophygital.test.Models.User;
import io.gophygital.test.services.CustomUserDetailService;
import io.gophygital.test.util.JWTUtil;

@RestController
@CrossOrigin("*")
public class JWTTokenController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private JWTUtil jwtUtil;

	@PostMapping("/generate-token")
	public ResponseEntity<?> generateToken(@RequestBody JWTRequest jwtRequest) throws Exception {

		try {

			authenticateUser(jwtRequest.getUsername(), jwtRequest.getPassword());

		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("User not found");
		}

		UserDetails userDetails = this.customUserDetailService.loadUserByUsername(jwtRequest.getUsername());
		String token = this.jwtUtil.generateToken(userDetails);
		System.out.println("Token generated");

		return ResponseEntity.ok(new JWTResponse(token));
	}

	private void authenticateUser(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		} catch (DisabledException e) {
			throw new Exception("User is Disabled");
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid User");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@GetMapping("/current-user")
	public User currentUser(Principal principal) {
		return (User) this.customUserDetailService.loadUserByUsername(principal.getName());

	}

}
