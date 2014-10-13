package de.techdev.trackr.domain.employee.expenses.reports.comments;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.Instant;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(value = Comment.class)
@Slf4j
public class CommentEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or #comment.employee.id == principal?.id")
    public void checkCreateAuthority(Comment comment) {
        comment.setSubmissionDate(Instant.now());
        log.debug("Creating comment {}", comment);
    }

    @HandleBeforeSave
    @PreAuthorize("denyAll()")
    public void checkUpdateAuthority(Comment comment) {
        //deny all
    }

    @HandleBeforeLinkSave
    @PreAuthorize("denyAll()")
    public void checkLinkSaveAuthority(Comment comment, Object links) {
        //deny all
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("denyAll()")
    public void checkLinkDeleteAuthority(Comment comment) {
        //deny all
    }
}
