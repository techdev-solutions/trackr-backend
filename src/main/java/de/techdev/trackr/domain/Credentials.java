package de.techdev.trackr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Login credentials and authorities for any employee.
 * @author Moritz Schulze
 */
@Data
@ToString(exclude = "employee")
@Entity
@JsonIgnoreProperties({"employee"})
public class Credentials {

    @Id
    private Long id;

    @MapsId
    @OneToOne(mappedBy = "credentials")
    @JoinColumn(name = "id")
    private Employee employee;

    @Column(unique = true)
    private String email;

    private boolean enabled;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities;

}
