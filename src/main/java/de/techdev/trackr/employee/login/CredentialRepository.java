package de.techdev.trackr.employee.login;

import de.techdev.trackr.domain.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Moritz Schulze
 */
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Credential findByEmail(@Param("email") String email);

}
