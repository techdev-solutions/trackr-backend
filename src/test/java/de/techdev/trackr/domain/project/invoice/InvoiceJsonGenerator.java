package de.techdev.trackr.domain.project.invoice;

import de.techdev.test.rest.AbstractJsonGenerator;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InvoiceJsonGenerator extends AbstractJsonGenerator<Invoice, InvoiceJsonGenerator> {

    private Long debitorId;

    public InvoiceJsonGenerator withDebitorId(Long debitorId) {
        this.debitorId = debitorId;
        return this;
    }

    @Override
    protected String getJsonRepresentation(Invoice object) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jg.writeStartObject()
          .write("identifier", object.getIdentifier())
          .write("invoiceState", object.getInvoiceState().toString())
          .write("invoiceTotal", object.getInvoiceTotal())
          .write("debitor", "/companies/" + debitorId)
          .write("creationDate", sdf.format(object.getCreationDate()));

        if (object.getDueDate() != null) {
            jg.write("dueDate", sdf.format(object.getDueDate()));
        }
        if (object.getId() != null) {
            jg.write("id", object.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }

    @Override
    protected Invoice getNewTransientObject(int i) {
        Invoice invoice = new Invoice();
        invoice.setIdentifier("identifier_" + i);
        invoice.setInvoiceState(Invoice.InvoiceState.OUTSTANDING);
        invoice.setDueDate(new Date());
        invoice.setInvoiceTotal(BigDecimal.TEN);
        invoice.setCreationDate(new Date());
        return invoice;
    }

    @Override
    protected InvoiceJsonGenerator getSelf() {
        return this;
    }

    @Override
    protected void reset() {
        debitorId = null;
    }
}
