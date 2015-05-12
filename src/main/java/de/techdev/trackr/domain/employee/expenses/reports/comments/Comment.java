package de.techdev.trackr.domain.employee.expenses.reports.comments;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.expenses.reports.Report;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "travelExpenseReportComment")
public class Comment {

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
    private Report travelExpenseReport;

    @ManyToOne
    @NotNull
    private Employee employee;
}

