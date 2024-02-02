package com.exercise.PersonalRegistry.service;

import com.exercise.PersonalRegistry.entity.Person;
import com.exercise.PersonalRegistry.repository.AddressRepository;
import com.exercise.PersonalRegistry.repository.ContactRepository;
import com.exercise.PersonalRegistry.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{

    PersonRepository personRepository;
    AddressService addressService;
    ContactService contactService;
    @Autowired
    public PersonServiceImpl(
            PersonRepository personRepository,
            AddressService addressService,
            ContactService contactService){
        this.personRepository = personRepository;
        this.addressService = addressService;
        this.contactService = contactService;
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person findById(Long id) {
        return personRepository.findById(id).orElseThrow();
    }

    @Override
    public Person findByIdCard(String idCard) {
        return personRepository.findByIdCard(idCard);
    }

    @Override
    public void deleteById(Long id) {
        addressService.deleteAllByPersonId(id);
        personRepository.deleteById(id);
    }
}
