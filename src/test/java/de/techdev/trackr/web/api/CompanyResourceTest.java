package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Company;
import de.techdev.trackr.domain.ContactPerson;
import de.techdev.trackr.domain.support.CompanyDataOnDemand;
import de.techdev.trackr.domain.support.ContactPersonDataOnDemand;
import de.techdev.trackr.web.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class CompanyResourceTest extends MockMvcTest {

    private final String companyJson = "{\"companyId\": %d, \"name\": \"techdev\", \"address\": \"/address/0\"}";

    @Autowired
    private CompanyDataOnDemand companyDataOnDemand;

    @Autowired
    private ContactPersonDataOnDemand contactPersonDataOnDemand;

    @Before
    public void setUp() throws Exception {
        companyDataOnDemand.init();
    }

    /**
     * Root is accessible.
     * @throws Exception
     */
    @Test
    public void root() throws Exception {
        mockMvc.perform(
                get("/companies")
                        .session(basicSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType));
    }

    /**
     * One company is accessible
     * @throws Exception
     */
    @Test
    public void one() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/companies/" + company.getId())
                        .session(basicSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType));
    }

    /**
     * A company can be found via the findByCompanyId finder
     * @throws Exception
     */
    @Test
    public void findByCompanyId() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/companies/search/findByCompanyId")
                        .param("companyId", company.getCompanyId().toString())
                        .session(basicSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType));
    }

    /**
     * An admin can create companies.
     * @throws Exception
     */
    @Test
    public void postAllowedForAdmin() throws Exception {
        String json = String.format(companyJson, 12345);
        mockMvc.perform(
                post("/companies")
                        .session(adminSession())
                        .content(json))
               .andExpect(status().isCreated());
    }

    /**
     * An admin can edit companies.
     * @throws Exception
     */
    @Test
    public void putAllowedForAdmin() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        String json = String.format(companyJson, company.getCompanyId());
        mockMvc.perform(
                put("/companies/" + company.getId())
                        .session(adminSession())
                        .content(json))
               .andExpect(status().isOk());
    }

    /**
     * An admin can edit companies via PATCH.
     * @throws Exception
     */
    @Test
    public void patchAllowedForAdmin() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/companies/" + company.getId())
                        .session(adminSession())
                        .content("{\"name\": \"test\"}"))
               .andExpect(status().isOk());
    }

    /**
     * A supervisor can not create companies-
     * @throws Exception
     */
    @Test
    public void postForbiddenForSupervisor() throws Exception {
        String json = String.format(companyJson, 12345);
        mockMvc.perform(
                post("/companies")
                        .session(supervisorSession())
                        .content(json))
               .andExpect(status().isForbidden());
    }

    /**
     * A supervisor can not edit companies.
     * @throws Exception
     */
    @Test
    public void putForbiddenForSupervisor() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        String json = String.format(companyJson, company.getCompanyId());
        mockMvc.perform(
                put("/companies/" + company.getId())
                        .session(supervisorSession())
                        .content(json))
               .andExpect(status().isForbidden());
    }

    /**
     * A supervisor can not edit companies via PATCH.
     * @throws Exception
     */
    @Test
    public void patchForbiddenForSupervisor() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/companies/" + company.getId())
                        .session(supervisorSession())
                        .content("{\"name\": \"test\"}"))
               .andExpect(status().isForbidden());
    }

    /**
     * Wrong data leads to HTTP 400.
     * @throws Exception
     */
    @Test
    public void constraintViolation() throws Exception {
        mockMvc.perform(
                post("/companies")
                        .session(adminSession())
                        .content("{ \"companyId\": \"1234\" }"))
               .andExpect(status().isBadRequest());
    }

    /**
     * A supervisor can add a contact person.
     * @throws Exception
     */
    @Test
    public void addContactPersonSupervisor() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/companies/" + company.getId() + "/contactPersons")
                        .session(supervisorSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/contactPersons/" + contactPerson.getId()))
               .andExpect(status().isNoContent());
    }

    /**
     * An employee can not add a contact person.
     * @throws Exception
     */
    @Test
    public void addContactForbiddenForEmployee() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/companies/" + company.getId() + "/contactPersons")
                        .session(basicSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/contactPersons/" + contactPerson.getId()))
               .andExpect(status().isForbidden());
    }

    /**
     * A supervisor can delete a contact person.
     * @throws Exception
     */
    @Test
    public void deleteContactAllowedForSupervisor() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/companies/" + company.getId() + "/contactPersons/0")
                        .session(supervisorSession()))
               .andExpect(status().isNoContent());
    }

    /**
     * An employee can not delete a contact person.
     * @throws Exception
     */
    @Test
    public void deleteContactNotAllowedForEmployee() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/companies/" + company.getId() + "/contactPersons/0")
                        .session(basicSession()))
               .andExpect(status().isForbidden());
    }

    /**
     * An admin can delete companies.
     * @throws Exception
     */
    @Test
    public void deleteAllowedForAdmin() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/companies/" + company.getId())
                        .session(adminSession()))
               .andExpect(status().isNoContent());
    }

    /**
     * A supervisor can not delete companies.
     * @throws Exception
     */
    @Test
    public void deleteForbiddenForSupervisor() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/companies/" + company.getId())
                        .session(supervisorSession()))
               .andExpect(status().isForbidden());
    }

    /**
     * The address of a company is accessible.
     * @throws Exception
     */
    @Test
    public void getAddress() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/companies/" + company.getId() + "/address")
                        .session(basicSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType));
    }
}
