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
}
