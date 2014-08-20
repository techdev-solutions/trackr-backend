package de.techdev.trackr.core.security;

/**
 * @author Moritz Schulze
 */
public interface RemoveTokenService {
    void removeTokens(String clientId, String userName);
}
