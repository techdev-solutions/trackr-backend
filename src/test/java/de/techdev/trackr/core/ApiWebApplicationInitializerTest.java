package de.techdev.trackr.core;

import de.techdev.trackr.core.security.MethodSecurityConfiguration;
import de.techdev.trackr.core.web.api.ApiWebMvcConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.echocat.jomon.testing.ArrayMatchers.containsItemsOf;
import static org.echocat.jomon.testing.BaseMatchers.isEmpty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class ApiWebApplicationInitializerTest {

    private ApiWebApplicationInitializer apiWebApplicationInitializer;

    @Before
    public void setUp() throws Exception {
        apiWebApplicationInitializer = new ApiWebApplicationInitializer();
    }

    @Test
    public void getRootConfigClasses() throws Exception {
        assertThat(apiWebApplicationInitializer.getRootConfigClasses(), isEmpty());
    }

    @Test
    public void getServletConfigClasses() throws Exception {
        Class<?>[] servletConfigClasses = apiWebApplicationInitializer.getServletConfigClasses();
        assertThat(servletConfigClasses, containsItemsOf(ApiWebMvcConfiguration.class));
        assertThat(servletConfigClasses, containsItemsOf(MethodSecurityConfiguration.class));
    }

    @Test
    public void getServletMappings() throws Exception {
        assertThat(apiWebApplicationInitializer.getServletMappings(), containsItemsOf("/api/*"));
    }

    @Test
    public void getServletName() throws Exception {
        assertThat(apiWebApplicationInitializer.getServletName(), is("api-dispatcher"));
    }
}
