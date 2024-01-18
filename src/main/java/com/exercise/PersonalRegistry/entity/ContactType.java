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
@Table(name = "contact_types")
public class ContactType {

    @Id
    @Column(name = "contact_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "contact_type_value", nullable = false)
    String value;

    @JsonBackReference
    @OneToMany(mappedBy = "contactType")
    List<Contact> contacts;

    public ContactType(String value) {
        this.value = value;
    }
}
