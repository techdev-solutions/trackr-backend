package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Moritz Schulze
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByIdentifier(@Param("identifier") String identifier);

}
