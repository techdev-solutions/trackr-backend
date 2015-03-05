package de.techdev.trackr.domain.company;

import de.techdev.test.rest.AbstractJsonGenerator;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

public class CompanyJsonGenerator extends AbstractJsonGenerator<Company, CompanyJsonGenerator> {

    private Long addressId;

    public CompanyJsonGenerator withAddressId(Long addressId) {
        this.addressId = addressId;
        return this;
    }

    @Override
    protected String getJsonRepresentation(Company object) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
          .write("name", object.getName())
          .write("version", object.getVersion())
          .write("companyId", object.getCompanyId());

        if (addressId != null) {
            jg.write("address", "/api/addresses/" + addressId);
        }
        if (object.getId() != null) {
            jg.write("id", object.getId());
        }
        if (object.getTimeForPayment() != null) {
            jg.write("timeForPayment", object.getTimeForPayment());
        }
        jg.writeEnd().close();
        return writer.toString();
    }

    @Override
    protected Company getNewTransientObject(int i) {
        Company company = new Company();
        company.setVersion(0);
        company.setName("name_" + i);
        company.setCompanyId((long) i);
        company.setTimeForPayment(i);
        return company;
    }

    @Override
    protected CompanyJsonGenerator getSelf() {
        return this;
    }

    @Override
    protected void reset() {
        addressId = null;
    }
}
