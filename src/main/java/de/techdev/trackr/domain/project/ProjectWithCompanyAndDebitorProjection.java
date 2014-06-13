package de.techdev.trackr.domain.project;

import de.techdev.trackr.domain.company.Company;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * @author Moritz Schulze
 */
@Projection(types = Project.class, name = "withCompanyAndDebitor")
public interface ProjectWithCompanyAndDebitorProjection {
    Long getId();

    Integer getVersion();

    String getIdentifier();

    String getName();

    Company getCompany();

    Integer getVolume();

    BigDecimal getHourlyRate();

    BigDecimal getDailyRate();

    BigDecimal getFixedPrice();

    Company getDebitor();
}
