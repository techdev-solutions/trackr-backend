package de.techdev.trackr.domain.company;

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

    public List<Company> findByNameLikeIgnoreCaseOrderByNameAsc(@Param("name") String name);

}
