package com.exercise.PersonalRegistry.service;

import com.exercise.PersonalRegistry.entity.ContactType;
import com.exercise.PersonalRegistry.repository.ContactTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactTypeServiceImpl implements ContactTypeService{

    ContactTypeRepository contactTypeRepository;
    @Autowired
    public void setContactTypeRepository(ContactTypeRepository contactTypeRepository) {
        this.contactTypeRepository = contactTypeRepository;
    }

    @Override
    public List<ContactType> findAll() {
        return contactTypeRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        contactTypeRepository.deleteById(id);
    }

    @Override
    public ContactType save(ContactType contactType) {
        return contactTypeRepository.save(contactType);
    }

    // get the 1st value from DB
    @Override
    public ContactType first() {
        return contactTypeRepository.findAll().get(0);
    }

    @Override
    public ContactType findById(Long id) {
        return contactTypeRepository.findById(id).orElse(null);
    }
}
