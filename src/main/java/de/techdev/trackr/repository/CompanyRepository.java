package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;

/**
 * @author Moritz Schulze
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {

    public Company findByCompanyId(@Param("companyId") String companyId);

}
