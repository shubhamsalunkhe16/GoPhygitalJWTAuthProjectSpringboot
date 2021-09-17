package io.gophygital.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.gophygital.test.Models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
