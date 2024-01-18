package com.exercise.PersonalRegistry.service;

import com.exercise.PersonalRegistry.entity.AddressType;
import com.exercise.PersonalRegistry.repository.AddressTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressTypeServiceImpl implements AddressTypeService{

    AddressTypeRepository addressTypeRepository;
    @Autowired
    public void setAddressTypeRepository(AddressTypeRepository addressTypeRepository) {
        this.addressTypeRepository = addressTypeRepository;
    }

    @Override
    public List<AddressType> findAll() {
        return addressTypeRepository.findAll();
    }
}
