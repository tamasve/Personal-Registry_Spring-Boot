package com.exercise.PersonalRegistry.service;

import com.exercise.PersonalRegistry.entity.Contact;
import com.exercise.PersonalRegistry.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService{

    ContactRepository contactRepository;
    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }
}
