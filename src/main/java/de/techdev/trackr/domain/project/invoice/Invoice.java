package de.techdev.trackr.domain.project.invoice;

import de.techdev.trackr.domain.company.Company;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "identifier"))
@ToString(exclude = "debitor")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    @NotEmpty
    private String identifier;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Min(0)
    private BigDecimal invoiceTotal;

    @ManyToOne
    private Company debitor;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Enumerated(EnumType.STRING)
    private InvoiceState invoiceState;

}
