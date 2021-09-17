package io.gophygital.test.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.gophygital.test.Models.EmailToken;

@Repository("emailTokenRepository")
public interface EmailTokenRepository extends CrudRepository<EmailToken, String> {
	EmailToken findByConfirmationToken(String confirmationToken);
}
