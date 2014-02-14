package de.techdev.trackr.domain.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Moritz Schulze
 */
public abstract class AbstractDataOnDemand<S> {

    @Autowired
    protected JpaRepository<S, Long> repository;

    protected List<S> data;

    protected SecureRandom rnd;

    public AbstractDataOnDemand() {
        rnd = new SecureRandom();
    }

    public void init() {
        int from = 0;
        int to = 10;
        data = repository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Component' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }

        data = new ArrayList<S>();
        for (int i = 0; i < 10; i++) {
            S obj = getNewTransientObject(i);
            try {
                repository.save(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            repository.flush();
            data.add(obj);
        }
    }

    public abstract S getNewTransientObject(int i);
}
