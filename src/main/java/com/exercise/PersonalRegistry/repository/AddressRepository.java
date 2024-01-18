package com.exercise.PersonalRegistry.repository;

import com.exercise.PersonalRegistry.entity.Address;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends ListCrudRepository<Address, Long> {

}
