package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * @author Moritz Schulze
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Override
    @RestResource(exported = false)
    List<Address> findAll();

    @Override
    @RestResource(exported = false)
    Page<Address> findAll(Pageable pageable);

    @Override
    @RestResource(exported = false)
    List<Address> findAll(Sort sort);

    @Override
    @RestResource(exported = false)
    void delete(Long aLong);

    @Override
    @RestResource(exported = false)
    void delete(Address entity);
}
