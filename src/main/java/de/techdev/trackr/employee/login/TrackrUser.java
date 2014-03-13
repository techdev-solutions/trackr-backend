package de.techdev.trackr.employee.login;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author Moritz Schulze
 */
public class TrackrUser extends User {

    private Long id;

    public TrackrUser(String username, boolean enabled, Collection<? extends GrantedAuthority> authorities, Long id) {
        super(username, "", enabled, true, true, true, authorities);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
