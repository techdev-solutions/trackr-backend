package de.techdev.trackr.domain.employee;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface SettingsRepository extends Repository<Settings, Long> {

    Settings save(Settings settings);

    List<Settings> findByEmployee_Email(String email);

    Settings findByTypeAndEmployee_Email(Settings.SettingsType type, String email);

}
