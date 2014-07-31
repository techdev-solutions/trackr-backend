package de.techdev.trackr.domain.company;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * @author Moritz Schulze
 */
@Projection(types = Company.class, name = "withAddressAndContactPersons")
public interface CompanyWithAddressAndContactPersonsProjection {

    Long getId();

    Integer getVersion();

    Long getCompanyId();

    String getName();

    Integer getTimeForPayment();

    Address getAddress();

    List<ContactPerson> getContactPersons();
}
