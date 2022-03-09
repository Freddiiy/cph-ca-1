package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "address") //Vi får sikkert en fejl her. Mvh Oli
    private List<Person> residents = new ArrayList<>();

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="cityInfo")
    private CityInfo cityInfo;

    public String street;
    public String description;

    public Address(String street) {
        this.street = street;
    }

    public Address(String street, String description) {
        this.street = street;
        this.description = description;
    }

    public Address()
    {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
