package com.exercise.PersonalRegistry.repository;

import com.exercise.PersonalRegistry.entity.Address;
import com.exercise.PersonalRegistry.entity.AddressType;
import com.exercise.PersonalRegistry.entity.Person;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AddressRepositoryTests {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressTypeRepository addressTypeRepository;

    @Autowired
    private PersonRepository personRepository;


    @Test
    public void AddressRepository_save_ReturnSavedAddress() {

        AddressType addressType = addressTypeRepository.save(
                AddressType.builder()
                        .value("Ideiglenes")
                        .build()
        );
        Person person = personRepository.save(
                Person.builder()
                        .firstName("John")
                        .lastName("Last")
                        .idCard("RT978798")
                        .build()
        );
        Address savedAddress = addressRepository.save(
                Address.builder()
                        .person(person)
                        .addressType(addressType)
                        .city("Budapest")
                        .postalCode(1111)
                        .place("Budafoki út")
                        .number(8)
                        .build()
        );

        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getId()).isGreaterThan(0);

    }

    @Test
    public void AddressRepository_findById_ReturnAddressById() {

        AddressType addressType = addressTypeRepository.save(
                AddressType.builder()
                        .value("Ideiglenes")
                        .build()
        );
        Person person = personRepository.save(
                Person.builder()
                        .firstName("John")
                        .lastName("Last")
                        .idCard("RT978798")
                        .build()
        );
        Address savedAddress = addressRepository.save(
                Address.builder()
                        .person(person)
                        .addressType(addressType)
                        .city("Budapest")
                        .postalCode(1111)
                        .place("Budafoki út")
                        .number(8)
                        .build()
        );

        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getId()).isGreaterThan(0);

        Address foundAddress = addressRepository.findById( savedAddress.getId() ).orElse(null);
        assertThat(foundAddress).isNotNull();
        assertThat(foundAddress.getCity()).isEqualTo(savedAddress.getCity());
        assertThat(foundAddress.getNumber()).isEqualTo(savedAddress.getNumber());

    }


    @Test
    public void AddressRepository_findAll_ReturnAddressList() {

        AddressType addressType = addressTypeRepository.save(
                AddressType.builder()
                        .value("Ideiglenes")
                        .build()
        );
        Person person = personRepository.save(
                Person.builder()
                        .firstName("John")
                        .lastName("Last")
                        .idCard("RT978798")
                        .build()
        );
        Address savedAddress1 = addressRepository.save(
                Address.builder()
                        .person(person)
                        .addressType(addressType)
                        .city("Budapest")
                        .postalCode(1111)
                        .place("Budafoki út")
                        .number(8)
                        .build()
        );
        Address savedAddress2 = addressRepository.save(
                Address.builder()
                        .person(person)
                        .addressType(addressType)
                        .city("Humbug")
                        .postalCode(2211)
                        .place("Macko utca")
                        .number(58)
                        .build()
        );

        assertThat(savedAddress1).isNotNull();
        assertThat(savedAddress2).isNotNull();

        List<Address> addresses = addressRepository.findAll();
        assertThat(addresses).isNotNull();
        assertThat(addresses.size()).isEqualTo(2);
        assertThat(addresses.get(0).getCity()).isEqualTo(savedAddress1.getCity());
        assertThat( addresses.get(1).getAddressType().getValue() ).isEqualTo( savedAddress2.getAddressType().getValue() );

    }

    @Test
    public void AddressRepository_findByPersonId_ReturnAddressListOfAPerson() {

        AddressType addressType = addressTypeRepository.save(
                AddressType.builder()
                        .value("Ideiglenes")
                        .build()
        );
        Person person1 = personRepository.save(
                Person.builder()
                        .firstName("John")
                        .lastName("Last")
                        .idCard("RT978798")
                        .build()
        );
        Address savedAddress1 = addressRepository.save(
                Address.builder()
                        .person(person1)
                        .addressType(addressType)
                        .city("Budapest")
                        .postalCode(1111)
                        .place("Budafoki út")
                        .number(8)
                        .build()
        );
        Address savedAddress2 = addressRepository.save(
                Address.builder()
                        .person(person1)
                        .addressType(addressType)
                        .city("Humbug")
                        .postalCode(2211)
                        .place("Macko utca")
                        .number(58)
                        .build()
        );

        Person person2 = personRepository.save(
                Person.builder()
                        .firstName("Bela")
                        .lastName("Kovac")
                        .idCard("ZT976498")
                        .build()
        );
        Address savedAddress3 = addressRepository.save(
                Address.builder()
                        .person(person2)
                        .addressType(addressType)
                        .city("Szeged")
                        .postalCode(6789)
                        .place("Irinyi út")
                        .number(88)
                        .build()
        );

        assertThat(savedAddress1).isNotNull();
        assertThat(savedAddress2).isNotNull();
        assertThat(savedAddress3).isNotNull();

        List<Address> addresses = addressRepository.findAllByPersonId( person1.getId() );
        assertThat(addresses).isNotNull();
        assertThat(addresses.size()).isEqualTo(2);
        assertThat(addresses.get(0).getCity()).isEqualTo(savedAddress1.getCity());
        assertThat( addresses.get(1).getAddressType().getValue() ).isEqualTo( savedAddress2.getAddressType().getValue() );

    }

    @Test
    public void AddressRepository_delete_AddressDeleted() {

        AddressType addressType = addressTypeRepository.save(
                AddressType.builder()
                        .value("Ideiglenes")
                        .build()
        );
        Person person = personRepository.save(
                Person.builder()
                        .firstName("John")
                        .lastName("Last")
                        .idCard("RT978798")
                        .build()
        );
        Address savedAddress = addressRepository.save(
                Address.builder()
                        .person(person)
                        .addressType(addressType)
                        .city("Budapest")
                        .postalCode(1111)
                        .place("Budafoki út")
                        .number(8)
                        .build()
        );

        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getId()).isGreaterThan(0);

        addressRepository.delete(savedAddress);
        Address foundAddress = addressRepository.findById(savedAddress.getId()).orElse(null);
        assertThat(foundAddress).isNull();

    }

    @Test
    public void AddressRepository_deleteById_AddressDeleted() {

        AddressType addressType = addressTypeRepository.save(
                AddressType.builder()
                        .value("Ideiglenes")
                        .build()
        );
        Person person = personRepository.save(
                Person.builder()
                        .firstName("John")
                        .lastName("Last")
                        .idCard("RT978798")
                        .build()
        );
        Address savedAddress = addressRepository.save(
                Address.builder()
                        .person(person)
                        .addressType(addressType)
                        .city("Budapest")
                        .postalCode(1111)
                        .place("Budafoki út")
                        .number(8)
                        .build()
        );

        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getId()).isGreaterThan(0);

        addressRepository.deleteById(savedAddress.getId());
        Address foundAddress = addressRepository.findById(savedAddress.getId()).orElse(null);
        assertThat(foundAddress).isNull();

    }
}
