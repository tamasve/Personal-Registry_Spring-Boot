package com.exercise.PersonalRegistry.service;

import com.exercise.PersonalRegistry.entity.Address;
import com.exercise.PersonalRegistry.entity.AddressType;
import com.exercise.PersonalRegistry.entity.Person;
import com.exercise.PersonalRegistry.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static  org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTests {

    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ContactService contactService;

    private Person person1, person2;
    private AddressType addressType;
    private Address address1, address2, address3;
    private List<Address>addresses, addressesOfPerson1;


    @BeforeEach
    public void init() {

        addressType = AddressType.builder()
                        .value("Ideiglenes")
                        .id(1L)
                        .build();
        person1 = Person.builder()
                        .firstName("John")
                        .lastName("Last")
                        .idCard("RT978798")
                        .id(1L)
                        .build();
        address1 = Address.builder()
                        .person(person1)
                        .addressType(addressType)
                        .city("Budapest")
                        .postalCode(1111)
                        .place("Budafoki út")
                        .number(8)
                        .id(1L)
                        .build();
        address2 = Address.builder()
                        .person(person1)
                        .addressType(addressType)
                        .city("Humbug")
                        .postalCode(2211)
                        .place("Macko utca")
                        .number(58)
                        .id(2L)
                        .build();

        person2 = Person.builder()
                        .firstName("Bela")
                        .lastName("Kovac")
                        .idCard("ZT976498")
                        .id(2L)
                        .build();
        address3 = Address.builder()
                        .person(person2)
                        .addressType(addressType)
                        .city("Szeged")
                        .postalCode(6789)
                        .place("Irinyi út")
                        .number(88)
                        .id(3L)
                        .build();

        addresses = Arrays.asList(address1, address2, address3);
        addressesOfPerson1 = Arrays.asList(address1, address2);
    }


    @Test
    public void AddressService_findAll_ReturnListOfAddresses() {

        when( addressRepository.findAll() ).thenReturn(addresses);

        List<Address> foundAddresses = addressService.findAll();

        assertThat(foundAddresses).isNotNull();
        assertThat(foundAddresses.size()).isEqualTo(addresses.size());

        verify( addressRepository ).findAll();
    }

    @Test
    public void AddressService_findAllByPersonId_ReturnAddressList() {

        when( addressRepository.findAllByPersonId(anyLong()) ).thenReturn(addressesOfPerson1);

        List<Address> foundAddresses = addressService.findAllByPersonId( person1.getId() );

        assertThat(foundAddresses).isNotNull();
        assertThat(foundAddresses.size()).isEqualTo(addressesOfPerson1.size());
        assertThat(foundAddresses.get(0).getCity()).isEqualTo(addressesOfPerson1.get(0).getCity());
        assertThat( foundAddresses.get(1).getAddressType().getValue() ).isEqualTo( addressesOfPerson1.get(1).getAddressType().getValue() );

        verify(addressRepository).findAllByPersonId(anyLong());
    }

    @Test
    public void AddressService_deleteById_ReturnVoid() {

        doNothing().when( contactService ).deleteAllByAddressId( anyLong() );
        doNothing().when( addressRepository ).deleteById( anyLong() );

        assertAll( () -> addressService.deleteById( address1.getId() ) );

        verify( contactService ).deleteAllByAddressId( anyLong() );
        verify( addressRepository ).deleteById( anyLong() );
    }

    @Test
    public void AddressService_deleteAllByPersonId_ReturnVoid() {

        when( addressRepository.findAllByPersonId(anyLong()) ).thenReturn(addressesOfPerson1);
        doNothing().when( contactService ).deleteAllByAddressId( anyLong() );
        doNothing().when( addressRepository ).delete( any(Address.class) );

        assertAll( () -> addressService.deleteAllByPersonId( address1.getId() ) );

        verify( addressRepository ).findAllByPersonId( anyLong() );
        verify( contactService, times(2) ).deleteAllByAddressId( anyLong() );       // max 2 addresses can exist for a person
        verify( addressRepository, times(2) ).delete( any(Address.class) );
    }

    @Test
    public void AddressService_findById_ReturnAddress() {

        when( addressRepository.findById(anyLong()) ).thenReturn(Optional.of(address1));

        assertEquals(address1, addressService.findById(address1.getId()));

        verify( addressRepository ).findById( anyLong() );
    }

    @Test
    public void AddressService_save_ReturnAddress() {

        when( addressRepository.save(any(Address.class)) ).thenReturn(address1);

        Address savedAddress = addressService.save(address1);

        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress).isEqualTo(address1);

        verify( addressRepository ).save( any(Address.class) );
    }

}
