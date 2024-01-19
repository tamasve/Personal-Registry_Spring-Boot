package com.exercise.PersonalRegistry.service;

import com.exercise.PersonalRegistry.entity.Person;
import com.exercise.PersonalRegistry.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{

    PersonRepository personRepository;
    @Autowired
    public void setPersonRepository(PersonRepository personRepository){
        this.personRepository = personRepository;
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
}
