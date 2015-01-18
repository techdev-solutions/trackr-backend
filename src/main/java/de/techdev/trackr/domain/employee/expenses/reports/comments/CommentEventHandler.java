package de.techdev.trackr.domain.employee.expenses.reports.comments;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;

@RepositoryEventHandler(value = Comment.class)
@Slf4j
public class CommentEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or #comment.employee.email == principal?.username")
    public void checkCreateAuthority(Comment comment) {
        comment.setSubmissionDate(new Date());
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
