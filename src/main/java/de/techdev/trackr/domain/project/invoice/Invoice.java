package de.techdev.trackr.domain.project.invoice;

import de.techdev.trackr.domain.company.Company;
import lombok.Data;
import lombok.ToString;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "identifier"))
@ToString(exclude = "debitor")
public class Invoice {

    public static enum InvoiceState {
        OUTSTANDING, PAID, OVERDUE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    @NotEmpty
    private String identifier;

    @NotNull
    private LocalDate creationDate;

    @Min(0)
    private BigDecimal invoiceTotal;

    @ManyToOne
    @JoinColumn(name = "debitor")
    private Company debitor;

    private LocalDate dueDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private InvoiceState invoiceState;
}
