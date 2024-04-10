package com.exercise.PersonalRegistry.controller;

import com.exercise.PersonalRegistry.entity.*;
import com.exercise.PersonalRegistry.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PersonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // the person object id that is currently shown on Person page
    Long shownPersonId;

    // flags and data holder in case of new records / modifications
    Long modifiedContactId;
    Long modifiedAddressId;

    // flag for new / modified items >> "cancel" can mean cancel or delete
    String cancel;

    // for filling out the form of a new Person
    private Person newPerson;


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


// -- PERSON PAGE --

    // -- Common  data objects for every rendering --
    @ModelAttribute
    public void dataHandover(Model model) {

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

    // -- The central  renderer method --
    @GetMapping("/person")
    public String person(Model model) {
        model.addAttribute("person", personService.findById(shownPersonId));
        return "person";
    }

    // select a person by id card
    @GetMapping("/persons/select/{cardId}")
    public String selectPerson(@PathVariable("cardId") String cardId, Model model) {
        cardId = cardId.substring(0, 8);
        Person person = personService.findByIdCard(cardId);
        shownPersonId = person.getId();
        model.addAttribute("person", person);
        model.addAttribute("modifiedContactId", 0);     // no Contact to modify
        return "person";
    }

    // put a NEW CONTACT row to a given address of the selected person:
    // save a new bianco Contact with the 1st type into DB then offer it for modification
    @GetMapping("persons/{personId}/addresses/{addressId}/contacts/new")
    public  String newContactForAddress(@PathVariable("personId") Long personId, @PathVariable("addressId") Long addressId, Model model) {
        modifiedContactId = contactService.save(
                new Contact( addressService.findById(addressId),
                                            contactTypeService.first(),
                            " ")
            ).getId();
        shownPersonId = personId;
        cancel = "delete";
        return "redirect:/person";
    }

    // back from the mini-form: get all field values from the DTO and set them to the new Contact object, then save it into DB
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

    // modify 1 contact of an address
    @GetMapping("/contacts/modify/{id}")
    public String modifyContact(@PathVariable("id") Long id) {
        if (modifiedAddressId == 0L && modifiedContactId == 0L)  modifiedContactId = id;       // only 1 new or modified item at the same time
        cancel = "cancel";          // to cancel modification, not delete
        return "redirect:/person";
    }

    // delete a contact of an address
    @GetMapping("/contacts/delete/{id}")
    public String deleteContact(@PathVariable("id") Long id) {
        contactService.deleteById(id);
        modifiedContactId = 0L;         // not to modify anymore: print data row instead of input form (in case of cancellation of a new contact)
        return "redirect:/person";
    }

    // cancel modification of a contact of an address
    @GetMapping("/contacts/cancel/{id}")
    public String cancelContact(@PathVariable("id") Long id) {
        modifiedContactId = 0L;         // not to modify anymore: print data row instead of input form (in case of cancellation of a new contact)
        return "redirect:/person";
    }

    // put a NEW ADDRESS row to the selected person if it has less than 2 addresses:
    // save a new bianco Address with the missing type into DB then offer it for modification
    @GetMapping("/addresses/new")
    public String newAddress(Model model) {

        Person person = personService.findById(shownPersonId);
        List<Address> addresses = person.getAddresses();
        if ( addresses.size() > 1 )  return "redirect:/person";

        Long addressTypeId = addresses.get(0).getAddressType().getId();
        addressTypeId = addressTypeId == 1L ? 2L : 1L;          // only 2 types possible

        modifiedAddressId = addressService.save(
                new Address( person,
                                            addressTypeService.findById(addressTypeId),
                        0,
                                    "",
                                "",
                            0)
        ).getId();

        cancel = "delete";      // this is a new Address >> cancel means delete
        return "redirect:/person";
    }

    // coming back from the page with the new Address object filled out >> save it into DB after data check
    @PostMapping("/addresses/new/save")
    public String saveNewAddress(@ModelAttribute("newAddress") Address newAddress) {
        // no part of an address can be empty
        if (newAddress.getPostalCode() == 0 || newAddress.getCity().isEmpty() || newAddress.getPlace().isEmpty() || newAddress.getNumber() == 0)  return "redirect:/person";
        addressService.save(newAddress);
        modifiedAddressId = 0L;         // clear flag of new address
        if (cancel.equals("delete"))
            modifiedContactId = contactService.save(
                    new Contact( newAddress,
                                                contactTypeService.first(),
                                    " ")
            ).getId();   // aut.ly create a new contact for the new address
        return "redirect:/person";
    }

    // modify an Address of the selected person
    @GetMapping("/addresses/modify/{id}")
    public String modifyAddress(@PathVariable("id") Long id) {
        if (modifiedAddressId == 0L && modifiedContactId == 0L)  modifiedAddressId = id;       // only 1 new or modified item at the same time
        cancel = "cancel";
        return "redirect:/person";
    }

    // delete an Address of the selected person >> delete in DB
    @GetMapping("/addresses/delete/{id}")
    public String deleteAddress(@PathVariable("id") Long id) {
        addressService.deleteById(id);
        modifiedAddressId = 0L;     // maybe it was a cancel... clear id of address under modification after new...
        return "redirect:/person";
    }

    // cancel an Address under modification of the selected person >> only clear modification flag
    @GetMapping("/addresses/cancel/{id}")
    public String cancelAddress(@PathVariable("id") Long id) {
        modifiedAddressId = 0L;     // maybe it was a cancel... clear id of address under modification after new...
        return "redirect:/person";
    }


    // -- NEW PERSON FORM --

    // only for creating a new Person - but all addresses and their contacts will be created after this action on the Person page...
    @GetMapping("/new-person")
    public String newPerson(Model model) {
        newPerson = new Person();
        model.addAttribute("person", newPerson);
        return "newperson";
    }

    // save new person, then go to Person page with 1 created Address to be filled out...
    @PostMapping("/new-person/save")
    public String savePerson(@ModelAttribute("person") Person person){
        shownPersonId = personService.save(person).getId();
        modifiedAddressId = addressService.save(
                new Address( person,
                                            addressTypeService.findById(1L),
                        0,
                                    "",
                                "",
                            0)
        ).getId();
        cancel = "delete";
        return "redirect:/person";
    }

}
