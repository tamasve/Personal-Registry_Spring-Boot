package com.exercise.PersonalRegistry.repository;

import com.exercise.PersonalRegistry.entity.AddressType;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressTypeRepository extends ListCrudRepository<AddressType, Long> {

}
