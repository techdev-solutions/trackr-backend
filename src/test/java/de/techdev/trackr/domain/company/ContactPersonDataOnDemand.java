package de.techdev.trackr.domain.company;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Moritz Schulze
 */
@Component
public class ContactPersonDataOnDemand extends AbstractDataOnDemand<ContactPerson> {

    @Override
    protected int getExpectedElements() {
        return 2;
    }

    @Autowired
    private CompanyDataOnDemand companyDataOnDemand;

    @Override
    public ContactPerson getNewTransientObject(int i) {
        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setEmail("email_" + i + "@test.com");
        contactPerson.setFirstName("firstName_" + i);
        contactPerson.setLastName("lastName_" + i);
        contactPerson.setPhone("phone_" + i);
        contactPerson.setSalutation("salutation_" + i);
        contactPerson.setCompany(companyDataOnDemand.getRandomObject());
        return contactPerson;
    }
}
