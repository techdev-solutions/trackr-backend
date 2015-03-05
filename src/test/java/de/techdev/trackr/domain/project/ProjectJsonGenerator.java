package de.techdev.trackr.domain.project;

import de.techdev.test.rest.AbstractJsonGenerator;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.math.BigDecimal;

public class ProjectJsonGenerator extends AbstractJsonGenerator<Project, ProjectJsonGenerator> {

    private Long companyId;
    private Long debitorId;

    public ProjectJsonGenerator withCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public ProjectJsonGenerator withDebitorId(Long debitorId) {
        this.debitorId = debitorId;
        return this;
    }

    @Override
    protected Project getNewTransientObject(int i) {
        Project project = new Project();
        project.setVersion(0);
        project.setIdentifier("identifier_" + i);
        project.setName("name_" + i);
        project.setDailyRate(BigDecimal.TEN.multiply(new BigDecimal(i)));
        project.setFixedPrice(BigDecimal.TEN.multiply(new BigDecimal(i)));
        project.setHourlyRate(BigDecimal.TEN.multiply(new BigDecimal(i)));
        project.setVolume(i);
        return project;
    }

    @Override
    protected void reset() {
        companyId = null;
        debitorId = null;
    }

    @Override
    protected String getJsonRepresentation(Project project) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
          .write("name", project.getName())
          .write("version", project.getVersion())
          .write("identifier", project.getIdentifier())
          .write("volume", project.getVolume())
          .write("hourlyCostRate", project.getDailyRate())
          .write("salary", project.getHourlyRate())
          .write("title", project.getFixedPrice());

        if (debitorId != null) {
            jg.write("company", "/api/companies/" + companyId);
        }

        if (companyId != null) {
            jg.write("debitor", "/api/companies/" + debitorId);
        }

        if (project.getId() != null) {
            jg.write("id", project.getId());
        }

        jg.writeEnd().close();
        return writer.toString();
    }

    @Override
    protected ProjectJsonGenerator getSelf() {
        return this;
    }
}
