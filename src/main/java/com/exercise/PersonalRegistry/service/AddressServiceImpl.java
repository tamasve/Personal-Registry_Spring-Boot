package com.exercise.PersonalRegistry.service;

import com.exercise.PersonalRegistry.entity.Address;
import com.exercise.PersonalRegistry.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    AddressRepository addressRepository;
    ContactService contactService;
    @Autowired
    public AddressServiceImpl(
            AddressRepository addressRepository,
            ContactService contactService) {
        this.addressRepository = addressRepository;
        this.contactService = contactService;
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public List<Address> findByPersonId(Long id) {
        return addressRepository.findByPersonId(id);
    }

    @Override
    public void deleteAllByPersonId(Long id) {
        for (Address address : findByPersonId(id)) {
            contactService.deleteAllByAddressId( address.getId() );
            addressRepository.delete(address);
        }
    }

    @Override
    public void deleteById(Long id) {
        contactService.deleteAllByAddressId(id);
        addressRepository.deleteById(id);
    }

    @Override
    public Address findById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }
}
