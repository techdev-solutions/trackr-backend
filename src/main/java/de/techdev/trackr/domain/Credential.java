package de.techdev.trackr.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

/**
 * Login credentials and authorities for any employee.
 * Does not need a version as it saved 1:1 to an employee. TODO: is that right?
 * @author Moritz Schulze
 */
@Data
@ToString(exclude = "employee")
@EqualsAndHashCode(exclude = "employee")
@Entity
public class Credential {

    @Id
    private Long id;

    @MapsId
    @OneToOne(mappedBy = "credential")
    @JoinColumn(name = "id")
    private Employee employee;

    @Column(unique = true)
    @NotEmpty
    @Email
    private String email;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities;

}
