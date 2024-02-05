package com.exercise.PersonalRegistry.service;

import com.exercise.PersonalRegistry.entity.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> findAll();

    void deleteAllByAddressId(Long id);

    Contact save(Contact contact);

    Contact findById(Long id);

    void deleteById(Long id);

    void delete(Contact contact);
}
