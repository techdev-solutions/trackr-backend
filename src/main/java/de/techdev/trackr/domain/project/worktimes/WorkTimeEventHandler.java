package de.techdev.trackr.domain.project.worktimes;

import de.techdev.trackr.domain.employee.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@RepositoryEventHandler(WorkTime.class)
@Slf4j
public class WorkTimeEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("#workTime.employee.email == principal?.username")
    public void checkCreateAuthority(WorkTime workTime) {
        log.debug("Creating work time {}", workTime);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_ADMIN') or #workTime.employee.email == principal?.username")
    public void checkUpdateAuthority(WorkTime workTime) {
        log.debug("Updating work time {}", workTime);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN') or #workTime.employee.email == principal?.username")
    public void checkDeleteAuthority(WorkTime workTime) {
        log.debug("Deleting work time {}", workTime);
    }

    @HandleBeforeLinkSave
    @PreAuthorize("hasRole('ROLE_ADMIN') or #workTime.employee.email == principal?.username")
    public void checkUpdateLinkAuthority(WorkTime workTime, Object link) throws HttpRequestMethodNotSupportedException {
        if(Employee.class.isAssignableFrom(link.getClass())) {
            throw new HttpRequestMethodNotSupportedException("POST", new String[0]);
        }
        log.debug("Updating links {} in work time {}", link, workTime);
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("denyAll()")
    public void checkDeleteLinkAuthority(WorkTime workTime) {
        //deny all, cannot be called
    }
}
