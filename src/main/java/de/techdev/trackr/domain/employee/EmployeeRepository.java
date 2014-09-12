package de.techdev.trackr.domain.employee;

import de.techdev.trackr.domain.common.FederalState;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Moritz Schulze
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Override
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or #id == principal?.id")
    Employee findOne(@Param("id") Long id);

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

    @RestResource(exported = false)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<Employee> findByLeaveDateAndCredential_Enabled(LocalDate leaveDate, Boolean credentialEnabled);

    @RestResource(exported = false)
    List<Employee> findByFederalState(FederalState berlin);

    /**
     * Not exported find all method to access all employees without the need for SUPERVISOR/ADMIN rights.
     *
     * Filters the admin out. Only used for the address book.
     */
    @RestResource(exported = false)
    @Query("select e from Employee e where e.credential.email <> 'admin@techdev.de'")
    Page<Employee> findAllForAddressBook(Pageable pageable);
}
