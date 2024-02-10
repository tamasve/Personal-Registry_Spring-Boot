package com.exercise.PersonalRegistry.controller;

import com.exercise.PersonalRegistry.entity.*;
import com.exercise.PersonalRegistry.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// -- CONTACT TYPES HANDLER --
@Controller
@RequestMapping("/contact-types")
public class ContactTypeController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private boolean newRow;

    ContactTypeService contactTypeService;
    @Autowired
    public ContactTypeController(
                ContactTypeService contactTypeService) {

        this.contactTypeService = contactTypeService;
        newRow = false;
    }

    @GetMapping("")
    public String contactTypes(Model model) {
        model.addAttribute("contactTypes", contactTypeService.findAll());
        model.addAttribute("newRow", newRow);
        return "contact_types";
    }

    @GetMapping("new")
    public String newContactType (Model model) {
        newRow = true;
        model.addAttribute("contactTypes", contactTypeService.findAll());
        model.addAttribute("newRow", newRow);
        model.addAttribute("contactType", new ContactType(""));
        return "contact_types";
    }

    @PostMapping("save")
    public String saveContactType (@ModelAttribute ContactType contactType) {
        if (!contactType.getValue().isEmpty())  contactTypeService.save(contactType);
        newRow = false;
        return "redirect:/contact-types";
    }

    @GetMapping("delete/{id}")
    public String deleteContactType(@PathVariable Long id) {
        contactTypeService.deleteById(id);
        return "redirect:/contact-types";
    }
}
