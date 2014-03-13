package de.techdev.trackr.domain.project;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.company.CompanyDataOnDemand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Moritz Schulze
 */
@Component
public class ProjectDataOnDemand extends AbstractDataOnDemand<Project> {

    @Override
    protected int getExpectedElements() {
        return 2;
    }

    @Autowired
    private CompanyDataOnDemand companyDataOnDemand;

    @Override
    public Project getNewTransientObject(int i) {
        Project project = new Project();
        project.setCompany(companyDataOnDemand.getRandomObject());
        project.setIdentifier("identifier_" + i);
        project.setName("name_" + i);
        project.setDailyRate(BigDecimal.TEN.multiply(new BigDecimal(i)));
        project.setFixedPrice(BigDecimal.TEN.multiply(new BigDecimal(i)));
        project.setHourlyRate(BigDecimal.TEN.multiply(new BigDecimal(i)));
        project.setVolume(i);
        project.setDebitor(companyDataOnDemand.getRandomObject());
        return project;
    }

}
