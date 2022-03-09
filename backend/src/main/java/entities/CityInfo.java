package entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class CityInfo {

    @OneToMany(mappedBy = "cityInfo")
    private List<Address> address;

    private int zipCode;
    private String city;

    public CityInfo(int zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
