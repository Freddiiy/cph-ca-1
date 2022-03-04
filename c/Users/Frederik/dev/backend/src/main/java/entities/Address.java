package entities;

public class Address {
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
