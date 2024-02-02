package com.exercise.PersonalRegistry.controller;

import com.exercise.PersonalRegistry.entity.*;
import com.exercise.PersonalRegistry.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContactController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // for filling out the form for a new Person with all its addresses and their contacts
    private boolean newRow;

    // DI for all service classes
//    PersonService personService;
//    AddressService addressService;
//    AddressTypeService addressTypeService;
//    ContactService contactService;
    ContactTypeService contactTypeService;
    @Autowired
    public ContactController(
//                PersonService personService,
//                AddressService addressService,
//                AddressTypeService addressTypeService,
//                ContactService contactService,
                ContactTypeService contactTypeService) {
//        this.personService = personService;
//        this.addressService = addressService;
//        this.addressTypeService = addressTypeService;
//        this.contactService = contactService;
        this.contactTypeService = contactTypeService;
        newRow = false;
    }


    @GetMapping("/contact-types")
    public String contactTypes(Model model) {
        model.addAttribute("contactTypes", contactTypeService.findAll());
        model.addAttribute("newRow", newRow);
        return "contact_types";
    }

    @GetMapping("/contact-types/delete/{id}")
    public String deleteContactType(@PathVariable Long id) {
        contactTypeService.deleteById(id);
        return "redirect:/contact-types";
    }

    @GetMapping("contact-types/new")
    public String newContactType (Model model) {
        newRow = true;
        model.addAttribute("contactTypes", contactTypeService.findAll());
        model.addAttribute("newRow", newRow);
        model.addAttribute("contactType", new ContactType(""));
        return "contact_types";
    }

    @PostMapping("contact-types/save")
    public String saveContactType (@ModelAttribute ContactType contactType) {
        if (!contactType.getValue().isEmpty())  contactTypeService.save(contactType);
        newRow = false;
        return "redirect:/contact-types";
    }
}
