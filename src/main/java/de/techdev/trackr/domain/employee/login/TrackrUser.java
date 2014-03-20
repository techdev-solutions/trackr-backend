package de.techdev.trackr.domain.employee.login;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Locale;

/**
 * @author Moritz Schulze
 */
public class TrackrUser extends User {

    private Long id;

    private Locale locale;

    public TrackrUser(String username, boolean enabled, Collection<? extends GrantedAuthority> authorities, Long id, Locale locale) {
        super(username, "", enabled, true, true, true, authorities);
        this.id = id;
        this.locale = locale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
