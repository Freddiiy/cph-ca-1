package rest;

import dtos.*;
import entities.*;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import repository.PersonRepository;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test


public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";


    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf; //Testing add()
    private EntityManager em;
    private PersonRepository personRepository;

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


    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return  GrizzlyHttpServerFactory.createHttpServer(BASE_URI,rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
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
        gallerAddress.setCityInfo(new CityInfo(4100, "Ringested"));
        person_2 = new Person("Galler", "Thicci", phoneList_2);
        person_2.setHobbies(hobbyList_2);
        person_2.setAddress(gallerAddress);

        //Testing getAllByHobby();
        Phone oliPhone = new Phone("77771111", "snor-på-dåse");
        phoneList_3.add(oliPhone);
        Hobby oliHobby = new Hobby("minimalistisk kunst-entusiast", "se maling tørre");
        hobbyList_3.add(oliHobby);
        Address oliAddress = new Address("LimSpiser vej 48", "BingBy");
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
        person_4 = new Person("foo", "bar", phoneList_4);
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
        try {
            em.getTransaction().begin();
            //OBS Rækkefølgen er ekstrem vigtig her v
            em.createQuery("DELETE FROM Phone ").executeUpdate();
            em.createQuery("DELETE FROM Hobby ").executeUpdate();
            em.createQuery("DELETE FROM Person ").executeUpdate();
            em.createQuery("DELETE FROM Address ").executeUpdate();
            em.createQuery("DELETE FROM CityInfo ").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/xxx").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/test/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello World"));
    }

    @Test
    public void testCount() throws Exception {
        given()
                .contentType("application/json")
                .get("/xxx/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(2));
    }
}


