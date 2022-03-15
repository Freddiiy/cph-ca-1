package repository;

import dtos.*;
import entities.Address;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryTest {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactoryForTest();
    PersonRepository personRepository = PersonRepository.getRepo(emf);
    PersonDTO personDTO;
    List<PhoneDTO> phoneDTOList = new ArrayList<>();


    @BeforeEach
    void setUp() {
        phoneDTOList.add(new PhoneDTO("40404040", "Job"));
        phoneDTOList.add(new PhoneDTO("12345678", "Hjem"));

        List<HobbyDTO> hobbyDTOList = new ArrayList<>();
        hobbyDTOList.add(new HobbyDTO("Fodbold", "Hos KB"));

        personDTO = new PersonDTO("Filibaba",
            "Kastanje",
            new AddressDTO("Munkevej", "I Danmark"),
            new CityInfoDTO(2770, "Kastrup"),
            phoneDTOList,
            hobbyDTOList);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getRepo() {
        assertNotNull(personRepository);
    }

    @Test
    void add() {
        emf.createEntityManager();
        PersonDTO actual = personRepository.add(personDTO);
        PersonDTO expected = personDTO;
        assertEquals(expected.equals(actual), actual.equals(expected));
    }

    @Test
    void delete() {
    }

    @Test
    void getById() {
    }

    @Test
    void getByPhone() {
    }

    @Test
    void getAllByHobby() {
    }

    @Test
    void getAllByCity() {
    }

    @Test
    void getAll() {
    }

    @Test
    void edit() {
    }
}