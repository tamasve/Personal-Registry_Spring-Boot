package com.exercise.PersonalRegistry.service;

import com.exercise.PersonalRegistry.entity.Address;

import java.util.List;

public interface AddressService {

    List<Address> findAll();
    List<Address> findByPersonId(Long id);
    void deleteAllByPersonId(Long id);
}
