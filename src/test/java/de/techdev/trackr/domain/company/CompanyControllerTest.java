package de.techdev.trackr.domain.company;

import de.techdev.trackr.core.web.MockMvcTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class CompanyControllerTest extends MockMvcTest {

    @Autowired
    private CompanyDataOnDemand companyDataOnDemand;

    @Test
    public void createWithAddressAllowedForAdmin() throws Exception {
        Company company = companyDataOnDemand.getNewTransientObject(500);
        CompanyController.CreateCompany createCompany = new CompanyController.CreateCompany();
        createCompany.setCompany(company);
        createCompany.setAddress(company.getAddress());

        mockMvc.perform(
                post("/companies/createWithAddress")
                        .session(adminSession())
                        .header("Content-Type", "application/json")
                        .content(generateCreateCompanyJson(createCompany)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("id", isNotNull()));
    }

    @Test
    public void createWithAddressForbiddenForSupervisor() throws Exception {
        Company company = companyDataOnDemand.getNewTransientObject(500);
        CompanyController.CreateCompany createCompany = new CompanyController.CreateCompany();
        createCompany.setCompany(company);
        createCompany.setAddress(company.getAddress());

        mockMvc.perform(
                post("/companies/createWithAddress")
                        .session(supervisorSession())
                        .header("Content-Type", "application/json")
                        .content(generateCreateCompanyJson(createCompany)))
               .andExpect(status().isForbidden());
    }

    @Test
    public void createWithAddressBindingError() throws Exception {
        Company company = companyDataOnDemand.getNewTransientObject(500);
        CompanyController.CreateCompany createCompany = new CompanyController.CreateCompany();
        company.getAddress().setStreet("");
        createCompany.setCompany(company);
        createCompany.setAddress(company.getAddress());

        mockMvc.perform(
                post("/companies/createWithAddress")
                        .session(adminSession())
                        .header("Content-Type", "application/json")
                        .content(generateCreateCompanyJson(createCompany)))
               .andExpect(status().isBadRequest());
    }


    protected String generateCreateCompanyJson(CompanyController.CreateCompany company) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
          .writeStartObject("company")
          .write("companyId", company.getCompany().getCompanyId())
          .write("name", company.getCompany().getName())
          .writeStartObject("address").writeEnd()
          .writeEnd()
          .writeStartObject("address")
          .write("street", company.getAddress().getStreet())
          .write("houseNumber", company.getAddress().getHouseNumber())
          .write("city", company.getAddress().getCity())
          .write("zipCode", company.getAddress().getZipCode())
          .write("country", company.getAddress().getCountry())
          .writeEnd()
          .writeEnd().close();
        return writer.toString();
    }
}
