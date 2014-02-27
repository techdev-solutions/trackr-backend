package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Company;
import de.techdev.trackr.domain.ContactPerson;
import de.techdev.trackr.domain.support.CompanyDataOnDemand;
import de.techdev.trackr.domain.support.ContactPersonDataOnDemand;
import de.techdev.trackr.web.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    /**
     * Root URL is accessible
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
     * @throws Exception
     */
    @Test
    public void one() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/contactPersons/" + contactPerson.getId())
                        .session(basicSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType));
    }

    /**
     * A Supervisor can create contact persons
     * @throws Exception
     */
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

    /**
     * A Supervisor can edit contact persons
     * @throws Exception
     */
    @Test
    public void putAllowedForSupervisor() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        String json = String.format(contactPersonJson, contactPerson.getCompany().getId());
        mockMvc.perform(
                put("/contactPersons/" + contactPerson.getId())
                        .session(supervisorSession())
                        .content(json))
               .andExpect(status().isOk());
    }

    /**
     * An employee can not edit contact persons
     * @throws Exception
     */
    @Test
    public void putNotAllowedForEmployee() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        String json = String.format(contactPersonJson, contactPerson.getCompany().getId());
        mockMvc.perform(
                put("/contactPersons/" + contactPerson.getId())
                        .session(basicSession())
                        .content(json))
               .andExpect(status().isForbidden());
    }

    /**
     * A supervisor can edit contact persons via PATCH
     * @throws Exception
     */
    @Test
    public void patchAllowedForSupervisor() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/contactPersons/" + contactPerson.getId())
                        .session(supervisorSession())
                        .content("{\"firstName\": \"Test\"}"))
               .andExpect(status().isOk());
    }

    /**
     * An employee can not edit contact persons via PATCH
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
     * @throws Exception
     */
    @Test
    public void postNotAllowedForEmployee() throws Exception {
        String json = String.format(contactPersonJson, 0);
        mockMvc.perform(
                post("/contactPersons")
                        .session(basicSession())
                        .content(json))
               .andExpect(status().isForbidden());
    }

    /**
     * Wrong data leads to HTTP 400
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
}
