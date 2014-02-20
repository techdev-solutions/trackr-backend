package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Company;
import de.techdev.trackr.domain.support.CompanyDataOnDemand;
import de.techdev.trackr.web.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class CompanyResourceTest extends MockMvcTest {

    @Autowired
    private CompanyDataOnDemand companyDataOnDemand;

    @Before
    public void setUp() throws Exception {
        companyDataOnDemand.init();
    }

    @Test
    public void root() throws Exception {
        mockMvc.perform(get("/companies")).andExpect(status().isOk()).andExpect(content().contentType(standardContentType));
    }

    @Test
    public void one() throws Exception {
        Company company = companyDataOnDemand.getRandomCompany();
        mockMvc.perform(get("/companies/" + company.getId())).andExpect(status().isOk()).andExpect(content().contentType(standardContentType));
    }

    @Test
    public void findByCompanyId() throws Exception {
        Company company = companyDataOnDemand.getRandomCompany();
        mockMvc.perform(get("/companies/search/findByCompanyId").param("companyId", company.getCompanyId().toString()))
                .andExpect(status().isOk()).andExpect(content().contentType(standardContentType));
    }

    @Test
    public void constraintViolation() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(adminAuthentication());
        mockMvc.perform(post("/companies").content("{ \"companyId\": \"1234\" }")).andExpect(status().isBadRequest());
    }
}
