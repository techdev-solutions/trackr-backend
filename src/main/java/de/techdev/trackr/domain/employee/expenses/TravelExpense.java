package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.employee.expenses.reports.Report;
import de.techdev.trackr.domain.validation.constraints.EndAfterBegin;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

/**
 * @author Moritz Schulze
 */
@Data
@ToString(exclude = "report")
@Entity
@EndAfterBegin(begin = "fromDate", end = "toDate")
public class TravelExpense {

    public static enum Type {
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
    private LocalDate fromDate;

    @NotNull
    private LocalDate toDate;

    @NotNull
    private Instant submissionDate;

    private String comment;
}
