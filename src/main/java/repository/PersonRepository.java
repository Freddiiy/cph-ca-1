package repository;

import dtos.PersonDTO;
import entities.Person;

import javax.persistence.*;
import java.util.List;

public class PersonRepository implements IPersonRepository {

    private static EntityManagerFactory emf;
    private static PersonRepository instance;

    private PersonRepository() {}

    public static PersonRepository getFacade(EntityManagerFactory _emf) {
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
            em.persist(personDTO);
            em.getTransaction().commit();;
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
    public PersonDTO get(Long id) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);

        return new PersonDTO(person);
    }

    @Override
    public List<PersonDTO> getAll() {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("select p from Person p", Person.class);

        return PersonDTO.getList(query.getResultList());
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
}
