package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Moritz Schulze
 */
public interface CredentialsRepository extends JpaRepository<Credentials, Long> {

    Credentials findByEmail(String email);

}
