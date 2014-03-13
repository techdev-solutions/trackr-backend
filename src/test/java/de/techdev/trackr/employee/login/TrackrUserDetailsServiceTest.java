package de.techdev.trackr.employee.login;

import de.techdev.trackr.employee.Employee;
import de.techdev.trackr.employee.login.TrackrUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Moritz Schulze
 */
public class TrackrUserDetailsServiceTest {

    private TrackrUserDetailsService trackrUserDetailsService;

    @Before
    public void setUp() throws Exception {
        trackrUserDetailsService = new TrackrUserDetailsService();
    }

    @Test
    public void createDeactivatedEmployee() throws Exception {
        Employee employee = trackrUserDetailsService.createDeactivatedEmployee("moritz.schulze@techdev.de", "Moritz", "Schulze");
        assertThat(employee.getFirstName(), is("Moritz"));
        assertThat(employee.getLastName(), is("Schulze"));
        assertThat(employee.getCredential(), isNotNull());
        assertThat(employee.getCredential().getEmail(), is("moritz.schulze@techdev.de"));
        assertThat("New employee must be disabled", employee.getCredential().getEnabled(), is(false));
    }

    @Test
    public void convertOpenIdAttributesToMap() throws Exception {
        OpenIDAuthenticationToken tokenMock = getOpenIDAuthenticationTokenMock();
        Map<String,String> attributeMap = trackrUserDetailsService.convertOpenIdAttributesToMap(tokenMock);
        assertThat(attributeMap.get("email"), is("moritz.schulze@techdev.de"));
        assertThat(attributeMap.get("first"), is("Moritz"));
    }

    protected OpenIDAuthenticationToken getOpenIDAuthenticationTokenMock() {
        OpenIDAttribute email = new OpenIDAttribute("email", "type", asList("moritz.schulze@techdev.de"));
        OpenIDAttribute firstName = new OpenIDAttribute("first", "type", asList("Moritz"));
        List<OpenIDAttribute> attributes = asList(email, firstName);
        OpenIDAuthenticationToken tokenMock = mock(OpenIDAuthenticationToken.class);
        when(tokenMock.getAttributes()).thenReturn(attributes);
        return tokenMock;
    }
}
