package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Moritz Schulze
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
