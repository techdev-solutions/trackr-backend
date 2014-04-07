package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.core.web.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class TravelExpenseResourceTest extends MockMvcTest {

    @Autowired
    private TravelExpenseDataOnDemand travelExpenseDataOnDemand;

    @Autowired
    private TravelExpenseRepository travelExpenseRepository;

    @Before
    public void setUp() throws Exception {
        travelExpenseDataOnDemand.init();
    }

    @Test
    public void rootNotExported() throws Exception {
        mockMvc.perform(
                get("/travelExpenses")
                        .session(adminSession())
        ).andExpect(status().isMethodNotAllowed());
    }
}
