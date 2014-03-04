package de.techdev.trackr.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Entity
@Data
public class WorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "employee")
    private Employee employee;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "project")
    private Project project;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    private Time start;

    private Time end;

    private String comment;

}
