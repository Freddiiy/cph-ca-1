package repository;

import entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Generate {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    public static void main(String[] args) {
        Person p1 = new Person("Frederik", "Galler", "40404040");
        Person p2 = new Person("Lasr", "Henriksen", "67676767");
        Person p3 = new Person("Janne", "Tuborg", "12981298");
        Person p4 = new Person("Ulrik", "Olsen", "12345678");

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
