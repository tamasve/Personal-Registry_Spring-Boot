package com.exercise.PersonalRegistry.service;

import com.exercise.PersonalRegistry.entity.Person;

import java.util.List;

public interface PersonService {

    List<Person> findAll();

    Person findById(Long id);

    Person findByIdCard(String idCard);
}
