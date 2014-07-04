package de.techdev.trackr.domain.employee.addressbook;

import de.techdev.trackr.core.web.MockMvcTest;
import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddressBookResourceTest extends MockMvcTest {

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    @Before
    public void setUp() throws Exception {
        employeeDataOnDemand.init();
    }

    @Test
    public void rootIsAccessible() throws Exception {
        mockMvc.perform(get("/address_book")
                        .session(employeeSession())
        )
                .andExpect(status().isOk());
    }
}