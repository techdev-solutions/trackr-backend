package de.techdev.trackr.domain.employee.login;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author Moritz Schulze
 */
public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    @Override
    @RestResource(exported = false)
    void delete(Long aLong);

    @Override
    @RestResource(exported = false)
    <S extends Authority> S save(S entity);
}
