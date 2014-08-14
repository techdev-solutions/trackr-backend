package de.techdev.trackr.domain.common;

import de.techdev.trackr.domain.employee.expenses.reports.Report;
import de.techdev.trackr.domain.employee.vacation.VacationRequest;
import de.techdev.trackr.domain.project.invoice.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.repository.CrudRepository;

/**
 * Generic converter to convert from a String representing an id to an entity that we have a repository for.
 * @author Moritz Schulze
 */
public class StringToEntityConverter<T> implements Converter<String,T> {

    @Autowired
    private CrudRepository<T, Long> repository;

    @Override
    public T convert(String source) {
        Long id;
        try {
            id = Long.valueOf(source);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot convert to id from string " + source, e);
        }
        T one = repository.findOne(id);
        if (one == null) {
            throw new IllegalArgumentException("Cannot convert to type from id " + id);
        }
        return one;
    }

    public static class StringToInvoiceConverter extends StringToEntityConverter<Invoice> {

    }

    public static class StringToVacationRequestConverter extends StringToEntityConverter<VacationRequest> {

    }

    public static class StringToTravelExpenseReportConverter extends StringToEntityConverter<Report> {

    }

}