package dtos;

import entities.Address;
import entities.CityInfo;

public class AddressDTO {
    private String street;
    private String description;
    private CityInfoDTO cityInfoDTO;

    public AddressDTO(String street, String description) {
        this.street = street;
        this.description = description;
    }

    public AddressDTO(Address address) {
        if (address.street != null) {
            this.street = address.getStreet();
            this.description = address.getDescription();
            this.cityInfoDTO = new CityInfoDTO(address.getCityInfo());
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

    public CityInfoDTO getCityInfoDTO() {
        return cityInfoDTO;
    }

    public void setCityInfoDTO(CityInfoDTO cityInfoDTO) {
        this.cityInfoDTO = cityInfoDTO;
    }
}
