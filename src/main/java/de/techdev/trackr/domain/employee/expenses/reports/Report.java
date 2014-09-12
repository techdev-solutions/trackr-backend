package de.techdev.trackr.domain.employee.expenses.reports;

import de.techdev.trackr.domain.company.Company;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.expenses.TravelExpense;
import de.techdev.trackr.domain.employee.expenses.reports.comments.Comment;
import de.techdev.trackr.domain.project.Project;
import de.techdev.trackr.domain.validation.constraints.ProjectBelongsToCompany;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@Data
@Entity
@Table(name = "travelExpenseReport")
@ToString(exclude = {"expenses", "employee", "approver", "comments"})
@ProjectBelongsToCompany(companyField = "debitor")
public class Report {

    public static enum Status {
        SUBMITTED, PENDING, APPROVED, REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    @ManyToOne
    private Employee employee;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    private List<TravelExpense> expenses = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Status status;
    private Instant submissionDate;

    private Instant approvalDate;

    @ManyToOne
    private Employee approver;

    @OneToMany(mappedBy = "travelExpenseReport", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ManyToOne(optional = false)
    private Company debitor;

    @ManyToOne
    private Project project;
}
