package de.techdev.trackr.domain.project.worktimes;

import de.techdev.trackr.domain.employee.Employee;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@RepositoryEventHandler(WorkTime.class)
@SuppressWarnings("unused")
public class WorkTimeEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("#workTime.employee.email == principal?.username")
    public void checkCreateAuthority(WorkTime workTime) {
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_ADMIN') or #workTime.employee.email == principal?.username")
    public void checkUpdateAuthority(WorkTime workTime) {
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN') or #workTime.employee.email == principal?.username")
    public void checkDeleteAuthority(WorkTime workTime) {
    }

    @HandleBeforeLinkSave
    @PreAuthorize("hasRole('ROLE_ADMIN') or #workTime.employee.email == principal?.username")
    public void checkUpdateLinkAuthority(WorkTime workTime, Object link) throws HttpRequestMethodNotSupportedException {
        if(Employee.class.isAssignableFrom(link.getClass())) {
            throw new HttpRequestMethodNotSupportedException("POST", new String[0]);
        }
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("denyAll()")
    public void checkDeleteLinkAuthority(WorkTime workTime, Object linkedEntity) {
        //deny all, cannot be called
    }
}
