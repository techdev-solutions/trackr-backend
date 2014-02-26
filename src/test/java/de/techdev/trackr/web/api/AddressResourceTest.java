package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Address;
import de.techdev.trackr.domain.support.AddressDataOnDemand;
import de.techdev.trackr.web.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class AddressResourceTest extends MockMvcTest {

    @Autowired
    private AddressDataOnDemand addressDataOnDemand;

    @Before
    public void setUp() throws Exception {
        addressDataOnDemand.init();
    }

    @Test
    public void root() throws Exception {
        mockMvc.perform(
                get("/addresses")
                        .session(basicSession()))
               .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void one() throws Exception {
        Address address = addressDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/companies/" + address.getId())
                        .session(basicSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType));
    }

}
