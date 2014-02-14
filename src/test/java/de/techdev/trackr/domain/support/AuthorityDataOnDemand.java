package de.techdev.trackr.domain.support;

import de.techdev.trackr.domain.Authority;
import org.springframework.stereotype.Component;

/**
 * @author Moritz Schulze
 */
@Component
public class AuthorityDataOnDemand extends AbstractDataOnDemand<Authority> {

    public Authority getRandomAuthority() {
        init();
        Authority obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return repository.findOne(id);
    }

    @Override
    public Authority getNewTransientObject(int i) {
        Authority authority = new Authority();
        authority.setAuthority("authority_" + i);
        return authority;
    }
}
