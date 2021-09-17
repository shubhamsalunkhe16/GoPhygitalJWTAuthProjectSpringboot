package io.gophygital.test.services;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.web.servlet.ModelAndView;

import io.gophygital.test.Models.User;
import io.gophygital.test.Models.UserRole;

public interface UserService {

	public User registerUser(User user, Set<UserRole> userRoles) throws Exception;

	public User getUser(String username);

	public ArrayList<User> getAllUsers();

	public ModelAndView confirmUserAccount(ModelAndView modelAndView, String confirmationToken);

	public User enableUser(String username) throws Exception;

	public User disableUser(String username) throws Exception;

}
