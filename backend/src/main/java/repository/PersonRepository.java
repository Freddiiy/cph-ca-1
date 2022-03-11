package repository;

import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class PersonRepository implements IPersonRepository {

    private static EntityManagerFactory emf;
    private static PersonRepository instance;

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
            ;
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
        TypedQuery<Person> query = em.createQuery("SELECT p from Person p where Phone.number=:number", Person.class);
        em.setProperty("number", phone);


        return new PersonDTO(query.getSingleResult());
    }

    @Override
    public List<PersonDTO> getAllByHobby(String hobby) {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("select p from Person p where Hobby.name=:hobby", Person.class);
        em.setProperty("hobby", hobby);

        return PersonDTO.convertToDTO(query.getResultList());
    }

    @Override
    public List<PersonDTO> getAllByCity(String cityInfoDTO) {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("select p from Person p where CityInfo.city=:cityInfo", Person.class);
        em.setProperty("cityInfo", cityInfoDTO);
        return PersonDTO.convertToDTO(query.getResultList());
    }

    @Override
    public List<PersonDTO> getAll() {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("select p from Person p", Person.class);

        return PersonDTO.convertToDTO(query.getResultList());
    }

    @Override
    public PersonDTO edit(PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        if (personDTO.getId() == null) return null;

        try {
            em.getTransaction().begin();
            em.merge(personDTO);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return personDTO;
    }

    @Override
    public List<CityInfoDTO> getZipCode(){
        EntityManager em = getEntityManager();
        TypedQuery<CityInfoDTO> query = em.createQuery("SELECT c FROM CityInfo c", CityInfoDTO.class);
        return query.getResultList();
    }
}
