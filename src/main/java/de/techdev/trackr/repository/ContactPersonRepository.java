package de.techdev.trackr.repository;

import de.techdev.trackr.domain.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Moritz Schulze
 */
public interface ContactPersonRepository extends JpaRepository<ContactPerson, Long> {

}
