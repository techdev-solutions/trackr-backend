package de.techdev.trackr.domain.translations;

import de.techdev.trackr.core.web.MockMvcTest;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class TranslationsResourceTest extends MockMvcTest {

    @Test
    public void testGetTranslationsIsAccessible() throws Exception {
        mockMvc.perform(
                get("/translations")
                        .session(employeeSession())
                        .param("locale", "en")
        )
                .andExpect(status().isOk());
    }
}
