package de.techdev.trackr.domain.employee.login;

import de.techdev.trackr.domain.AbstractDataOnDemand;

/**
 * @author Moritz Schulze
 */
public class AuthorityDataOnDemand extends AbstractDataOnDemand<Authority> {

    @Override
    protected int getExpectedElements() {
        return 3;
    }

    @Override
    public Authority getNewTransientObject(int i) {
        Authority authority = new Authority();
        authority.setAuthority("authority_" + i);
        authority.setOrder(i);
        return authority;
    }
}
