package de.techdev.trackr.domain.company;

import de.techdev.trackr.domain.AbstractDomainResourceTest;
import org.junit.Test;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class AddressResourceTest extends AbstractDomainResourceTest<Address> {

    @Override
    protected String getResourceName() {
        return "addresses";
    }

    /**
     * Root is not accessible.
     *
     * @throws Exception
     */
    @Test
    public void findAllNotExported() throws Exception {
        assertThat(root(employeeSession()), isMethodNotAllowed());
    }

    /**
     * One address is accessible.
     *
     * @throws Exception
     */
    @Test
    public void one() throws Exception {
        assertThat(one(employeeSession()), isAccessible());
    }

    /**
     * An admin can create addresses.
     *
     * @throws Exception
     */
    @Test
    public void createAllowedForAdmin() throws Exception {
        assertThat(create(adminSession()), isCreated());
    }

    /**
     * An admin can edit addresses.
     *
     * @throws Exception
     */
    @Test
    public void putAllowedForAdmin() throws Exception {
        assertThat(update(adminSession()), isUpdated());
    }

    /**
     * An admin can edit addresses via PATCH.
     *
     * @throws Exception
     */
    @Test
    public void patchAllowedForAdmin() throws Exception {
        assertThat(updateViaPatch(adminSession(), "{\"street\": \"test\"}"), isUpdated());
    }

    /**
     * A supervisor can not create addresses.
     *
     * @throws Exception
     */
    @Test
    public void createNotAllowedForSupervisor() throws Exception {
        assertThat(create(supervisorSession()), isForbidden());
    }

    /**
     * A supervisor can not edit addresses.
     *
     * @throws Exception
     */
    @Test
    public void putForbiddenForSupervisor() throws Exception {
        assertThat(update(supervisorSession()), isForbidden());
    }

    /**
     * A supervisor can not edit addresses via PATCH.
     *
     * @throws Exception
     */
    @Test
    public void patchForbiddenForSupervisor() throws Exception {
        assertThat(updateViaPatch(supervisorSession(), "{\"street\": \"test\"}"), isForbidden());
    }

    /**
     * Addresses are not deletable.
     *
     * @throws Exception
     */
    @Test
    public void deleteNotExported() throws Exception {
        assertThat(remove(adminSession()), isMethodNotAllowed());
    }

    @Override
    protected String getJsonRepresentation(Address address) {
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
