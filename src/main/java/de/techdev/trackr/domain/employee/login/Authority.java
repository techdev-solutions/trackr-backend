package de.techdev.trackr.domain.employee.login;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;

/**
 * @author Moritz Schulze
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @NotEmpty
    @Column(unique = true)
    private String authority;

    @DecimalMin(value = "0")
    @Column(name = "authorityOrder")
    private Integer order;

}
