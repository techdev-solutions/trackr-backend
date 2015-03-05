package de.techdev.trackr.domain.company;

import de.techdev.test.rest.AbstractJsonGenerator;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

public class ContactPersonJsonGenerator extends AbstractJsonGenerator<ContactPerson, ContactPersonJsonGenerator> {

    private Long companyId;

    public ContactPersonJsonGenerator withCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    @Override
    protected String getJsonRepresentation(ContactPerson person) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
          .write("version", person.getVersion())
          .write("email", person.getEmail())
          .write("firstName", person.getFirstName())
          .write("lastName", person.getLastName())
          .write("phone", person.getPhone())
          .write("salutation", person.getSalutation())
          .write("roles", person.getRoles());

        if (person.getId() != null) {
            jg.write("id", person.getId());
        }

        if (companyId != null) {
            jg.write("company", "/api/companies/" + companyId);
        }

        jg.writeEnd().close();
        return writer.toString();
    }

    @Override
    protected ContactPerson getNewTransientObject(int i) {
        ContactPerson person = new ContactPerson();
        person.setVersion(0);
        person.setEmail("person@techdev.de_" + i);
        person.setFirstName("first_name_" + i);
        person.setLastName("last_name_" + i);
        person.setPhone("phone_" + i);
        person.setSalutation("salutation_" + i);
        person.setRoles("roles_" + i);

        return person;
    }

    @Override
    protected ContactPersonJsonGenerator getSelf() {
        return this;
    }

    @Override
    protected void reset() {
        companyId = null;
    }
}
