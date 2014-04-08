package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.employee.Employee;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@Data
@Entity
public class TravelExpenseReport {

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
    private TravelExpenseReportStatus status;
}
