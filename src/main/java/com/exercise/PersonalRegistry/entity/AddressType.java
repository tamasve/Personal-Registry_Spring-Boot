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
@Table(name = "address_types")
public class AddressType {

    @Id
    @Column(name = "address_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "address_type_value", nullable = false)
    String value;

    @JsonBackReference
    @OneToMany(mappedBy = "addressType")
    List<Address> addresses;

    public AddressType(String value) {
        this.value = value;
    }
}
