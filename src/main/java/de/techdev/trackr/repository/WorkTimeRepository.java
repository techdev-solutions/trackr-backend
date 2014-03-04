package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Employee;
import de.techdev.trackr.domain.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * @author Moritz Schulze
 */
public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {

    List<WorkTime> findByEmployeeAndDateOrderByStartTimeAsc(@Param("employee") Employee employee, @Param("date") @Temporal(TemporalType.DATE) Date date);

    List<WorkTime> findByEmployeeAndDateBetweenOrderByDateAscStartTimeAsc(@Param("employee") Employee employee,
                                                               @Param("start") @Temporal(TemporalType.DATE) Date start,
                                                               @Param("end") @Temporal(TemporalType.DATE) Date end);

}
