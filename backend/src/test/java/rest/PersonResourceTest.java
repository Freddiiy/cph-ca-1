package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.*;
import entities.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import repository.PersonRepository;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;


class PersonResourceTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private PersonRepository personRepository;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }


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

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        personRepository = PersonRepository.getRepo(emf);

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


    }

    @AfterEach
    void tearDown() {

        try {
            em.getTransaction().begin();
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

    //DEMO TEST ( PASSED )
    @Test
    void demoTest() {
        given()
                .contentType(ContentType.JSON)
                .get("/person/test")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello World"));
    }

    //Get by ID ( PASSED )
    //Expected JSON Object = Person ={firstname = Cleve}
    @Test
    void getPersonByIDTest() {
        given()
                .contentType(ContentType.JSON)
                .get("/person/" + person_2.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("firstname", equalTo(person_2.getFirstname()));
    }

    //Get All Persons ( PASSED )
    @Test
    void getAllPersonsTest() {
        List<PersonDTO> extractedDTOs;
        extractedDTOs = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/person")
                .then()
                .extract().body().jsonPath().getList("", PersonDTO.class);

        assertEquals(personRepository.getAll().equals(extractedDTOs), extractedDTOs.equals(personRepository.getAll()));
    }

    //Add Person ( Passed? )
    @Test
    void addPersonTest() {

        List<PhoneDTO> expPhoneList = new ArrayList<>();
        expPhoneList.add(new PhoneDTO("64646464", "Hippo Bling"));

        List<HobbyDTO> expHobbyList = new ArrayList<>();
        expHobbyList.add(new HobbyDTO("Likes em big", "Likes em chonky"));

        AddressDTO expAddress = new AddressDTO("ChunkyStr", "Mudderbad");
        CityInfoDTO cityInfoDTO = new CityInfoDTO(1650, "Istedgade");
        expAddress.setCityInfoDTO(cityInfoDTO);

        PersonDTO expPerson = new PersonDTO("Moto", "Moto",
                expAddress, cityInfoDTO, expPhoneList, expHobbyList);

        String requestBody = GSON.toJson(expPerson);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/person/add")
                .then()
                .assertThat()
                .statusCode(200)
                .body("firstname", equalTo("Moto"))
                .body("lastname", equalTo("Moto"))
                .body("phones", hasItems(hasEntry("number", "64646464")));

    }

    //Delete Person ( Passed )
    @Test
    void deletePersonTest() {
        Long id = person_3.getId();

        given()
                .contentType(ContentType.JSON)
                .delete("/person/" + id + "")
                .then()
                .assertThat()
                .statusCode(200);
        //Det virker, men vi ved ikke helt hvordan vi skulle assert det.
    }

    //Update Person ( Passed )
    @Test
    void updatePersonTest() {
        person_2.setFirstname("Willump");
        PersonDTO pdto = new PersonDTO(person_2);
        String requestBody = GSON.toJson(pdto);

        given()
                .header("Content-type", ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/person/" + person_2.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .body("firstname", equalTo("Willump"));
    }

    //Get All People by Hobby name ( Passed )
    @Test
    void getAllPeopleByHobbyName() {
        List<PersonDTO> resultList = new ArrayList<>();
        resultList = given()
                .contentType(ContentType.JSON)
//                .pathParam("id", p1.getId()).when()
                .get("/person/hobby/{hobby}", hobbyList_3.get(0).getName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract().body().jsonPath().getList("", PersonDTO.class);

        List<PersonDTO> expectedList = new ArrayList<>();
        expectedList = personRepository.getAllByHobby(hobbyList_3.get(0).getName());

        assertEquals(expectedList.equals(resultList), resultList.equals(expectedList));
    }

    //Number of People with given hobby ( Passed )
    @Test
    void NumOfPeopleWithGivenHobbyTest() {
        String numOfPeople = get("/person/numberof/{hobby}", person_1.getHobbies().get(0).getName())
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .asString();

        int parsedNumber = Integer.parseInt(numOfPeople);

        assertEquals(1, parsedNumber);
    }

    //Get List of People living in a city ( Passed )
    @Test
    void getListOfPeopleInCity() {
        List<PersonDTO> extractedDTOs;
        extractedDTOs = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/person/city/{city}", "Brøndby")
                .then()
                .extract().body().jsonPath().getList("", PersonDTO.class);

        List<PersonDTO> expectedList;
        expectedList = personRepository.getAllByCity("Brøndby");

        assertEquals(expectedList.equals(extractedDTOs), extractedDTOs.equals(expectedList));

    }

    //Get Person By Phone ( Passed )
    @Test
    void getPersonByPhoneTest() {
        given()
                .contentType(ContentType.JSON)
                .get("/person/phone/{phone}", person_1.getPhones().get(0).getNumber())
                .then()
                .assertThat()
                .body("firstname", equalTo(person_1.getPhones().get(0).getOwner().getFirstname()));
    }

    //Get Lists of Cities by Zip ( passed)
    @Test
    void getListOfZipcodes() {
        List<CityInfoDTO> extractedDTOs;
        extractedDTOs = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/person/zipcode")
                .then()
                .extract().body().jsonPath().getList("", CityInfoDTO.class);

        List<CityInfoDTO> expectedList;
        expectedList = personRepository.getZipCode();

        assertEquals(expectedList.equals(extractedDTOs), extractedDTOs.equals(expectedList));
    }
}