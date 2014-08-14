package de.techdev.trackr.domain.project.invoice;

import de.techdev.trackr.domain.company.Company;
import de.techdev.trackr.util.LocalDateUtil;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class InvoiceEventHandlerTest {

    private InvoiceEventHandler invoiceEventHandler;

    @Before
    public void setUp() throws Exception {
        invoiceEventHandler = new InvoiceEventHandler();
    }

    @Test
    public void testSetInvoiceStateIfNecessaryOverdue() throws Exception {
        Invoice invoice = new Invoice();
        LocalDate dueDate = LocalDate.of(2013, 10, 1);
        invoice.setDueDate(LocalDateUtil.fromLocalDate(dueDate));
        invoiceEventHandler.setInvoiceStateIfNecessary(invoice);
        assertThat(invoice.getInvoiceState(), is(Invoice.InvoiceState.OVERDUE));
    }

    @Test
    public void testSetInvoiceStateIfNecessaryOutstanding() throws Exception {
        Invoice invoice = new Invoice();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        invoice.setDueDate(LocalDateUtil.fromLocalDate(dueDate));
        invoiceEventHandler.setInvoiceStateIfNecessary(invoice);
        assertThat(invoice.getInvoiceState(), is(Invoice.InvoiceState.OUTSTANDING));
    }

    @Test
    public void testSetDueDateFromTimeForPayment() throws Exception {
        Invoice invoice = new Invoice();
        Company company = new Company();
        company.setTimeForPayment(14);
        invoice.setDebitor(company);
        invoice.setCreationDate(LocalDateUtil.fromLocalDate(LocalDate.of(2014, 1, 1)));

        invoiceEventHandler.setDueDateFromTimeForPayment(invoice);
        assertThat(invoice.getDueDate(), is(LocalDateUtil.fromLocalDate(LocalDate.of(2014, 1, 15))));
    }

    @Test
    public void testDontSetDueDateFromTimeForPaymentIfItIsFilled() throws Exception {
        Invoice invoice = new Invoice();
        Company company = new Company();
        company.setTimeForPayment(14);
        invoice.setDebitor(company);
        invoice.setCreationDate(LocalDateUtil.fromLocalDate(LocalDate.of(2014, 1, 1)));
        invoice.setDueDate(LocalDateUtil.fromLocalDate(LocalDate.of(2014, 1, 2)));

        invoiceEventHandler.setDueDateFromTimeForPayment(invoice);
        assertThat(invoice.getDueDate(), is(LocalDateUtil.fromLocalDate(LocalDate.of(2014, 1, 2))));
    }

    @Test
    public void testSetDueDateFromTimeForPaymentDontFailOnNoDebitor() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setCreationDate(LocalDateUtil.fromLocalDate(LocalDate.of(2014, 1, 1)));
        invoiceEventHandler.setDueDateFromTimeForPayment(invoice);
    }

    @Test
    public void testSetDueDateFromTimeForPaymentDontFailOnNoTimeForPayment() throws Exception {
        Invoice invoice = new Invoice();
        Company company = new Company();
        invoice.setDebitor(company);
        invoice.setCreationDate(LocalDateUtil.fromLocalDate(LocalDate.of(2014, 1, 1)));

        invoiceEventHandler.setDueDateFromTimeForPayment(invoice);
    }

    @Test
    public void stateGetsSetToOverdueIfTimeForPaymentSetsDueDateInPast() throws Exception {
        Invoice invoice = new Invoice();
        Company company = new Company();
        company.setTimeForPayment(14);
        invoice.setDebitor(company);
        invoice.setCreationDate(LocalDateUtil.fromLocalDate(LocalDate.of(2014, 1, 1)));

        invoiceEventHandler.authorizeCreate(invoice);
        assertThat(invoice.getDueDate(), is(LocalDateUtil.fromLocalDate(LocalDate.of(2014, 1, 15))));
        assertThat(invoice.getInvoiceState(), is(Invoice.InvoiceState.OVERDUE));
    }
}