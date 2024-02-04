package com.exercise.PersonalRegistry.service;

import com.exercise.PersonalRegistry.entity.ContactType;

import java.util.List;

public interface ContactTypeService {

    List<ContactType> findAll();

    void deleteById(Long id);

    ContactType save(ContactType contactType);

    // get the 1st value from DB
    ContactType first();
}
