package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Moritz Schulze
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
