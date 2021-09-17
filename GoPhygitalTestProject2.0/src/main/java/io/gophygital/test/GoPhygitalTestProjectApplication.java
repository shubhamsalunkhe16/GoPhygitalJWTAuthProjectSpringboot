package io.gophygital.test;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.gophygital.test.Models.Role;
import io.gophygital.test.Models.User;
import io.gophygital.test.Models.UserRole;
import io.gophygital.test.services.UserService;

@SpringBootApplication
public class GoPhygitalTestProjectApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GoPhygitalTestProjectApplication.class, args);
	}

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Application is running");

		// code to add admin

		/*
		 * User user = new User(); user.setUserId(1);
		 * user.setUsername("admin@gmail.com"); user.setName("Admin");
		 * user.setPassword(bCryptPasswordEncoder.encode("admin12345"));
		 * user.setMobileNo("9517532590"); user.setLanguage("EN");
		 * user.setEmailVerified(true); user.setEnabled(true);
		 * 
		 * Role role = new Role(); role.setRoleId(1); role.setRoleName("ADMIN");
		 * 
		 * UserRole userRole = new UserRole(); userRole.setUserRoleId(1);
		 * userRole.setRole(role); userRole.setUser(user);
		 * 
		 * Set<UserRole> userRoleSet = new HashSet<UserRole>();
		 * userRoleSet.add(userRole);
		 * 
		 * User admin = userService.registerUser(user, userRoleSet);
		 * System.out.println(admin.getUsername());
		 */

	}

}
