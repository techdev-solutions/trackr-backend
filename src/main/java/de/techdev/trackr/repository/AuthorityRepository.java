package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author Moritz Schulze
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    @Override
    @RestResource(exported = false)
    void delete(Long aLong);

    @Override
    @RestResource(exported = false)
    <S extends Authority> S save(S entity);
}
