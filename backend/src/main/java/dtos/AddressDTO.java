package dtos;

import entities.Address;

public class AddressDTO {
    public String street;
    public String description;

    public AddressDTO(String street, String description) {
        this.street = street;
        this.description = description;
    }

    public AddressDTO(Address address) {
        if (address.street != null) {
            this.street = address.getStreet();
            this.description = address.getDescription();
        }
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
