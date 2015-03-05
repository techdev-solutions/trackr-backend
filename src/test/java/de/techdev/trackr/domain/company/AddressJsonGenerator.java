package de.techdev.trackr.domain.company;

import de.techdev.test.rest.AbstractJsonGenerator;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

public class AddressJsonGenerator extends AbstractJsonGenerator<Address, AddressJsonGenerator> {

    @Override
    protected String getJsonRepresentation(Address address) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
          .write("version", address.getVersion())
          .write("street", address.getStreet())
          .write("houseNumber", address.getHouseNumber())
          .write("zipCode", address.getZipCode())
          .write("city", address.getCity())
          .write("country", address.getCountry());

        if (address.getId() != null) {
            jg.write("id", address.getId());
        }

        jg.writeEnd().close();
        return writer.toString();
    }

    @Override
    protected Address getNewTransientObject(int i) {
        Address address = new Address();
        address.setVersion(0);
        address.setCity("city_" + i);
        address.setCountry("country_" + i);
        address.setHouseNumber(Integer.toString(i));
        address.setStreet("street_" + i);
        address.setZipCode("zip_" + i);

        return address;
    }

    @Override
    protected AddressJsonGenerator getSelf() {
        return this;
    }
}
