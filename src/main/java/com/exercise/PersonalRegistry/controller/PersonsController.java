package com.exercise.PersonalRegistry.controller;

import com.exercise.PersonalRegistry.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


// -- PERSONS PAGE --

@Controller
@RequestMapping("/persons")
public class PersonsController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    PersonService personService;

    @Autowired
    public PersonsController(
            PersonService personService) {

        this.personService = personService;

    }

    @GetMapping("")
    public String persons(Model model) {
        model.addAttribute("persons", personService.findAll());
        return "persons";
    }

    @GetMapping("delete/{id}")
    public String deletePerson(@PathVariable("id") Long id) {
        personService.deleteById(id);
        return "redirect:/persons";
    }

}
