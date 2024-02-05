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

    // the person object id that is currently shown on Person page
    Long shownPersonId;

    // flags and data holder in case of new records / modifications
    Long modifiedContactId;
    Long modifiedAddressId;

    // flag for new / modified items >> "cancel" means cancel or delete?
    String cancel;

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

        shownPersonId = 1L;     // default: first registered
        modifiedContactId = 0L;     // default: no modification
        modifiedAddressId = 0L;    // default: no modification
        cancel = "cancel";      // default: no deletion - this sets the mapping for the Cancel buttons on the form to different dispatcher methods
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }


    // -- PERSONS PAGE --

    @GetMapping("/persons")
    public String persons(Model model) {
        model.addAttribute("persons", personService.findAll());
        return "persons";
    }

    @GetMapping("/persons/delete/{id}")
    public String deletePerson(@PathVariable("id") Long id) {
        personService.deleteById(id);
        return "redirect:/persons";
    }


// -- PERSON PAGE --

    // common objects needed in the Model - this method runs first at every request...
    @ModelAttribute
    public void handover(Model model) {

        model.addAttribute("persons", personService.findAll());
        model.addAttribute("contactTypes", contactTypeService.findAll());
        model.addAttribute("modifiedAddressId", modifiedAddressId);
        model.addAttribute("modifiedContactId", modifiedContactId);
        model.addAttribute("cancel", cancel);

        // give the new address object to the Model
        if (modifiedAddressId != 0L)
            model.addAttribute("newAddress", addressService.findById(modifiedAddressId));
        else
            model.addAttribute("newAddress", null);

        // give a DTO of the new contact to the Model
        if (modifiedContactId != 0L) {
            Contact contact = contactService.findById(modifiedContactId);
            model.addAttribute("contactDTO",
                    new ContactDTO(modifiedContactId,
                                                        contact.getAddress().getId(),
                                                        contact.getContactType().getId(),
                                                        contact.getValue()));
        } else {
            model.addAttribute("contactDTO",
                    new ContactDTO(0L,
                                                        0L,
                                                        contactTypeService.first().getId(),
                                                        " "));
        }
    }

    @GetMapping("/person")
    public String person(Model model) {
        model.addAttribute("person", personService.findById(shownPersonId));
        return "person";
    }

    @GetMapping("/persons/select/{cardId}")
    public String selectPerson(@PathVariable("cardId") String cardId, Model model) {
        cardId = cardId.substring(0, 8);
        Person person = personService.findByIdCard(cardId);
        shownPersonId = person.getId();
        model.addAttribute("person", person);
        model.addAttribute("modifiedContactId", 0);     // no Contact to modify
        return "person";
    }

    // put a NEW CONTACT row to a given address of the person:
    // save a new bianco Contact with the 1st type into DB then offer it for modification
    @GetMapping("persons/{personId}/addresses/{addressId}/contacts/new")
    public  String newContactForAddress(@PathVariable("personId") Long personId, @PathVariable("addressId") Long addressId, Model model) {
        modifiedContactId = contactService.save( new Contact( addressService.findById(addressId), contactTypeService.first(), " ") ).getId();
        shownPersonId = personId;
        cancel = "delete";
        return "redirect:/person";
    }

    // back from the mini-form: get all field values from the DTO and set them to the new Contact object, then save it
    @PostMapping("/contacts/save/{id}")
    public String saveContact(@PathVariable("id") Long id, @ModelAttribute("contactDTO") ContactDTO contactDTO) {
        Contact contact = contactService.findById(id);
        contact.setContactType( contactTypeService.findById( contactDTO.getContactTypeId() ) );
        String contactValue = contactDTO.getValue();
        if (contactValue.isEmpty())   return "redirect:/person";        // check if contact has no empty value

        contact.setValue( contactValue );
        contactService.save(contact);
        modifiedContactId = 0L;         // not to modify anymore: print data row instead of input form
        return "redirect:/person";
    }

    @GetMapping("/contacts/modify/{id}")
    public String modifyContact(@PathVariable("id") Long id) {
        if (modifiedAddressId == 0L && modifiedContactId == 0L)  modifiedContactId = id;       // only 1 new or modified item at the same time
        cancel = "cancel";
        return "redirect:/person";
    }

    @GetMapping("/contacts/delete/{id}")
    public String deleteContact(@PathVariable("id") Long id) {
        contactService.deleteById(id);
        modifiedContactId = 0L;         // not to modify anymore: print data row instead of input form (in case of cancellation of a new contact)
        return "redirect:/person";
    }

    @GetMapping("/contacts/cancel/{id}")
    public String cancelContact(@PathVariable("id") Long id) {
        modifiedContactId = 0L;         // not to modify anymore: print data row instead of input form (in case of cancellation of a new contact)
        return "redirect:/person";
    }

    @GetMapping("/addresses/new")
    public String newAddress(Model model) {

        Person person = personService.findById(shownPersonId);
        List<Address> addresses = person.getAddresses();
        if ( addresses.size() > 1 )  return "redirect:/person";

        Long addressTypeId = addresses.get(0).getAddressType().getId();
        addressTypeId = addressTypeId == 1L ? 2L : 1L;          // only 2 types possible

        modifiedAddressId = addressService.save( new Address( person, addressTypeService.findById(addressTypeId), 0, "", "", 0) ).getId();

        cancel = "delete";
        return "redirect:/person";
    }

    @PostMapping("/addresses/new/save")
    public String saveNewAddress(@ModelAttribute("newAddress") Address newAddress) {
        if (newAddress.getPostalCode() == 0 || newAddress.getCity().isEmpty() || newAddress.getPlace().isEmpty() || newAddress.getNumber() == 0)  return "redirect:/person";    // no value of address can be empty
        addressService.save(newAddress);
        modifiedAddressId = 0L;         // clear flag of new address
        if (cancel.equals("delete"))
            modifiedContactId = contactService.save( new Contact( newAddress, contactTypeService.first(), " ") ).getId();   // aut.ly create a new contact for the new address
        return "redirect:/person";
    }

    @GetMapping("/addresses/modify/{id}")
    public String modifyAddress(@PathVariable("id") Long id) {
        if (modifiedAddressId == 0L && modifiedContactId == 0L)  modifiedAddressId = id;       // only 1 new or modified item at the same time
        cancel = "cancel";
        return "redirect:/person";
    }

    @GetMapping("/addresses/delete/{id}")
    public String deleteAddress(@PathVariable("id") Long id) {
        addressService.deleteById(id);
        modifiedAddressId = 0L;     // maybe it was a cancel... clear id of address under modification after new...
        return "redirect:/person";
    }

    @GetMapping("/addresses/cancel/{id}")
    public String cancelAddress(@PathVariable("id") Long id) {
        modifiedAddressId = 0L;     // maybe it was a cancel... clear id of address under modification after new...
        return "redirect:/person";
    }


    // -- NEW PERSON FORM --

    // only for creating a new Person - but all addresses and their contacts will be created on the Person page...
    @GetMapping("/new-person")
    public String newPerson(Model model) {
        newPerson = new Person();
        model.addAttribute("person", newPerson);
        return "newperson";
    }

    // save new person, then go to Person page with 1 created Address to fill out...
    @PostMapping("/new-person/save")
    public String savePerson(@ModelAttribute("person") Person person){
        shownPersonId = personService.save(person).getId();
        modifiedAddressId = addressService.save( new Address( person, addressTypeService.findById(1L), 0, "", "", 0) ).getId();
        cancel = "delete";
        return "redirect:/person";
    }

}
