package com.exercise.PersonalRegistry.controller;

import com.exercise.PersonalRegistry.entity.*;
import com.exercise.PersonalRegistry.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PersonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // for filling out the form for a new Person with all its addresses and their contacts
    private Person newPerson;
    private List<Address> newAddresses;
    private List<Contact> newContacts1, newContacts2;

    // DI for all service classes
    PersonService personService;
    AddressService addressService;
    AddressTypeService addressTypeService;
    ContactService contactService;
    ContactTypeService contactTypeService;

    @Autowired
    public PersonController(
            PersonService personService,
            AddressService addressService,
            AddressTypeService addressTypeService,
            ContactService contactService,
            ContactTypeService contactTypeService) {
        this.personService = personService;
        this.addressService = addressService;
        this.addressTypeService = addressTypeService;
        this.contactService = contactService;
        this.contactTypeService = contactTypeService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/persons")
    public String persons(Model model) {
        model.addAttribute("persons", personService.findAll());
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
        personService.deleteById(id);
        return "redirect:/persons";
    }

    @GetMapping("/addresses/delete/{id}")
    public String deleteAddress(@PathVariable("id") Long id) {
        return "home";
    }

    @GetMapping("/contacts/delete/{id}")
    public String deleteContact(@PathVariable("id") Long id) {
        return "home";
    }

    @GetMapping("/new-person")
    public String newPerson(Model model) {

        newPerson = new Person();
        newAddresses = new ArrayList<>();
        newContacts1 = new ArrayList<>();
        newContacts2 = new ArrayList<>();

        for (AddressType addressType : addressTypeService.findAll()) {
            newAddresses.add(new Address(null, addressType, 0, "", "", 0));
        }
        for (ContactType contactType : contactTypeService.findAll()) {
            newContacts1.add(new Contact(null, contactType, ""));
            newContacts2.add(new Contact(null, contactType, ""));
        }
        model.addAttribute("person", newPerson);
        model.addAttribute("addresses", newAddresses);
        model.addAttribute("contacts1", newContacts1);
        model.addAttribute("contacts2", newContacts2);
        return "newperson";
    }

    @PostMapping("/new-person/save")
    public String savePerson(@ModelAttribute("person") Person person,
                             @ModelAttribute("addresses") List<Address> addresses,
                             @ModelAttribute("contacts1") List<Contact> contacts1,
                             @ModelAttribute("contacts2") List<Contact> contacts2) {
        log.info(person.getLastName());
        log.info(addresses.get(0).getCity());
        return "home";
    }

}
