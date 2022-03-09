package entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Address {

    @OneToMany(mappedBy = "address") //Vi får sikkert en fejl her. Mvh Oli
    private List<Person> residents;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="address")
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
