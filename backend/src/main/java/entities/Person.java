package entities;

import dtos.PersonDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String firstname;
    private String lastname;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    private List<Phone> phones = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "HOBBY_PERSON",
        joinColumns = @JoinColumn(name ="hobby_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name ="person_id", referencedColumnName = "id"))
    private List<Hobby> hobbies = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address")
    private Address address;

    private Date createdAt;
    private Date lastEdited;

    public Person() {}

    public Person(String firstname, String lastname, List<Phone> phones) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phones = phones;

        setOwners();
    }

    public Person(PersonDTO personDTO) {
        if (personDTO.getId() != null) {
            this.id = personDTO.getId();
            this.firstname = personDTO.getFirstname();
            this.lastname = personDTO.getLastname();
            this.address = new Address(personDTO.getAddress());
            this.phones = Phone.convertFromDTO(personDTO.getPhones());
            this.hobbies = Hobby.convertfromDTO(personDTO.getHobbies());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setOwners() {
        for (Phone phone : this.phones) {
            phone.setOwner(this);
        }
    }


    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void lastEdited() {
        lastEdited = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstname, person.firstname) && Objects.equals(lastname, person.lastname) && Objects.equals(phones, person.phones) && Objects.equals(hobbies, person.hobbies) && Objects.equals(address, person.address) && Objects.equals(createdAt, person.createdAt) && Objects.equals(lastEdited, person.lastEdited);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, phones, hobbies, address, createdAt, lastEdited);
    }
}