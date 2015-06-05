package de.techdev.trackr.domain.employee.sickdays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryEventHandler(value = SickDays.class)
@SuppressWarnings("unused")
public class SickDaysEventHandler {

    @Autowired
    private SickDaysNotifyService sickDaysNotifyService;

    @HandleBeforeCreate
    @PreAuthorize("#sickDays.employee.email == principal?.username or hasRole('ROLE_ADMIN')")
    public void checkSaveAuthority(SickDays sickDays) {
        sickDaysNotifyService.notifySupervisorsAboutNew(sickDays);
    }

    @HandleBeforeSave
    @PreAuthorize("#sickDays.employee.email == principal?.username or hasRole('ROLE_ADMIN')")
    public void checkUpdateAuthority(SickDays sickDays) {
        sickDaysNotifyService.notifySupervisorsAboutUpdate(sickDays);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkDeleteAuthority(SickDays sickDays) {
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


