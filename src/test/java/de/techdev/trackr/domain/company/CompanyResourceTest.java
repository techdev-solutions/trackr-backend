package de.techdev.trackr.domain.company;

import de.techdev.trackr.domain.AbstractDomainResourceTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.*;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Moritz Schulze
 */
public class CompanyResourceTest extends AbstractDomainResourceTest<Company> {

    @Autowired
    private ContactPersonDataOnDemand contactPersonDataOnDemand;

    @Override
    protected String getResourceName() {
        return "companies";
    }

    @Before
    public void setUp() throws Exception {
        contactPersonDataOnDemand.init();
    }

    /**
     * Root is accessible.
     *
     * @throws Exception
     */
    @Test
    public void root() throws Exception {
        assertThat(root(employeeSession()), isAccessible());
    }

    /**
     * One company is accessible
     *
     * @throws Exception
     */
    @Test
    public void one() throws Exception {
        assertThat(one(employeeSession()), isAccessible());
    }

    @Test
    public void findByNameLikeOrderByNameAsc() throws Exception {
        Company company = dataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/companies/search/findByNameLikeOrderByNameAsc")
                        .session(employeeSession())
                        .param("name", company.getName())
        )
               .andExpect(status().isOk())
               .andExpect(content().contentType(STANDARD_CONTENT_TYPE))
               .andExpect(jsonPath("_embedded.companies[0].id", isNotNull()));
    }

    /**
     * A company can be found via the findByCompanyId finder
     *
     * @throws Exception
     */
    @Test
    public void findByCompanyId() throws Exception {
        Company company = dataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/companies/search/findByCompanyId")
                        .param("companyId", company.getCompanyId().toString())
                        .session(employeeSession())
        )
               .andExpect(status().isOk())
               .andExpect(content().contentType(STANDARD_CONTENT_TYPE))
               .andExpect(jsonPath("_embedded.companies[0].companyId", is(company.getCompanyId().intValue())));
    }

    /**
     * An admin can create companies.
     *
     * @throws Exception
     */
    @Test
    public void postAllowedForAdmin() throws Exception {
        assertThat(create(adminSession()), returnsCreated());
    }

    /**
     * An admin can edit companies.
     *
     * @throws Exception
     */
    @Test
    public void putAllowedForAdmin() throws Exception {
        assertThat(update(adminSession()), isUpdated());
    }

    /**
     * An admin can edit companies via PATCH.
     *
     * @throws Exception
     */
    @Test
    public void patchAllowedForAdmin() throws Exception {
        assertThat(updateViaPatch(adminSession(), "{\"name\": \"test\"}"), isUpdated());
    }

    /**
     * A supervisor can not create companies-
     *
     * @throws Exception
     */
    @Test
    public void postForbiddenForSupervisor() throws Exception {
        assertThat(create(supervisorSession()), isForbidden());
    }

    /**
     * A supervisor can not edit companies.
     *
     * @throws Exception
     */
    @Test
    public void putForbiddenForSupervisor() throws Exception {
        assertThat(update(supervisorSession()), isForbidden());
    }

    /**
     * A supervisor can not edit companies via PATCH.
     *
     * @throws Exception
     */
    @Test
    public void patchForbiddenForSupervisor() throws Exception {
        assertThat(updateViaPatch(supervisorSession(), "{\"name\": \"test\"}"), isForbidden());
    }

    /**
     * Wrong data leads to HTTP 400.
     *
     * @throws Exception
     */
    @Test
    public void constraintViolation() throws Exception {
        mockMvc.perform(
                post("/companies")
                        .session(adminSession())
                        .content("{ \"companyId\": \"1234\" }")
        )
               .andExpect(status().isBadRequest());
    }

    /**
     * A supervisor can add a contact person.
     *
     * @throws Exception
     */
    @Test
    public void addContactPersonSupervisor() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        assertThat(updateLink(supervisorSession(), "contactPersons", "/contactPersons/" + contactPerson.getId()), isNoContent());
    }

    /**
     * An employee can not add a contact person.
     *
     * @throws Exception
     */
    @Test
    public void addContactForbiddenForEmployee() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        assertThat(updateLink(employeeSession(), "contactPersons", "/contactPersons/" + contactPerson.getId()), isForbidden());
    }

    /**
     * A supervisor can delete a contact person.
     *
     * @throws Exception
     */
    @Test
    public void deleteContactAllowedForSupervisor() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/companies/" + contactPerson.getCompany().getId() + "/contactPersons/" + contactPerson.getId())
                        .session(supervisorSession())
        )
               .andExpect(status().isNoContent());
    }

    /**
     * An employee can not delete a contact person.
     *
     * @throws Exception
     */
    @Test
    public void deleteContactNotAllowedForEmployee() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/companies/" + contactPerson.getCompany().getId() + "/contactPersons/" + contactPerson.getId())
                        .session(employeeSession())
        )
               .andExpect(status().isForbidden());
    }

    /**
     * An admin can delete companies.
     *
     * @throws Exception
     */
    @Test
    public void deleteAllowedForAdmin() throws Exception {
        assertThat(remove(adminSession()), isNoContent());
    }

    /**
     * A supervisor can not delete companies.
     *
     * @throws Exception
     */
    @Test
    public void deleteForbiddenForSupervisor() throws Exception {
        assertThat(remove(supervisorSession()), isForbidden());
    }

    /**
     * The address of a company is accessible.
     *
     * @throws Exception
     */
    @Test
    public void getAddress() throws Exception {
        Company company = dataOnDemand.getRandomObject();
        assertThat(oneUrl(employeeSession(), "/companies/" + company.getId() + "/address"), isAccessible());
    }

    @Override
    protected String getJsonRepresentation(Company item) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
          .write("name", item.getName())
          .write("companyId", item.getCompanyId())
          .write("address", "/api/addresses/" + item.getAddress().getId());
        if (item.getId() != null) {
            jg.write("id", item.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }
}
