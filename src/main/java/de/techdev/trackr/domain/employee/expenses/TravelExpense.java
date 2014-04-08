package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.validation.constraints.EndAfterBegin;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Data
@ToString(exclude = "report")
@Entity
@EndAfterBegin(begin = "fromDate", end = "toDate")
public class TravelExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    @NotNull
    @ManyToOne
    private TravelExpenseReport report;

    @Enumerated(EnumType.STRING)
    private TravelExpenseType type;

    @NotNull
    private BigDecimal cost;

    @NotNull
    private BigDecimal vat;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date toDate;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date submissionDate;
}
