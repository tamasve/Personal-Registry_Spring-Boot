package com.exercise.PersonalRegistry.repository;

import com.exercise.PersonalRegistry.entity.ContactType;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactTypeRepository extends ListCrudRepository<ContactType, Long> {

}
