package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.domain.common.FederalState;
import lombok.Data;

import javax.persistence.*;

import java.time.LocalDate;

/**
 * @author Moritz Schulze
 */
@Entity
@Data
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate day;

    private String name;

    @Enumerated(EnumType.STRING)
    private FederalState federalState;
}
