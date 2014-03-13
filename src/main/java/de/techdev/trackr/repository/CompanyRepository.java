package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @author Moritz Schulze
 */
@RepositoryRestResource(path = "/companies")
public interface CompanyRepository extends JpaRepository<Company, Long> {

    public Company findByCompanyId(@Param("companyId") Long companyId);

    public List<Company> findByNameLikeOrderByNameAsc(@Param("name") String name);

}
