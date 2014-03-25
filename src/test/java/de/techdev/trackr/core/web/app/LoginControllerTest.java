package de.techdev.trackr.core.web.app;

import de.techdev.trackr.domain.JpaConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Moritz Schulze
 */
@WebAppConfiguration
@ContextConfiguration(classes = {AppWebMvcConfiguration.class, JpaConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class LoginControllerTest {
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public final void setUpMockMvc() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void home() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name("views/login.jsp"));
    }

    @Test
    public void admin() throws Exception {
        mockMvc.perform(get("/admin")).andExpect(status().isOk()).andExpect(view().name("views/admin.jsp"));
    }

    @Test
    public void root() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index.html"));
    }
}
