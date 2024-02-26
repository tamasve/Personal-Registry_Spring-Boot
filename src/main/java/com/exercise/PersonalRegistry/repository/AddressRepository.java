package com.exercise.PersonalRegistry.repository;

import com.exercise.PersonalRegistry.entity.Address;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends ListCrudRepository<Address, Long> {

    List<Address> findAllByPersonId(Long id);
    void deleteAllByPersonId(Long id);

}
