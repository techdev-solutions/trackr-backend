package de.techdev.trackr.domain.company;

import de.techdev.trackr.domain.AbstractDataOnDemand;

/**
 * @author Moritz Schulze
 */
public class AddressDataOnDemand extends AbstractDataOnDemand<Address> {

    @Override
    protected int getExpectedElements() {
        return 3;
    }

    public Address getNewTransientObject(int i) {
        Address address = new Address();
        address.setStreet("street_" + i);
        address.setHouseNumber(String.format("%d", i));
        address.setZipCode("zip_" + i);
        address.setCity("city_" + i);
        address.setCountry("country_" + i);
        return address;
    }
}
