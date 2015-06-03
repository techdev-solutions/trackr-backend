package de.techdev.trackr.domain.employee.expenses;

import de.techdev.test.rest.AbstractJsonGenerator;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TravelExpenseJsonGenerator extends AbstractJsonGenerator<TravelExpense, TravelExpenseJsonGenerator> {

    private Long reportId;

    public TravelExpenseJsonGenerator withReportId(Long reportId) {
        this.reportId = reportId;
        return this;
    }

    @Override
    protected String getJsonRepresentation(TravelExpense object) {
        if (reportId == null) {
            throw new IllegalStateException("Report id must not be null");
        }
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        jg.writeStartObject()
          .write("cost", object.getCost())
          .write("vat", object.getVat())
          .write("fromDate", sdf.format(object.getFromDate()))
          .write("toDate", sdf.format(object.getToDate()))
          .write("submissionDate", sdf2.format(object.getSubmissionDate()))
          .write("type", object.getType().toString())
          .write("paid", object.isPaid())
          .write("report", "/objectReports/" + reportId);

        if(object.getComment() != null) {
            jg.write("comment", object.getComment());
        }

        if (object.getId() != null) {
            jg.write("id", object.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }

    @Override
    protected TravelExpense getNewTransientObject(int i) {
        TravelExpense travelExpense = new TravelExpense();
        travelExpense.setFromDate(new Date());
        travelExpense.setToDate(new Date());
        travelExpense.setCost(BigDecimal.TEN);
        travelExpense.setType(TravelExpense.Type.TAXI);
        travelExpense.setSubmissionDate(new Date());
        travelExpense.setVat(BigDecimal.ONE);
        travelExpense.setComment("comment_" + i);
        return travelExpense;
    }

    @Override
    protected TravelExpenseJsonGenerator getSelf() {
        return this;
    }

    @Override
    protected void reset() {
        reportId = null;
    }
}
