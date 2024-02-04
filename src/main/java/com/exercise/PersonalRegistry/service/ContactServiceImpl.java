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

    @Override
    public void deleteAllByAddressId(Long id) {
        for (Contact contact : contactRepository.findAllByAddressId(id)) {
            contactRepository.delete(contact);
        }
    }

    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact findById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }

}
