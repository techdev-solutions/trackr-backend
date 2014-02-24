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

    private final String companyJson = "{\"companyId\": 12345, \"name\": \"techdev\", \"address\": {\"street\": \"strasse\", \"houseNumber\": \"11\", \"city\": \"Karlsruhe\", \"zipCode\": \"12345\", \"country\": \"Germany\"}}";

    @Autowired
    private CompanyDataOnDemand companyDataOnDemand;

    @Autowired
    private ContactPersonDataOnDemand contactPersonDataOnDemand;

    @Before
    public void setUp() throws Exception {
        companyDataOnDemand.init();
    }

    @Test
    public void root() throws Exception {
        mockMvc.perform(
                get("/companies")
                        .session(basicSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType));
    }

    @Test
    public void one() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/companies/" + company.getId())
                        .session(basicSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType));
    }

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

    @Test
    public void postAllowedForAdmin() throws Exception {
        mockMvc.perform(
                post("/companies")
                        .session(adminSession())
                        .content(companyJson))
               .andExpect(status().isCreated());
    }

    @Test
    public void postForbiddenForSupervisor() throws Exception {
        mockMvc.perform(
                post("/companies")
                        .session(supervisorSession())
                        .content(companyJson))
               .andExpect(status().isForbidden());
    }

    @Test
    public void constraintViolation() throws Exception {
        mockMvc.perform(
                post("/companies")
                        .session(adminSession())
                        .content("{ \"companyId\": \"1234\" }"))
               .andExpect(status().isBadRequest());
    }

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

    @Test
    public void deleteAllowedForAdmin() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/companies/" + company.getId())
                        .session(adminSession()))
               .andExpect(status().isNoContent());
    }

    @Test
    public void deleteForbiddenForSupervisor() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/companies/" + company.getId())
                        .session(supervisorSession()))
               .andExpect(status().isForbidden());
    }
}
