package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.employee.Employee;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Entity
@Data
public class TravelExpenseReportComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String text;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date submissionDate;

    @ManyToOne
    @NotNull
    private TravelExpenseReport travelExpenseReport;

    @ManyToOne
    @NotNull
    private Employee employee;
}

