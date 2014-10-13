package de.techdev.trackr.domain.project.worktimes;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.project.Project;
import de.techdev.trackr.domain.validation.constraints.EndAfterBegin;

/**
 * @author Moritz Schulze
 */
@Entity
@Data
@ToString(exclude = {"employee", "project"})
@EndAfterBegin(begin = "startTime", end = "endTime")
public class WorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "employee")
    private Employee employee;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "project")
    private Project project;

    @NotNull
    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String comment;

}
