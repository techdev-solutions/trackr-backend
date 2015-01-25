package de.techdev.trackr.core.web.app;

import de.techdev.trackr.domain.JpaConfiguration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
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
@TestPropertySource(properties="auth.module=property")
public class LoginControllerTest {
    protected MockMvc mockMvc;
    private final String expectedLoginViewName ;
    
    public LoginControllerTest() {
		this( "login");
	}

    protected LoginControllerTest(String expectedLoginViewName) {
		this.expectedLoginViewName = expectedLoginViewName;
	}

	@Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public final void setUpMockMvc() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void home() throws Exception {
		mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name(expectedLoginViewName));
    }

    @Test
    public void successPage() throws Exception {
        mockMvc.perform(get("/success")).andExpect(status().isOk()).andExpect(view().name("success"));
    }


}
