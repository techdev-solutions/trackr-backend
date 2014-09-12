package de.techdev.trackr.domain.project.billtimes;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.project.Project;

/**
 * @author Moritz Schulze
 */
@Entity
@Data
@ToString(exclude = {"employee", "project"})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"employee", "project", "date"}))
public class BillableTime {

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

    @NotNull
    @Min(0)
    private Integer minutes;
}
