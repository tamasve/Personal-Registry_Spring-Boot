package com.exercise.PersonalRegistry.repository;

import com.exercise.PersonalRegistry.entity.Person;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ListCrudRepository<Person, Long> {

}

