package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author Moritz Schulze
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Override
    //We have to use the @Param name here, not the variable name! How about that, only cost me 2 hours!!!
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or (isAuthenticated() and #id == principal.id)")
    Employee findOne(@Param("id") Long employeeId);

    @Override
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    List<Employee> findAll();

    @Override
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    List<Employee> findAll(Sort sort);

    @Override
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    List<Employee> findAll(Iterable<Long> longs);

    @Override
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    Page<Employee> findAll(Pageable pageable);
}
