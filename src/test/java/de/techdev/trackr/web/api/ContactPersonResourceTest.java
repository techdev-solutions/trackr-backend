package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.ContactPerson;
import de.techdev.trackr.domain.support.ContactPersonDataOnDemand;
import de.techdev.trackr.web.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Moritz Schulze
 */
public class ContactPersonResourceTest extends MockMvcTest {

    @Autowired
    private ContactPersonDataOnDemand contactPersonDataOnDemand;

    @Before
    public void setUp() throws Exception {
        contactPersonDataOnDemand.init();
    }

    /**
     * Root URL is accessible
     *
     * @throws Exception
     */
    @Test
    public void root() throws Exception {
        mockMvc.perform(
                get("/contactPersons")
                        .session(basicSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType));
    }

    /**
     * One contact person is accessible
     *
     * @throws Exception
     */
    @Test
    public void one() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/contactPersons/" + contactPerson.getId())
                        .session(basicSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType))
               .andExpect(jsonPath("id", is(contactPerson.getId().intValue())));
    }

    /**
     * A Supervisor can create contact persons
     *
     * @throws Exception
     */
    @Test
    public void postAllowedForSupervisor() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getNewTransientObject(500);
        mockMvc.perform(
                post("/contactPersons")
                        .session(supervisorSession())
                        .content(generateCompanyJson(contactPerson)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("id", isNotNull()));
    }

    /**
     * A Supervisor can edit contact persons
     *
     * @throws Exception
     */
    @Test
    public void putAllowedForSupervisor() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/contactPersons/" + contactPerson.getId())
                        .session(supervisorSession())
                        .content(generateCompanyJson(contactPerson)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("id", is(contactPerson.getId().intValue())));
    }

    /**
     * An employee can not edit contact persons
     *
     * @throws Exception
     */
    @Test
    public void putNotAllowedForEmployee() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/contactPersons/" + contactPerson.getId())
                        .session(basicSession())
                        .content(generateCompanyJson(contactPerson)))
               .andExpect(status().isForbidden());
    }

    /**
     * A supervisor can edit contact persons via PATCH
     *
     * @throws Exception
     */
    @Test
    public void patchAllowedForSupervisor() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/contactPersons/" + contactPerson.getId())
                        .session(supervisorSession())
                        .content("{\"firstName\": \"Test\"}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("firstName", is("Test")));
    }

    /**
     * An employee can not edit contact persons via PATCH
     *
     * @throws Exception
     */
    @Test
    public void patchNotAllowedForEmployee() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/contactPersons/" + contactPerson.getId())
                        .session(basicSession())
                        .content("{\"firstName\": \"Test\"}"))
               .andExpect(status().isForbidden());
    }

    /**
     * An employee can not create contact persons
     *
     * @throws Exception
     */
    @Test
    public void postNotAllowedForEmployee() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getNewTransientObject(500);
        mockMvc.perform(
                post("/contactPersons")
                        .session(basicSession())
                        .content(generateCompanyJson(contactPerson)))
               .andExpect(status().isForbidden());
    }

    /**
     * Wrong data leads to HTTP 400
     *
     * @throws Exception
     */
    @Test
    public void constraintViolation() throws Exception {
        mockMvc.perform(
                post("/contactPersons")
                        .session(supervisorSession())
                        .content("{}"))
               .andExpect(status().isBadRequest());
    }

    /**
     * A supervisor can delete contact persons
     *
     * @throws Exception
     */
    @Test
    public void deleteAllowedForSupervisor() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/contactPersons/" + contactPerson.getId())
                        .session(supervisorSession()))
               .andExpect(status().isNoContent());
    }

    /**
     * An employee can not delete contact persons
     *
     * @throws Exception
     */
    @Test
    public void deleteForbiddenForEmployee() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/contactPersons/" + contactPerson.getId())
                        .session(basicSession()))
               .andExpect(status().isForbidden());
    }

    protected String generateCompanyJson(ContactPerson contactPerson) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
          .write("firstName", contactPerson.getFirstName())
          .write("lastName", contactPerson.getLastName())
          .write("salutation", contactPerson.getSalutation())
          .write("email", contactPerson.getEmail())
          .write("phone", contactPerson.getPhone())
          .write("company", "/api/companies/" + contactPerson.getCompany().getId());

        if (contactPerson.getId() != null) {
            jg.write("id", contactPerson.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }
}
