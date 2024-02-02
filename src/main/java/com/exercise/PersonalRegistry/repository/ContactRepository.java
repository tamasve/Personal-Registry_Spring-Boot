package com.exercise.PersonalRegistry.repository;

import com.exercise.PersonalRegistry.entity.Contact;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends ListCrudRepository<Contact, Long> {

    List<Contact> findAllByAddressId(Long id);

}
