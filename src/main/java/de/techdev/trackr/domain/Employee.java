package de.techdev.trackr.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Represents any employee of techdev.
 * @author Moritz Schulze
 */
@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Credential credential;
}
