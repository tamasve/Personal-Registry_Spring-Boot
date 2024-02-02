package com.exercise.PersonalRegistry.controller;

import com.exercise.PersonalRegistry.entity.Address;
import com.exercise.PersonalRegistry.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataController {

    // DI for all service classes
    PersonService personService;
    AddressService addressService;
    AddressTypeService addressTypeService;
    ContactService contactService;
    ContactTypeService contactTypeService;
    @Autowired
    public DataController(
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

    // only for data test

    @GetMapping("/addressfor5")
    public List<Address> addressfor5() {
        return addressService.findByPersonId(5L);
    }

}
