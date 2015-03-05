package de.techdev.trackr.domain.company;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;

import static de.techdev.test.rest.DomainResourceTestMatchers.*;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;

@Sql("contactPerson/resourceTest.sql")
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@OAuthRequest("ROLE_SUPERVISOR")
public class ContactPersonResourceSecurityTest extends AbstractDomainResourceSecurityTest {

    private ContactPersonJsonGenerator jsonGenerator = new ContactPersonJsonGenerator();

    @Override
    protected String getResourceName() {
        return "contactPersons";
    }

    @Test
    @OAuthRequest
    public void rootAccessible() throws Exception {
        assertThat(root(), isAccessible());
    }

    @Test
    @OAuthRequest
    public void one() throws Exception {
        assertThat(one(0L), isAccessible());
    }

    @Test
    public void postAllowedForSupervisor() throws Exception {
        String json = jsonGenerator.start().withCompanyId(0L).build();
        assertThat(create(json), isCreated());
    }

    @Test
    public void putAllowedForSupervisor() throws Exception {
        String json = jsonGenerator.start().withCompanyId(0L).apply(c -> c.setId(0L)).build();
        assertThat(update(0L, json), isUpdated());
    }

    @Test
    @OAuthRequest
    public void putNotAllowedForEmployee() throws Exception {
        String json = jsonGenerator.start().withCompanyId(0L).apply(c -> c.setId(0L)).build();
        assertThat(update(0L, json), isForbidden());
    }

    @Test
    public void patchAllowedForSupervisor() throws Exception {
        assertThat(updateViaPatch(0L, "{\"firstName\": \"Test\"}"), isUpdated());
    }

    @Test
    @OAuthRequest
    public void patchNotAllowedForEmployee() throws Exception {
        assertThat(updateViaPatch(0L, "{\"firstName\": \"Test\"}"), isForbidden());
    }

    @Test
    @OAuthRequest
    public void postNotAllowedForEmployee() throws Exception {
        String json = jsonGenerator.start().withCompanyId(0L).build();
        assertThat(create(json), isForbidden());
    }

//    @Test
//    public void constraintViolation() throws Exception {
//        mockMvc.perform(
//                post("/contactPersons")
//                        .session(supervisorSession())
//                        .content("{}"))
//               .andExpect(status().isBadRequest());
//    }

    @Test
    public void deleteAllowedForSupervisor() throws Exception {
        assertThat(remove(0L), isNoContent());
    }

    @Test
    @OAuthRequest
    public void deleteForbiddenForEmployee() throws Exception {
        assertThat(remove(0L), isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_EMPLOYEE")
    public void updateCompanyForbiddenForEmployee() throws Exception {
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("Content-Type", asList("text/uri-list"));
        HttpEntity<String> requestEntity = new HttpEntity<>("/companies/0", headers);
        ResponseEntity<String> response = restTemplate.exchange(host + "/contactPersons/0/company", HttpMethod.PUT, requestEntity, String.class);
        assertThat(response, isForbidden());
    }

    @Test
    public void updateCompanyAllowedForSupervisor() throws Exception {
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("Content-Type", asList("text/uri-list"));
        HttpEntity<String> requestEntity = new HttpEntity<>("/companies/0", headers);
        ResponseEntity<String> response = restTemplate.exchange(host + "/contactPersons/0/company", HttpMethod.PUT, requestEntity, String.class);
        assertThat(response, isNoContent());
    }

    @Test
    public void deleteCompanyForbiddenForSupervisor() throws Exception {
        assertThat(removeUrl("/contactPersons/0/company"), isForbidden());
    }

}
