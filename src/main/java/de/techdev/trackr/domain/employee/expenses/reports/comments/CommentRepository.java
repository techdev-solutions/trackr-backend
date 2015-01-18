package de.techdev.trackr.domain.employee.expenses.reports.comments;

import de.techdev.trackr.domain.employee.expenses.reports.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RepositoryRestResource(path = "travelExpenseReportComments")
public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Override
    @RestResource(exported = false)
    Iterable<Comment> findAll();

    @Override
    @RestResource(exported = false)
    Comment findOne(Long aLong);

    @Override
    @RestResource(exported = false)
    void delete(Long aLong);

    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or #travelExpenseReport.employee.email == principal?.username")
    List<Comment> findByTravelExpenseReportOrderBySubmissionDateAsc(@Param("report") Report travelExpenseReport);
}
