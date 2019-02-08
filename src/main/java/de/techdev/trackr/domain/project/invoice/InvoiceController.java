package de.techdev.trackr.domain.project.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Moritz Schulze
 */
@Controller
@RequestMapping(value = "/invoices")
public class InvoiceController {

    @Autowired
    private ChangeStateService changeStateService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @RequestMapping(value = "{id}/markPaid", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String markAsPaid(@PathVariable("id") Long invoiceId) {
        Invoice invoice = invoiceRepository.findOne(invoiceId);
        changeStateService.changeState(invoice, Invoice.InvoiceState.PAID);
        return "Ok.";
    }
}
