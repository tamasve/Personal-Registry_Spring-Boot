package com.exercise.PersonalRegistry.repository;

import com.exercise.PersonalRegistry.entity.Person;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PersonRepositoryTests {

    @Autowired
    private PersonRepository personRepository;


    @Test
    public void PersonRepository_save_ReturnSavedCustomer() {

        Person savedPerson = personRepository.save(
                Person.builder()
                    .firstName("John")
                    .lastName("Last")
                    .idCard("RT978798")
                    .build()
        );
        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getId()).isGreaterThan(0);
    }

    @Test
    public void PersonRepository_findByIdCard_ReturnCustomer() {

        Person savedPerson = personRepository.save(
                Person.builder()
                        .firstName("John")
                        .lastName("Last")
                        .idCard("RT978798")
                        .build()
        );
        Person foundByIdCard = personRepository.findByIdCard("RT978798");
        assertThat(foundByIdCard).isNotNull();
        assertThat(foundByIdCard.getFirstName()).isEqualTo(savedPerson.getFirstName());
    }
}
