package de.techdev.trackr.domain.project;

import de.techdev.trackr.domain.employee.Employee;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    @Temporal(TemporalType.DATE)
    private Date date;

    @NotNull
    @Min(0)
    private Integer minutes;
}
