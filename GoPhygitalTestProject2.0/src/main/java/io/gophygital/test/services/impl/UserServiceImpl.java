package io.gophygital.test.services.impl;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import io.gophygital.test.Models.EmailToken;
import io.gophygital.test.Models.User;
import io.gophygital.test.Models.UserRole;
import io.gophygital.test.repositories.EmailTokenRepository;
import io.gophygital.test.repositories.RoleRepository;
import io.gophygital.test.repositories.UserRepository;
import io.gophygital.test.services.EmailService;
import io.gophygital.test.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private EmailTokenRepository emailTokenRepository;

	@Autowired
	private EmailService emailService;

	// method is used to register user
	@Override
	public User registerUser(User user, Set<UserRole> userRoles) throws Exception {

		User userInDB = this.userRepository.findByUsername(user.getUsername());
		if (userInDB != null) {
			throw new Exception("User is already exists in DB...try with new user");
		} else {
			for (UserRole uRole : userRoles) {
				roleRepository.save(uRole.getRole());
			}

			user.getUserRoles().addAll(userRoles);

			userInDB = this.userRepository.save(user);

			EmailToken confirmationToken = new EmailToken(user);

			emailTokenRepository.save(confirmationToken);

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getUsername());
			mailMessage.setSubject("Complete Registration!");

			if (userInDB.getLanguage().equalsIgnoreCase("EN")) {
				mailMessage.setText("Dear " + user.getName() + " ,\n\n\tTo confirm your account, please click here : "
						+ "http://localhost:9292/user/confirm-account?token=" + confirmationToken.getConfirmationToken()
						+ "\n\nThanks");
			} else if (userInDB.getLanguage().equalsIgnoreCase("DE")) {
				mailMessage.setText("Lieber " + user.getName()
						+ " ,\n\ntBitte klicken Sie hier, um lhre E-mail-Address zu besitatigen : "
						+ "http://localhost:9292/user/confirm-account?token=" + confirmationToken.getConfirmationToken()
						+ "\n\nVieien Dank");
			} else {
				mailMessage.setText("Error");
			}

			emailService.sendEmail(mailMessage);

		}
		return userInDB;
	}

	@Override
	public User getUser(String username) {
		User user = this.userRepository.findByUsername(username);
		return user;
	}

	@Override
	public ArrayList<User> getAllUsers() {
		ArrayList<User> allUsers = (ArrayList<User>) this.userRepository.findAllByOrderByUserIdDesc();
		return allUsers;
	}

	public ModelAndView confirmUserAccount(ModelAndView modelAndView, String confirmationToken) {
		System.out.println(confirmationToken);
		EmailToken token = this.emailTokenRepository.findByConfirmationToken(confirmationToken);
		System.out.println(token);

		if (token != null) {
			User user = userRepository.findByUsernameIgnoreCase(token.getUserEntity().getUsername());
			user.setEmailVerified(true);
			userRepository.save(user);
			modelAndView.setViewName("accountVerified");
		} else {
			modelAndView.addObject("message", "The link is invalid or broken!");
			modelAndView.setViewName("error");
		}

		return modelAndView;
	}

	public User enableUser(String username) throws Exception {

		User user = this.getUser(username);
		User enabledUser = null;

		if (user != null) {
			user = userRepository.findByUsername(username);
			user.setEnabled(true);
			enabledUser = userRepository.save(user);

		} else {
			throw new Exception("User not found");

		}

		return enabledUser;
	}

	public User disableUser(String username) throws Exception {

		User user = this.getUser(username);
		User disabledUser = null;

		if (user != null) {
			user = userRepository.findByUsername(username);
			user.setEnabled(false);
			disabledUser = userRepository.save(user);

		} else {
			throw new Exception("User not found");

		}

		return disabledUser;
	}

}
