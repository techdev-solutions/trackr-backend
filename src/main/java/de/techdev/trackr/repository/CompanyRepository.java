package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Moritz Schulze
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {

    public Company findByCompanyId(@Param("companyId") Long companyId);

}
