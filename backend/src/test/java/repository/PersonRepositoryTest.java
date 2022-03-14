package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.*;
import entities.*;
import jakarta.ws.rs.core.Application;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rest.ApplicationConfig;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryTest {

    private static final EntityManagerFactory emf = EMF_Creator.createEntityManagerFactoryForTest();

    private static final PersonRepository personRepository = PersonRepository.getRepo(emf);


    //Testing add()
    List<PhoneDTO> phoneDTOList_1 = new ArrayList<>();
    List<HobbyDTO> hobbyDTOList_1 = new ArrayList<>();
    PersonDTO personDTO_1;

    //Testing delete()
    List<Phone> phoneList_1 = new ArrayList<>();
    List<Hobby> hobbyList_1 = new ArrayList<>();
    Person person_1;

    //Testing getByPhone()
    List<Phone> phoneList_2 = new ArrayList<>();
    List<Hobby> hobbyList_2 = new ArrayList<>();
    Person person_2;

    //Testing getALlByHobby()
    List<Phone> phoneList_3 = new ArrayList<>();
    List<Phone> phoneList_4 = new ArrayList<>();
    List<Hobby> hobbyList_3 = new ArrayList<>();
    List<Hobby> hobbyList_4 = new ArrayList<>();
    Person person_3;
    Person person_4;

    //Testing edit()
    List<Phone> phoneList_5 = new ArrayList<>();
    List<Hobby> hobbyList_5 = new ArrayList<>();
    Person person_5;


    @BeforeEach
    void setUp() {
        //Testing add()
        PhoneDTO beansDusMobil = new PhoneDTO("22505044", "HotlineBean");
        phoneDTOList_1.add(beansDusMobil);
        HobbyDTO beansHobby = new HobbyDTO("Bukser", "21 års erfaren bokser");
        hobbyDTOList_1.add(beansHobby);
        personDTO_1 = new PersonDTO("Bean", "Beanson",
                new AddressDTO("Krammervej 4", "BeanStreet"),
                new CityInfoDTO(2150, "Nordhavn"), phoneDTOList_1, hobbyDTOList_1);

        //Testing delete()
        Phone clevesCell = new Phone("34343434", "digitale indkøbsseddel");
        phoneList_1.add(clevesCell);
        Hobby clevesHobby = new Hobby("Indkøb", "Allergisk overfor Nemlig.com");
        hobbyList_1.add(clevesHobby);
        Address cleveAddress = new Address("Roskildevej", "IndbkøbsCentrum");
        cleveAddress.setCityInfo(new CityInfo(2605, "Brøndby"));
        person_1 = new Person("Cleve", "Sama", phoneList_1);
        person_1.setHobbies(hobbyList_1);
        person_1.setAddress(cleveAddress);

        //Testing getByPhone()
        Phone gallerPhone = new Phone("12121212", "Nunu phone");
        phoneList_2.add(gallerPhone);
        Hobby gallerHobby = new Hobby("Elden Ring", "YOU DIED");
        hobbyList_2.add(gallerHobby);
        Address gallerAddress = new Address("BingBong vej 58", "SpektrumCentrum");
        gallerAddress.setCityInfo(new CityInfo(4100,"Ringested"));
        person_2 = new Person("Galler", "Thicci", phoneList_2);
        person_2.setHobbies(hobbyList_2);
        person_2.setAddress(gallerAddress);

        //Testing getAllByHobby();
        Phone oliPhone = new Phone("77771111", "snor-på-dåse");
        phoneList_3.add(oliPhone);
        Hobby oliHobby = new Hobby("minimalistisk kunst-entusiast", "se maling tørre");
        hobbyList_3.add(oliHobby);
        Address oliAddress = new Address ("LimSpiser vej 48", "BingBy");
        oliAddress.setCityInfo(new CityInfo(3100, "Hornbæk"));
        person_3 = new Person("Oliver", "Snoo", phoneList_3);
        person_3.setHobbies(hobbyList_3);
        person_3.setAddress(oliAddress);

        Phone fooPhone = new Phone("11112222", "fooPhone");
        phoneList_4.add(fooPhone);
//        Hobby fooHobby = new Hobby("foobar", "foobar");
//        hobbyList_4.add(fooHobby);
        Address fooAddress = new Address("foo", "bar");
        fooAddress.setCityInfo(new CityInfo(3400, "Hillerød"));
        person_4 = new Person("foo","bar", phoneList_4);
        person_4.setHobbies(hobbyList_3);
        person_4.setAddress(fooAddress);

        //Testing edit()
        Phone weinellPhone = new Phone("88889999", "businessPhone");
        phoneList_5.add(weinellPhone);
        Hobby weinellHobby = new Hobby("Pimp", "Rolls in money");
        hobbyList_5.add(weinellHobby);
        Address weinellAddress = new Address("RigmandSted", "Han er rig");
        weinellAddress.setCityInfo(new CityInfo(2900, "Hellerup"));
        person_5 = new Person("Pimpolai", "Weinell", phoneList_5);
        person_5.setHobbies(hobbyList_5);
        person_5.setAddress(weinellAddress);



        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            //Testing delete()
            em.persist(person_1);
            em.persist(person_2);

            //Testing getByPhone()
            em.persist(person_3);
            em.persist(person_4);

            //Testing edit()
            em.persist(person_5);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getRepo() {
        assertNotNull(personRepository);
    }

    //Add Person to Database ( PASSED )
    @Test
    void add() {
        PersonDTO actual = personRepository.add(personDTO_1);
        PersonDTO expected = personDTO_1;
        assertEquals(expected.equals(actual), actual.equals(expected));
    }

    //Delete person from Database ( PASSED )
    @Test
    void delete() {
        PersonDTO actual = personRepository.delete(person_1.getId());
        PersonDTO expected = new PersonDTO(person_1);
        assertEquals(expected.equals(actual), actual.equals(expected));
    }

    //Get Person With a Given ID ( PASSED )
    @Test
    void getById() {
        PersonDTO actual = personRepository.getById(person_1.getId());
        PersonDTO expected = personDTO_1;

        personRepository.add(personDTO_1);
        assertEquals(expected.equals(actual), actual.equals(expected));
    }

    //Get Person by phoneNumber ( PASSED )
    @Test
    void getByPhone() {
        PersonDTO actual = personRepository.getByPhone("12121212");
        System.out.println(actual.toString());

        assertNotNull(actual);
    }

    //Get all Persons with a given hobby ( PASSED )
    @Test
    void getAllByHobby() {
        List<PersonDTO> expected = new ArrayList<>();
        expected.add(personRepository.getById(1l)); //foo
        expected.add(personRepository.getById(2l)); //Oliver

        List<PersonDTO> actual = personRepository.getAllByHobby("minimalistisk kunst-entusiast");

        assertEquals(expected.equals(actual), actual.equals(expected));
    }

    //Get all Persons living in a given city ( PASSED )
    @Test
    void getAllByCity() {
        List<PersonDTO> actual = personRepository.getAllByCity("2");
        assertNotNull(actual);
    }

    //Get all Persons ( PASSED )
    @Test
    void getAll() {
        List<PersonDTO> actual = personRepository.getAll();
        for (PersonDTO personDTO : actual) {
            System.out.println(personDTO.getFirstname());
        }
        assertNotNull(actual);
    }

    //Edit Persons ( PASSED )
    @Test
    void edit() {
        PersonDTO actual_preEdit = personRepository.getById(4l);
        PersonDTO actual_postEdit = actual_preEdit;
        actual_postEdit.setFirstname("Nikolaj");

        personRepository.edit(actual_postEdit);

        String expectedName = "Nikolaj";

        assertEquals(expectedName, actual_postEdit.getFirstname());

    }
}