package com.exercise.PersonalRegistry.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    Person person;

    @ManyToOne
    @JoinColumn(name = "address_type_id")
    AddressType addressType;

    @Column(name = "address_value")
    String value;

    @JsonBackReference
    @OneToMany(mappedBy = "address")
    List<Contact> contacts;

    public Address(Person person, AddressType addressType, String value) {
        this.person = person;
        this.addressType = addressType;
        this.value = value;
    }
}
