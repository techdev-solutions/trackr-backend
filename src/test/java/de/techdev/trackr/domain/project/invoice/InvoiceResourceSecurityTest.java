package de.techdev.trackr.domain.project.invoice;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static de.techdev.test.rest.DomainResourceTestMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Sql("resourceTest.sql")
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@OAuthRequest("ROLE_ADMIN")
public class InvoiceResourceSecurityTest extends AbstractDomainResourceSecurityTest {

    private InvoiceJsonGenerator jsonGenerator = new InvoiceJsonGenerator();

    @Override
    protected String getResourceName() {
        return "invoices";
    }

    @Test
    public void rootIsAccessibleForAdmin() throws Exception {
        assertThat(root(), isAccessible());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void rootIsForbiddenForSupervisor() throws Exception {
        assertThat(root(), isForbidden());
    }

    @Test
    public void oneIsAccessibleForAdmin() throws Exception {
        assertThat(one(0L), isAccessible());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void oneIsForbiddenForSupervisor() throws Exception {
        assertThat(one(0L), isForbidden());
    }

    @Test
    public void findByInvoiceStateIsAccessibleForAdmin() throws Exception {
        assertThat(oneUrl("/invoices/search/findByInvoiceState?state=OUTSTANDING"), isAccessible());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void findByInvoiceStateIsForbiddenForSupervisor() throws Exception {
        assertThat(oneUrl("/invoices/search/findByInvoiceState?state=OUTSTANDING"), isForbidden());
    }

    @Test
    public void findByIdentifierLikeAndInvoiceStateIsAccessibleForAdmin() throws Exception {
        assertThat(oneUrl("/invoices/search/findByIdentifierLikeIgnoreCaseAndInvoiceState?identifier=TEST&state=OUTSTANDING"), isAccessible());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void findByIdentifierLikeAndInvoiceStateIsForbiddenForSupervisor() throws Exception {
        assertThat(oneUrl("/invoices/search/findByIdentifierLikeIgnoreCaseAndInvoiceState?identifier=TEST&state=OUTSTANDING"), isForbidden());
    }

    @Test
    public void findByCreationDateBetweenAccessibleForAdmin() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/invoices/search/findByCreationDateBetween?start=2014-01-01&end=2014-01-31", String.class);
        assertThat(response, isAccessible());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void findByCreationDateBetweenForbiddenForSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/invoices/search/findByCreationDateBetween?start=2014-01-01&end=2014-01-31", String.class);
        assertThat(response, isForbidden());
    }

    @Test
    public void adminCanCreate() throws Exception {
        String json = jsonGenerator.start().withDebitorId(0L).build();
        assertThat(create(json), isCreated());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void supervisorCannotCreate() throws Exception {
        String json = jsonGenerator.start().withDebitorId(0L).build();
        assertThat(create(json), isForbidden());
    }

    @Test
    public void adminCanDelete() throws Exception {
        assertThat(remove(0L), isNoContent());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void supervisorCannotDelete() throws Exception {
        assertThat(remove(0L), isForbidden());
    }

    @Test
    public void adminCanSetPaid() throws Exception {
        ResponseEntity<String> response = restTemplate.postForEntity(host + "/invoices/0/markPaid", null, String.class);
        assertThat(response, isAccessible());
        ResponseEntity<Invoice> invoice = restTemplate.getForEntity(host + "/invoices/0", Invoice.class);
        assertThat(invoice.getBody().getInvoiceState(), is(Invoice.InvoiceState.PAID));
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void supervisorCannotSetPaid() throws Exception {
        ResponseEntity<String> response = restTemplate.postForEntity(host + "/invoices/0/markPaid", null, String.class);
        assertThat(response, isForbidden());
    }
}
