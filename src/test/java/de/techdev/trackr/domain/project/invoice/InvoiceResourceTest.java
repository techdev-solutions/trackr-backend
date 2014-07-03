package de.techdev.trackr.domain.project.invoice;

import de.techdev.trackr.core.security.AuthorityMocks;
import de.techdev.trackr.domain.AbstractDomainResourceTest;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class InvoiceResourceTest extends AbstractDomainResourceTest<Invoice> {

    @Override
    protected String getResourceName() {
        return "invoices";
    }

    @Override
    protected String getJsonRepresentation(Invoice invoice) {

        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jg.writeStartObject()
                .write("identifier", invoice.getIdentifier())
                .write("invoiceState", invoice.getInvoiceState().toString())
                .write("invoiceTotal", invoice.getInvoiceTotal())
                .write("debitor", "/companies/" + invoice.getDebitor().getId())
                .write("creationDate", sdf.format(invoice.getCreationDate()));
        if (invoice.getDueDate() != null) {
            jg.write("dueDate", sdf.format(invoice.getDueDate()));
        }
        if (invoice.getId() != null) {
            jg.write("id", invoice.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }

    @Test
    public void rootIsAccessibleForAdmin() throws Exception {
        assertThat(root(adminSession()), isAccessible());
    }

    @Test
    public void rootIsForbiddenForSupervisor() throws Exception {
        assertThat(root(supervisorSession()), isForbidden());
    }

    @Test
    public void oneIsAccessibleForAdmin() throws Exception {
        assertThat(one(adminSession()), isAccessible());
    }

    @Test
    public void oneIsForbiddenForSupervisor() throws Exception {
        assertThat(one(employeeSession()), isForbidden());
    }

    @Test
    public void findByInvoiceStateIsAccessibleForAdmin() throws Exception {
        mockMvc.perform(
                get("/invoices/search/findByInvoiceState")
                        .param("state", "OUTSTANDING")
                        .session(adminSession())
        )
                .andExpect(status().isOk());
    }

    @Test
    public void findByInvoiceStateIsForbiddenForSupervisor() throws Exception {
        mockMvc.perform(
                get("/invoices/search/findByInvoiceState")
                        .param("state", "OUTSTANDING")
                        .session(supervisorSession())
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void findByIdentifierLikeAndInvoiceStateIsAccessibleForAdmin() throws Exception {
        mockMvc.perform(
                get("/invoices/search/findByIdentifierLikeIgnoreCaseAndInvoiceState")
                        .param("identifier", "TEST")
                        .param("state", "OUTSTANDING")
                        .session(adminSession())
        )
                .andExpect(status().isOk());
    }

    @Test
    public void findByIdentifierLikeAndInvoiceStateIsForbiddenForSupervisor() throws Exception {
        mockMvc.perform(
                get("/invoices/search/findByIdentifierLikeIgnoreCaseAndInvoiceState")
                        .param("identifier", "TEST")
                        .param("state", "OUTSTANDING")
                        .session(supervisorSession())
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void findByCreationDateBetweenAccessibleForAdmin() throws Exception {
        mockMvc.perform(
                get("/invoices/search/findByCreationDateBetween")
                        .param("start", String.valueOf(new Date().getTime()))
                        .param("end", String.valueOf(new Date().getTime()))
                        .session(adminSession())
        )
                .andExpect(status().isOk());
    }

    @Test
    public void findByCreationDateBetweenForbiddenForSupervisor() throws Exception {
        mockMvc.perform(
                get("/invoices/search/findByCreationDateBetween")
                        .param("start", String.valueOf(new Date().getTime()))
                        .param("end", String.valueOf(new Date().getTime()))
                        .session(supervisorSession())
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminCanCreate() throws Exception {
        assertThat(create(adminSession()), isCreated());
    }

    @Test
    public void supervisorCannotCreate() throws Exception {
        assertThat(create(supervisorSession()), isForbidden());
    }

    @Test
    public void adminCanDelete() throws Exception {
        assertThat(remove(adminSession()), isNoContent());
    }

    @Test
    public void supervisorCannotDelete() throws Exception {
        assertThat(remove(supervisorSession()), isForbidden());
    }

    @Test
    public void adminCanSetPaid() throws Exception {
        Invoice invoice = dataOnDemand.getRandomObject();
        invoice.setInvoiceState(InvoiceState.OUTSTANDING);
        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.adminAuthentication());
        repository.save(invoice);
        SecurityContextHolder.getContext().setAuthentication(null);
        mockMvc.perform(
                post("/invoices/" + invoice.getId() + "/markPaid")
                        .session(adminSession())
        );
        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.adminAuthentication());
        Invoice one = repository.findOne(invoice.getId());
        SecurityContextHolder.getContext().setAuthentication(null);
        assertThat(one.getInvoiceState(), is(InvoiceState.PAID));
    }

    @Test
    public void supervisorCannotSetPaid() throws Exception {
        Invoice invoice = dataOnDemand.getRandomObject();
        mockMvc.perform(
                post("/invoices/" + invoice.getId() + "/markPaid")
                        .session(supervisorSession())
        ).andExpect(status().isForbidden());
    }
}
