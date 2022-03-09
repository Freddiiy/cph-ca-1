package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String firstname;
    private String lastname;

    @OneToMany(mappedBy = "owner")
    private List<Phone> phones;

    @ManyToMany
    @JoinTable(
            name ="person_hobby",
            joinColumns = @JoinColumn(name="person_id" , referencedColumnName = "name"),
            inverseJoinColumns = @JoinColumn(name="name", referencedColumnName = "person_id")
    )
    private List<Hobby> hobbies;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "residents")
    private Address address;

    private Date createdAt;
    private Date lastEdited;

    public Person() {}

    public Person(String firstname, String lastname, List<Phone> phones) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phones = phones;
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

    public List<Phone> getPhone() {
        return phones;
    }

    public void setPhone(List<Phone> phones) {
        this.phones = phones;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Date lastEdited) {
        this.lastEdited = lastEdited;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void lastEdited() {
        lastEdited = new Date();
    }
}