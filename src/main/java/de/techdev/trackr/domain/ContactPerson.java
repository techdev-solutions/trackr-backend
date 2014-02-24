package de.techdev.trackr.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;

    @Email
    private String email;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private String phone;

    @NotEmpty
    private String salutation;

}
