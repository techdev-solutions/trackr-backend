package de.techdev.trackr;

import de.techdev.trackr.web.AppWebMvcConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.echocat.jomon.testing.ArrayMatchers.containsItemsOf;
import static org.echocat.jomon.testing.BaseMatchers.isEmpty;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class AppWebApplicationInitializerTest {

    private AppWebApplicationInitializer appWebApplicationInitializer;

    @Before
    public void setUp() throws Exception {
        appWebApplicationInitializer = new AppWebApplicationInitializer();
    }

    @Test
    public void getRootConfigClasses() throws Exception {
        assertThat(appWebApplicationInitializer.getRootConfigClasses(), isEmpty());
    }

    @Test
    public void getServletConfigClasses() throws Exception {
        Class<?>[] servletConfigClasses = appWebApplicationInitializer.getServletConfigClasses();
        assertThat(servletConfigClasses, containsItemsOf(AppWebMvcConfiguration.class));
    }

    @Test
    public void getServletMappings() throws Exception {
        assertThat(appWebApplicationInitializer.getServletMappings(), containsItemsOf("/"));
    }
}
