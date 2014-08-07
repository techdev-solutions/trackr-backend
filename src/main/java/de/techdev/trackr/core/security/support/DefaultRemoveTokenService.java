package de.techdev.trackr.core.security.support;

import de.techdev.trackr.core.security.RemoveTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Collection;

/**
 * @author Moritz Schulze
 */
@Slf4j
public class DefaultRemoveTokenService implements RemoveTokenService {

    @Autowired
    private TokenStore tokenStore;

    /**
     * Remove all tokens for a user belonging to a client.
     */
    @Override
    public void removeTokens(String clientId, String userName) {
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(clientId, userName);
        log.debug("Removing {} access tokens for user {} in client {}.", tokens.size(), userName, clientId);
        tokens.forEach(tokenStore::removeAccessToken);
    }
}
