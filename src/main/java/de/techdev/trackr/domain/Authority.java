package de.techdev.trackr.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

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

}
