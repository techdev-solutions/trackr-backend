package de.techdev.trackr.domain.employee.sickdays;

import de.techdev.trackr.domain.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@RepositoryRestResource(path = "sickDays")
public interface SickDaysRepository extends JpaRepository<SickDays, Long> {

    @Override
    @RestResource(exported = false)
    Page<SickDays> findAll(Pageable pageable);

    @Override
    @PostAuthorize("hasRole('ROLE_ADMIN') or ( isAuthenticated() and principal.id == returnObject.employee.id )")
    SickDays findOne(Long aLong);

    @PreAuthorize("isAuthenticated() and #employee.id == principal.id")
    List<SickDays> findByEmployee(@Param("employee") Employee employee);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<SickDays> findByStartDateBetweenOrEndDateBetween(
            @Param("startLower") Date startLower,
            @Param("startHigher") Date startHigher,
            @Param("endLower") Date endLower,
            @Param("endHigher") Date endHigher
    );

}
