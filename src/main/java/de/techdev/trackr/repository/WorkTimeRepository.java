package de.techdev.trackr.repository;

import de.techdev.trackr.domain.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Moritz Schulze
 */
public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {
}
