package de.techdev.trackr.core.security.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
/**
 * Thrown if an {@link UserDetailsService} implementation cannot use a {@link User} because missing activation.
 *
 */
public class UsernameNotActivatedException extends AuthenticationException {

    /**
     * generated
     */
    private static final long serialVersionUID = 615241536970823927L;

    /**
     * Constructs a <code>UsernameNotActivatedException</code> with the specified
     * message.
     *
     * @param msg the detail message.
     */
    public UsernameNotActivatedException(String msg) {
        super(msg);
    }

    /**
     * Constructs a {@code UsernameNotActivatedException} with the specified message and root cause.
     *
     * @param msg the detail message.
     * @param t root cause
     */
    public UsernameNotActivatedException(String msg, Throwable t) {
        super(msg, t);
    }

}
