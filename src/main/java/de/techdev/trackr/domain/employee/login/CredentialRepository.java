package de.techdev.trackr.domain.employee.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * @author Moritz Schulze
 */
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Credential findByEmail(@Param("email") String email);

    @RestResource(exported = false)
    List<Credential> findByAuthorities(Authority authority);
}
