package de.techdev.trackr.domain.company;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Moritz Schulze
 */
public class CompanyDataOnDemand extends AbstractDataOnDemand<Company> {

    @Override
    protected int getExpectedElements() {
        return 3;
    }

    @Autowired
    private AddressDataOnDemand addressDataOnDemand;

    @Override
    public Company getNewTransientObject(int i) {
        Company company = new Company();
        company.setId((long) i);
        company.setName("name_" + i);
        company.setCompanyId((long)i);
        Address address = addressDataOnDemand.getRandomObject();
        company.setAddress(address);
        return company;
    }
}
