package io.gophygital.test.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.gophygital.test.Models.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String username);

	public ArrayList<User> findAll();

	public ArrayList<User> findAllByOrderByUserIdDesc();

	public User findByUsernameIgnoreCase(String username);

}
