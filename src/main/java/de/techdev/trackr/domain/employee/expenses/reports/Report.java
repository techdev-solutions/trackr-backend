package de.techdev.trackr.domain.employee.expenses.reports;

import de.techdev.trackr.domain.company.Company;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.expenses.TravelExpense;
import de.techdev.trackr.domain.employee.expenses.reports.comments.Comment;
import de.techdev.trackr.domain.project.Project;
import de.techdev.trackr.domain.validation.constraints.ProjectBelongsToCompany;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@Getter
@Setter
@Entity
@Table(name = "travelExpenseReport")
@ProjectBelongsToCompany(companyField = "debitor")
public class Report {

    public enum Status {
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

    @Temporal(TemporalType.TIMESTAMP)
    private Date submissionDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalDate;

    @ManyToOne
    private Employee approver;

    @OneToMany(mappedBy = "travelExpenseReport", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ManyToOne(optional = false)
    private Company debitor;

    @ManyToOne
    private Project project;
}
