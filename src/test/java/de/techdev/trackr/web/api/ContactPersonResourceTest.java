package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Company;
import de.techdev.trackr.domain.ContactPerson;
import de.techdev.trackr.domain.support.CompanyDataOnDemand;
import de.techdev.trackr.domain.support.ContactPersonDataOnDemand;
import de.techdev.trackr.web.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class ContactPersonResourceTest extends MockMvcTest {

    private String contactPersonJson = "{\"salutation\": \"salutation_1\", \"firstName\" : \"firstName_1\", \"lastName\": \"lastName_1\", \"email\": \"email@test.com\", \"phone\": \"123452345\", \"company\": \"/companies/%d\"}";

    @Autowired
    private ContactPersonDataOnDemand contactPersonDataOnDemand;

    @Autowired
    private CompanyDataOnDemand companyDataOnDemand;

    @Before
    public void setUp() throws Exception {
        contactPersonDataOnDemand.init();
    }

    @Test
    public void root() throws Exception {
        mockMvc.perform(
                get("/contactPersons")
                        .session(basicSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType));
    }

    @Test
    public void one() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/contactPersons/" + contactPerson.getId())
                        .session(basicSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType));
    }

    @Test
    public void postAllowedForSupervisor() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        String json = String.format(contactPersonJson, company.getId());
        mockMvc.perform(
                post("/contactPersons")
                        .session(supervisorSession())
                        .content(json))
               .andExpect(status().isCreated());
    }

    @Test
    public void postNotAllowedForEmployee() throws Exception {
        String json = String.format(contactPersonJson, 0);
        mockMvc.perform(
                post("/contactPersons")
                        .session(basicSession())
                        .content(json))
               .andExpect(status().isForbidden());
    }

    @Test
    public void constraintViolation() throws Exception {
        mockMvc.perform(
                post("/contactPersons")
                        .session(supervisorSession())
                        .content("{}"))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteAllowedForSupervisor() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/contactPersons/" + contactPerson.getId())
                        .session(supervisorSession()))
               .andExpect(status().isNoContent());
    }

    @Test
    public void deleteForbiddenForEmployee() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/contactPersons/" + contactPerson.getId())
                        .session(basicSession()))
               .andExpect(status().isForbidden());
    }
}
