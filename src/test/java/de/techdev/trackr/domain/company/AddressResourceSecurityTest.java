package de.techdev.trackr.domain.company;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import static de.techdev.test.rest.DomainResourceTestMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Sql("address/resourceTest.sql")
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@OAuthRequest("ROLE_ADMIN")
public class AddressResourceSecurityTest extends AbstractDomainResourceSecurityTest {

    private AddressJsonGenerator jsonGenerator = new AddressJsonGenerator();

    @Override
    protected String getResourceName() {
        return "addresses";
    }

    @Test
    @OAuthRequest
    public void findAllNotExported() throws Exception {
        assertThat(root(), isMethodNotAllowed());
    }

    @Test
    @OAuthRequest
    public void one() throws Exception {
        assertThat(one(0L), isAccessible());
    }

    @Test
    public void createAllowedForAdmin() throws Exception {
        String json = jsonGenerator.start().build();
        assertThat(create(json), isCreated());
    }

    @Test
    public void putAllowedForAdmin() throws Exception {
        String json = jsonGenerator.start().apply(c -> c.setId(0L)).build();
        assertThat(update(0L, json), isUpdated());
    }

    @Test
    public void patchAllowedForAdmin() throws Exception {
        assertThat(updateViaPatch(0L, "{\"street\": \"test\"}"), isUpdated());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void createNotAllowedForSupervisor() throws Exception {
        String json = jsonGenerator.start().build();
        assertThat(create(json), isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void putForbiddenForSupervisor() throws Exception {
        String json = jsonGenerator.start().apply(c -> c.setId(0L)).build();
        assertThat(update(0L, json), isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void patchForbiddenForSupervisor() throws Exception {
        assertThat(updateViaPatch(0L, "{\"street\": \"test\"}"), isForbidden());
    }

    @Test
    public void deleteNotExported() throws Exception {
        assertThat(remove(0L), isMethodNotAllowed());
    }

}
