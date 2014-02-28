package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Moritz Schulze
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
