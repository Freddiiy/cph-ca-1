package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityInfoDTO;
import dtos.PersonDTO;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import utils.CityInfoApi;
import utils.EMF_Creator;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonRepository implements IPersonRepository {

    private static EntityManagerFactory emf;
    private static PersonRepository instance;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private PersonRepository() {
    }

    public static PersonRepository getRepo(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonRepository();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public PersonDTO add(PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Person(personDTO));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return personDTO;
    }

    @Override
    public PersonDTO delete(Long id) {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, id);

        try {
            em.getTransaction().begin();
            for (Phone phone : person.getPhones()) {
                em.remove(phone);
            }
            for (Hobby hobby : person.getHobbies() ) {
                em.remove(hobby);
            }
            em.remove(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new PersonDTO(person);
    }

    @Override
    public PersonDTO getById(Long id) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);

        return new PersonDTO(person);
    }

    @Override
    public PersonDTO getByPhone(String phone) {
        EntityManager em = getEntityManager();
        Phone query = em.createQuery("SELECT p FROM Phone p WHERE p.number=:number", Phone.class)
                .setParameter("number", phone)
                .getSingleResult();

        Person result = em.find(Person.class, query.getOwner().getId());

        PersonDTO personDTO = getById(query.getOwner().getId());
        if (personDTO == null) {
            throw new EntityExistsException("Person with phone :" + phone + " couldn't be found.");
        }

        return new PersonDTO(result);
    }

    @Override
    public List<PersonDTO> getAllByHobby(String hobby) {
        EntityManager em = getEntityManager();
        List<Person> hobby_query = em.createQuery("SELECT h.persons FROM Hobby h WHERE h.name=:hobby", Person.class)
                .setParameter("hobby", hobby)
                .getResultList();
        return PersonDTO.convertToDTO(hobby_query);
    }

    @Override
    public List<PersonDTO> getAllByCity(String cityInfo) {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.address.cityInfo.id=:cityInfo", Person.class)
                .setParameter("cityInfo", Long.parseLong(cityInfo));
        return PersonDTO.convertToDTO(query.getResultList());
    }

    @Override
    public List<PersonDTO> getAll() {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);

        return PersonDTO.convertToDTO(query.getResultList());
    }

    @Override
    public PersonDTO edit(PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        Person person = new Person(personDTO);
        if (person.getId() == null) return null;
        try {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new PersonDTO(person);
    }

    @Override
    public List<CityInfoDTO> getZipCode(){
        EntityManager em = getEntityManager();
        TypedQuery<CityInfoDTO> query = em.createQuery("SELECT c FROM CityInfo c", CityInfoDTO.class);
        return query.getResultList();
    }

    public static List<CityInfo> getCityInfo () {
        EntityManager em1 = emf.createEntityManager();
        TypedQuery<CityInfo> query = em1.createQuery("select c from CityInfo c", CityInfo.class);
        System.out.println(query.getResultList().size());

        if (query.getResultList().size() > 0) {
            return query.getResultList();
        }
        em1.close();

        try {
            URL url = new URL("https://api.dataforsyningen.dk/postnumre");
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("ACCEPT", MediaType.APPLICATION_JSON);
            Scanner scanner = new Scanner(urlConnection.getInputStream());
            StringBuilder jsonString = new StringBuilder();

            while (scanner.hasNext()) {
                jsonString.append(scanner.nextLine());
            }
            scanner.close();

            CityInfoApi[] cities = GSON.fromJson(String.valueOf(jsonString), CityInfoApi[].class);

            List<CityInfo> convertedCities = new ArrayList<>();

            for (CityInfoApi cityInfoApi : cities) {
                convertedCities.add(
                        new CityInfo(
                                Integer.parseInt(cityInfoApi.getNr()),
                                cityInfoApi.getNavn()
                        )
                );
            }

            for (CityInfo cityInfo : convertedCities) {
                EntityManager em2 = emf.createEntityManager();
                try {
                    em2.getTransaction().begin();
                    em2.persist(cityInfo);
                    em2.getTransaction().commit();
                } finally {
                    em2.close();
                }
            }

            return convertedCities;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
