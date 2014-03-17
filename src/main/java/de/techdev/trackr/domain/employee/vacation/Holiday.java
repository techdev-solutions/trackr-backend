package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.domain.common.FederalState;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Entity
@Data
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date day;

    private String name;

    @Enumerated(EnumType.STRING)
    private FederalState federalState;
}
