package com.exercise.PersonalRegistry.service;

import com.exercise.PersonalRegistry.entity.Address;
import com.exercise.PersonalRegistry.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    AddressRepository addressRepository;
    @Autowired
    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }
}
