package com.exercise.PersonalRegistry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @Column(name = "contact_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "address_id")
    Address address;

    @ManyToOne
    @JoinColumn(name = "contact_type_id")
    ContactType contactType;

    @Column(name = "contact_value", nullable = false)
    String value;

    public Contact(Address address, ContactType contactType, String value) {
        this.address = address;
        this.contactType = contactType;
        this.value = value;
    }
}
