package repository;

import entities.Person;
import entities.Phone;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Generate {
    private static final EntityManagerFactory emf = EMF_Creator.createEntityManagerFactoryForTest();

    public static void main(String[] args) {

        List<Phone> GallersTelefoner = new ArrayList<>();
        GallersTelefoner.add(new Phone("44442222", "MamaMichelle's Hotline"));
        GallersTelefoner.add(new Phone("12341234", "Galler's Arbejdstelefon"));

        List<Phone> BeansTelefoner = new ArrayList<>();
        BeansTelefoner.add(new Phone("22223333", "Bean's Daus Smugler"));
        BeansTelefoner.add(new Phone("22223334", "Bean's smadret Iphone"));

        List<Phone> ClevesTelefoner = new ArrayList<>();
        ClevesTelefoner.add(new Phone("66667777", "Cleve's gamle HTC"));
        ClevesTelefoner.add(new Phone("66668777", "Cleve's samling af dårlige jokes"));

        List<Phone> OliversTelefoner = new ArrayList<>();
        OliversTelefoner.add(new Phone("98989898", "Oli's Dåse med Snor"));


        Person p1 = new Person("Frederik", "Galler", GallersTelefoner);
        Person p2 = new Person("Bean", "Beanson", BeansTelefoner);
        Person p3 = new Person("Cleve", "Cleveson", ClevesTelefoner);
        Person p4 = new Person("Oli", "Olison", OliversTelefoner);

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
