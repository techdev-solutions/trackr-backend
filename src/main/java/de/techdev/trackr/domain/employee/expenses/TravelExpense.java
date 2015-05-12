package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.employee.expenses.reports.Report;
import de.techdev.trackr.domain.validation.constraints.EndAfterBegin;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Getter
@Setter
@Entity
@EndAfterBegin(begin = "fromDate", end = "toDate")
public class TravelExpense {

    public enum Type {
        HOTEL, TAXI, FLIGHT, TRAIN, PARKING, OEPNV, MILEAGE_ALLOWANCE, OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    @NotNull
    @ManyToOne
    private Report report;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

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

    private String comment;
}
