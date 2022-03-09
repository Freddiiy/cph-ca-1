package repository;

import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import utils.EMF_Creator;

import javax.enterprise.inject.spi.Bean;
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


        List<Hobby> GallerHobbies = new ArrayList<>();
        GallerHobbies.add(new Hobby("Incel", "Ahri fanboi"));
        GallerHobbies.add(new Hobby("Code Genius", "DevMaster"));

        List<Hobby> BeanHobbies = new ArrayList<>();
        BeanHobbies.add(new Hobby("Dårlig ernæring", "Tager Daus"));
        BeanHobbies.add(new Hobby("Atlet", "Løber som vinden"));
        BeanHobbies.add(new Hobby("No-Life", "230.4 Timer på LoLA"));

        List<Hobby> CleveHobbies = new ArrayList<>();
        CleveHobbies.add(new Hobby("Militær", "Er klar på russerne"));
        CleveHobbies.add(new Hobby("League of Legends", "Spiller kun svære champs"));

        List<Hobby> OliHobbies = new ArrayList<>();
        OliHobbies.add(new Hobby("Semi-Life", "199.5 Timer på LoLA"));
        OliHobbies.add(new Hobby("Fitness", "Tror han er stor"));
        OliHobbies.add(new Hobby("Toilet", "skal hele tiden pisse"));


        Person p1 = new Person("Frederik", "Galler", GallersTelefoner);
        Person p2 = new Person("Bean", "Beanson", BeansTelefoner);
        Person p3 = new Person("Cleve", "Cleveson", ClevesTelefoner);
        Person p4 = new Person("Oli", "Olison", OliversTelefoner);

        p1.setHobbies(GallerHobbies);
        p2.setHobbies(BeanHobbies);
        p3.setHobbies(CleveHobbies);
        p4.setHobbies(OliHobbies);

        p1.setAddress(new Address("Gallerstræde 2", "Istedgade 2.0"));
        p2.setAddress(new Address("Luterra City Street 48", "Noob-Area"));
        p3.setAddress(new Address("Clevegade 8", "Bean's Daus hjørne"));
        p4.setAddress(new Address("Shushire Igloo nummer 2", "Slavestræde"));



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
