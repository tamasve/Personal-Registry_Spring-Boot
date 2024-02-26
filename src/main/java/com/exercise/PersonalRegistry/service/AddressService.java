package com.exercise.PersonalRegistry.service;

import com.exercise.PersonalRegistry.entity.Address;

import java.util.List;

public interface AddressService {

    List<Address> findAll();
    List<Address> findAllByPersonId(Long id);
    void deleteAllByPersonId(Long id);

    void deleteById(Long id);

    Address findById(Long id);

    Address save(Address address);
}
