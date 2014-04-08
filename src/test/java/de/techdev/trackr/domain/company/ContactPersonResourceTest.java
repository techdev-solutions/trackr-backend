package de.techdev.trackr.domain.company;

import de.techdev.trackr.domain.AbstractDomainResourceTest;
import org.junit.Test;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class ContactPersonResourceTest extends AbstractDomainResourceTest<ContactPerson> {


    @Override
    protected String getResourceName() {
        return "contactPersons";
    }

    /**
     * Root URL is accessible
     *
     * @throws Exception
     */
    @Test
    public void root() throws Exception {
        assertThat(root(employeeSession()), isAccessible());
    }

    /**
     * One contact person is accessible
     *
     * @throws Exception
     */
    @Test
    public void one() throws Exception {
        assertThat(one(employeeSession()), isAccessible());
    }

    /**
     * A Supervisor can create contact persons
     *
     * @throws Exception
     */
    @Test
    public void postAllowedForSupervisor() throws Exception {
        assertThat(create(supervisorSession()), isCreated());
    }

    /**
     * A Supervisor can edit contact persons
     *
     * @throws Exception
     */
    @Test
    public void putAllowedForSupervisor() throws Exception {
        assertThat(update(supervisorSession()), isUpdated());
    }

    /**
     * An employee can not edit contact persons
     *
     * @throws Exception
     */
    @Test
    public void putNotAllowedForEmployee() throws Exception {
        assertThat(update(employeeSession()), isForbidden());
    }

    /**
     * A supervisor can edit contact persons via PATCH
     *
     * @throws Exception
     */
    @Test
    public void patchAllowedForSupervisor() throws Exception {
        assertThat(updateViaPatch(supervisorSession(), "{\"firstName\": \"Test\"}"), isUpdated());
    }

    /**
     * An employee can not edit contact persons via PATCH
     *
     * @throws Exception
     */
    @Test
    public void patchNotAllowedForEmployee() throws Exception {
        assertThat(updateViaPatch(employeeSession(), "{\"firstName\": \"Test\"}"), isForbidden());
    }

    /**
     * An employee can not create contact persons
     *
     * @throws Exception
     */
    @Test
    public void postNotAllowedForEmployee() throws Exception {
        assertThat(create(employeeSession()), isForbidden());
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
        assertThat(remove(supervisorSession()), isNoContent());
    }

    /**
     * An employee can not delete contact persons
     *
     * @throws Exception
     */
    @Test
    public void deleteForbiddenForEmployee() throws Exception {
        assertThat(remove(employeeSession()), isForbidden());
    }

    @Override
    protected String getJsonRepresentation(ContactPerson contactPerson) {
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
