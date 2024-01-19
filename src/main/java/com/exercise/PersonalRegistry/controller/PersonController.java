package com.exercise.PersonalRegistry.controller;

import com.exercise.PersonalRegistry.entity.Person;
import com.exercise.PersonalRegistry.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PersonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Person selectedPerson;

    PersonService personService;
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
        selectedPerson = personService.findById(1L);
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/persons")
    public String persons(Model model) {
        model.addAttribute("persons", personService.findAll());
        log.info(personService.findAll().get(0).getAddresses().get(0).getCity());
        return "persons";
    }

    @GetMapping("/person")
    public String person(Model model) {
        model.addAttribute("person", personService.findById(1L));
        model.addAttribute("persons", personService.findAll());
        return "person";
    }

    @GetMapping("/persons/select/{id}")
    public String selectPerson(@PathVariable("id") String id, Model model) {
        id = id.substring(0, 8);
        model.addAttribute("person", personService.findByIdCard(id));
        model.addAttribute("persons", personService.findAll());
        return "person";
    }

    @GetMapping("/persons/delete/{id}")
    public String deletePerson(@PathVariable("id") Long id) {
        log.info(personService.findById(id).getFirstName());
        return "home";
    }

    @GetMapping("/addresses/delete/{id}")
    public String deleteAddress(@PathVariable("id") Long id) {
        return "home";
    }

    @GetMapping("/new-person")
    public String newPerson() {
        return "home";
    }

    @GetMapping("/contact-types")
    public String contactTypes() {
        return "home";
    }
}
