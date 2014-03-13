package de.techdev.trackr.employee.login;

import de.techdev.trackr.domain.support.AbstractDataOnDemand;
import de.techdev.trackr.employee.login.Authority;
import org.springframework.stereotype.Component;

/**
 * @author Moritz Schulze
 */
@Component
public class AuthorityDataOnDemand extends AbstractDataOnDemand<Authority> {

    @Override
    protected int getExpectedElements() {
        return 3;
    }

    @Override
    public Authority getNewTransientObject(int i) {
        Authority authority = new Authority();
        authority.setAuthority("authority_" + i);
        authority.setScreenName("screenName_" + i);
        authority.setOrder(i);
        return authority;
    }
}
