package io.gophygital.test.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.gophygital.test.Models.EmailToken;
import io.gophygital.test.Models.Role;
import io.gophygital.test.Models.User;
import io.gophygital.test.Models.UserRole;
import io.gophygital.test.repositories.EmailTokenRepository;
import io.gophygital.test.repositories.UserRepository;
import io.gophygital.test.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	public UserService userService;

	@Autowired
	public UserRepository userRepository;

	@Autowired
	private EmailTokenRepository emailTokenRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/")
	public ResponseEntity<?> registerUser(@RequestBody User user) throws Exception {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		Role role = new Role();
		role.setRoleId(2);
		role.setRoleName("USER");

		UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);

		Set<UserRole> userRoleSet = new HashSet<>();
		userRoleSet.add(userRole);

		try {
			User userRegister = this.userService.registerUser(user, userRoleSet);

			return new ResponseEntity<>(userRegister, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>("UserAlreadyFoundException", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/{username}")
	public User getUser(@PathVariable("username") String username) {
		User user = this.userService.getUser(username);
		return user;
	}

	@GetMapping("/allUsers")
	public ArrayList<User> getAllUser() {
		ArrayList<User> allUsers = this.userService.getAllUsers();
		return allUsers;
	}

	@RequestMapping(value = "/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
		ModelAndView confirmModelAndView = this.userService.confirmUserAccount(modelAndView, confirmationToken);
		return confirmModelAndView;

	}

	@PutMapping("/enable-user/{username}")
	public User enableUser(@PathVariable("username") String username) throws Exception {
		User enabledUser = this.userService.enableUser(username);
		return enabledUser;
	}

	@PutMapping("/disable-user/{username}")
	public User disableUser(@PathVariable("username") String username) throws Exception {
		User disabledUser = this.userService.disableUser(username);
		return disabledUser;
	}

}
