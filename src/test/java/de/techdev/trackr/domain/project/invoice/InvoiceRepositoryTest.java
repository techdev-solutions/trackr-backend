package de.techdev.trackr.domain.project.invoice;

import de.techdev.trackr.TransactionalIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.junit.Assert.*;

public class InvoiceRepositoryTest extends TransactionalIntegrationTest {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceDataOnDemand invoiceDataOnDemand;

    @Before
    public void setUp() throws Exception {
        invoiceDataOnDemand.init();
    }

    @Test
    public void all() throws Exception {
        List<Invoice> all = invoiceRepository.findAll();
        assertThat(all, isNotEmpty());
    }

    @Test
    public void one() throws Exception {
        Invoice invoice = invoiceDataOnDemand.getRandomObject();
        Invoice one = invoiceRepository.findOne(invoice.getId());
        assertThat(one, isNotNull());
    }
}