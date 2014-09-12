package de.techdev.trackr.domain.employee.expenses.reports.comments;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.expenses.reports.Report;
import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.Instant;

/**
 * @author Moritz Schulze
 */
@Entity
@Data
@Table(name = "travelExpenseReportComment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String text;

    @NotNull
    private Instant submissionDate;

    @ManyToOne
    @NotNull
    private Report travelExpenseReport;

    @ManyToOne
    @NotNull
    private Employee employee;
}

