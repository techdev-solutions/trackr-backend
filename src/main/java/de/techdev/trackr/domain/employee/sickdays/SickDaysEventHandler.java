package de.techdev.trackr.domain.employee.sickdays;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryEventHandler(value = SickDays.class)
@Slf4j
public class SickDaysEventHandler {

    @Autowired
    private SickDaysNotifyService sickDaysNotifyService;

    @HandleBeforeCreate
    @PreAuthorize("#sickDays.employee.email == principal?.username or hasRole('ROLE_ADMIN')")
    public void checkSaveAuthority(SickDays sickDays) {
        log.debug("Creating sickdays {}", sickDays);
        sickDaysNotifyService.notifySupervisorsAboutNew(sickDays);
    }

    @HandleBeforeSave
    @PreAuthorize("#sickDays.employee.email == principal?.username or hasRole('ROLE_ADMIN')")
    public void checkUpdateAuthority(SickDays sickDays) {
        log.debug("Updating sickDays {}", sickDays);
        sickDaysNotifyService.notifySupervisorsAboutUpdate(sickDays);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkDeleteAuthority(SickDays sickDays) {
        log.debug("Deleting sickDays {}", sickDays);
    }

    @HandleBeforeLinkSave
    @PreAuthorize("denyAll()")
    public void checkLinkUpdateAuthority(SickDays sickDays, Object links) {
        //deny all
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("denyAll()")
    public void checkLiknkSaveAuthority(SickDays sickDays) {
        //deny all
    }
}


