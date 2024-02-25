package com.exercise.PersonalRegistry.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Column(name = "id_card", nullable = false)
    String idCard;

    @JsonBackReference
    @OneToMany(mappedBy = "person")
    List<Address> addresses;

    public Person(String firstName, String lastName, String idCard) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idCard = idCard;
    }
}
