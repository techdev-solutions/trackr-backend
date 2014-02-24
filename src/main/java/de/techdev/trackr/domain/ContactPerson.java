package de.techdev.trackr.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;

/**
 * @author Moritz Schulze
 */
@Entity
@Data
public class ContactPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;

    @Email
    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private String salutation;

}
