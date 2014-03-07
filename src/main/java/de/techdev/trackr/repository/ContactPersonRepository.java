package de.techdev.trackr.repository;

import de.techdev.trackr.domain.ContactPerson;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Moritz Schulze
 */
public interface ContactPersonRepository extends CrudRepository<ContactPerson, Long> {

}
