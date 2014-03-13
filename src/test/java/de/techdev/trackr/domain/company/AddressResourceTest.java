package de.techdev.trackr.domain.company;

import de.techdev.trackr.core.web.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    /**
     * Root is not accessible.
     *
     * @throws Exception
     */
    @Test
    public void findAllNotExported() throws Exception {
        mockMvc.perform(
                get("/addresses")
                        .session(employeeSession()))
               .andExpect(status().isMethodNotAllowed());
    }

    /**
     * One address is accessible.
     *
     * @throws Exception
     */
    @Test
    public void one() throws Exception {
        Address address = addressDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/addresses/" + address.getId())
                        .session(employeeSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType))
               .andExpect(jsonPath("id", isNotNull()));
    }

    /**
     * An admin can create addresses.
     *
     * @throws Exception
     */
    @Test
    public void createAllowedForAdmin() throws Exception {
        Address address = addressDataOnDemand.getNewTransientObject(500);
        mockMvc.perform(
                post("/addresses")
                        .session(adminSession())
                        .content(generateAddressJson(address)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("id", isNotNull()));
    }

    /**
     * An admin can edit addresses.
     *
     * @throws Exception
     */
    @Test
    public void putAllowedForAdmin() throws Exception {
        Address address = addressDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/addresses/" + address.getId())
                        .session(adminSession())
                        .content(generateAddressJson(address)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("id", is(address.getId().intValue())));
    }

    /**
     * An admin can edit addresses via PATCH.
     *
     * @throws Exception
     */
    @Test
    public void patchAllowedForAdmin() throws Exception {
        Address address = addressDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/addresses/" + address.getId())
                        .session(adminSession())
                        .content("{\"street\": \"test\"}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("id", isNotNull()))
               .andExpect(jsonPath("street", is("test")));
    }

    /**
     * A supervisor can not create addresses.
     *
     * @throws Exception
     */
    @Test
    public void createNotAllowedForSupervisor() throws Exception {
        Address address = addressDataOnDemand.getNewTransientObject(500);
        mockMvc.perform(
                post("/addresses")
                        .session(supervisorSession())
                        .content(generateAddressJson(address)))
               .andExpect(status().isForbidden());
    }

    /**
     * A supervisor can not edit addresses.
     *
     * @throws Exception
     */
    @Test
    public void putForbiddenForSupervisor() throws Exception {
        Address address = addressDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/addresses/" + address.getId())
                        .session(supervisorSession())
                        .content(generateAddressJson(address)))
               .andExpect(status().isForbidden());
    }

    /**
     * A supervisor can not edit addresses via PATCH.
     *
     * @throws Exception
     */
    @Test
    public void patchForbiddenForSupervisor() throws Exception {
        Address address = addressDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/addresses/" + address.getId())
                        .session(supervisorSession())
                        .content("{\"street\": \"test\"}"))
               .andExpect(status().isForbidden());
    }

    /**
     * Addresses are not deletable.
     *
     * @throws Exception
     */
    @Test
    public void deleteNotExported() throws Exception {
        Address address = addressDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/addresses/" + address.getId())
                        .session(adminSession()))
               .andExpect(status().isMethodNotAllowed());
    }

    protected String generateAddressJson(Address address) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
          .write("street", address.getStreet())
          .write("houseNumber", address.getHouseNumber())
          .write("city", address.getCity())
          .write("zipCode", address.getZipCode())
          .write("country", address.getCountry());
        if (address.getId() != null) {
            jg.write("id", address.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }
}
