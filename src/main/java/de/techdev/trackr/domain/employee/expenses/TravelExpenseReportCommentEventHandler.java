package de.techdev.trackr.domain.employee.expenses;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(value = TravelExpenseReportComment.class)
@Slf4j
public class TravelExpenseReportCommentEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or ( isAuthenticated() and #comment.employee.id == principal.id )")
    public void checkCreateAuthority(TravelExpenseReportComment comment) {
        comment.setSubmissionDate(new Date());
        log.debug("Creating comment {}", comment);
    }

    @HandleBeforeSave
    @PreAuthorize("denyAll()")
    public void checkUpdateAuthority(TravelExpenseReportComment comment) {
        //deny all
    }

    @HandleBeforeLinkSave
    @PreAuthorize("denyAll()")
    public void checkLinkSaveAuthority(TravelExpenseReportComment comment, Object links) {
        //deny all
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("denyAll()")
    public void checkLinkDeleteAuthority(TravelExpenseReportComment comment) {
        //deny all
    }
}
