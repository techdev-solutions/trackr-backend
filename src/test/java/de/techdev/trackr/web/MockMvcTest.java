package de.techdev.trackr.web;

import de.techdev.trackr.IntegrationTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * A base class for tests that access the web mvc resources.
 * @author Moritz Schulze
 */
public abstract class MockMvcTest extends IntegrationTest {

    protected final String standardContentType = "application/hal+json";

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public final void setUpMockMvc() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }
}
