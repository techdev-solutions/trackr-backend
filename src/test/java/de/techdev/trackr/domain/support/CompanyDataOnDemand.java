package de.techdev.trackr.domain.support;

import de.techdev.trackr.domain.Address;
import de.techdev.trackr.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Moritz Schulze
 */
@Component
public class CompanyDataOnDemand extends AbstractDataOnDemand<Company> {

    @Autowired
    private AddressDataOnDemand addressDataOnDemand;

    public Company getRandomCompany() {
        init();
        Company obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return repository.findOne(id);
    }

    @Override
    public Company getNewTransientObject(int i) {
        Company company = new Company();
        company.setId((long) i);
        company.setName("name_" + i);
        company.setCompanyId("companyId_" + i);
        Address address = addressDataOnDemand.getNewTransientObject(i);
        company.setAddress(address);
        return company;
    }
}
