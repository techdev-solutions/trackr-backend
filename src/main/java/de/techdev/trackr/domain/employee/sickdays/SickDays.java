package de.techdev.trackr.domain.employee.sickdays;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.validation.constraints.EndAfterBegin;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * @author Moritz Schulze
 */
@Entity
@Data
@EndAfterBegin(begin = "startDate", end = "endDate")
public class SickDays {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    @ManyToOne
    @NotNull
    private Employee employee;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

}
