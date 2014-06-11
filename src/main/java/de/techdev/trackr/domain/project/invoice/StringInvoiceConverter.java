package de.techdev.trackr.domain.project.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Moritz Schulze
 */
public class StringInvoiceConverter implements Converter<String, Invoice> {

    @Autowired
    private JpaRepository<Invoice, Long> repository;

    @Override
    public Invoice convert(String source) {
        Long aLong;
        try {
            aLong = Long.valueOf(source);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return repository.findOne(aLong);
    }

}
