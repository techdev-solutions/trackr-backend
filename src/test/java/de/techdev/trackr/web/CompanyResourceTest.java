package de.techdev.trackr.web;

import de.techdev.trackr.domain.support.CompanyDataOnDemand;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
