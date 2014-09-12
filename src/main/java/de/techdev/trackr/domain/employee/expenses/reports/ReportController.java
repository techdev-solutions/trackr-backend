package de.techdev.trackr.domain.employee.expenses.reports;

import de.techdev.trackr.core.pdf.PdfCreationException;
import de.techdev.trackr.core.pdf.PdfRenderer;
import de.techdev.trackr.domain.employee.expenses.TravelExpense;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Moritz Schulze
 */
@Controller
@RequestMapping("/travelExpenseReports")
public class ReportController {

    @Autowired
    private ReportService travelExpenseReportService;

    @Autowired
    private PdfRenderer pdfRenderer;

    @Autowired
    private ReportRepository travelExpenseReportRepository;

    @RequestMapping(value = "/{id}/submit", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void submit(@PathVariable("id") Report travelExpenseReport) {
        travelExpenseReportService.submit(travelExpenseReport);
    }

    @RequestMapping(value = "/{id}/approve", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void approve(@PathVariable("id") Report travelExpenseReport, Principal principal) {
        travelExpenseReportService.accept(travelExpenseReport, principal.getName());
    }

    @RequestMapping(value = "/{id}/reject", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reject(@PathVariable("id") Report travelExpenseReport, Principal principal) {
        travelExpenseReportService.reject(travelExpenseReport, principal.getName());
    }

    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    @RequestMapping(value = "/{id}/pdf", produces = "application/pdf")
    @Transactional
    public ResponseEntity<byte[]> asPdf(@PathVariable("id") Report travelExpenseReport) {
        Report report = travelExpenseReportRepository.findOne(travelExpenseReport.getId());

        Context ctx = new Context();
        ctx.setVariable("report", report);
        ctx.setVariable("today", LocalDate.now());
        List<TravelExpense> expenses = report.getExpenses();
        BigDecimal totalCost = expenses.stream().map(TravelExpense::getCost).reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2));
        ctx.setVariable("totalCost", totalCost);
        Optional<LocalDate> startDate = expenses.stream().map(TravelExpense::getFromDate).min(LocalDate::compareTo);
        Optional<LocalDate> endDate = expenses.stream().map(TravelExpense::getToDate).max(LocalDate::compareTo);
        if (startDate.isPresent()) {
            ctx.setVariable("startDate", startDate.get());
        }
        if(endDate.isPresent()) {
            ctx.setVariable("endDate", endDate.get());
        }
        try {
            byte[] pdf = pdfRenderer.renderPdf("travel-expenses/report", ctx);
            byte[] pdfBase64 = Base64.encodeBase64(pdf);
            return new ResponseEntity<>(pdfBase64, HttpStatus.OK);
        } catch (PdfCreationException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
