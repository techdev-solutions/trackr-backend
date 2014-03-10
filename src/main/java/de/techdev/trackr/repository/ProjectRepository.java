package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Moritz Schulze
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByIdentifier(@Param("identifier") String identifier);

    List<Project> findByNameLikeOrIdentifierLikeOrderByNameAsc(@Param("name") String name, @Param("identifier") String identifier);
}
