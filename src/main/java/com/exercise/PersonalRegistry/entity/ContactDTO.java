package com.exercise.PersonalRegistry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    Long id;

    Long addressId;

    Long contactTypeId;

    String value;

//    public ContactDTO(Long addressId, String contactType, String value) {
//        this.addressId = addressId;
//        this.contactType = contactType;
//        this.value = value;
//    }
}
