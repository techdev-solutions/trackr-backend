package de.techdev.trackr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@Entity
@Data
@ToString(exclude = {"company", "debitor", "workTimes"})
@JsonIgnoreProperties({"workTimes"})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    @NotEmpty
    @Column(unique = true)
    private String identifier;

    @NotEmpty
    private String name;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Min(0)
    private Integer volume;

    @Min(0)
    private BigDecimal hourlyRate;

    @Min(0)
    private BigDecimal dailyRate;

    @Min(0)
    private BigDecimal fixedPrice;

    @ManyToOne
    @JoinColumn(name = "debitor_id")
    private Company debitor;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "project")
    @RestResource(exported = false)
    private List<WorkTime> workTimes = new ArrayList<>();
}
