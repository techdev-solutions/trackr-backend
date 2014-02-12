package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Moritz Schulze
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByCredentials_Email(String email);
}
