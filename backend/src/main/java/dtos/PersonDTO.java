package dtos;

import entities.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private AddressDTO address;
    private CityInfoDTO cityInfo;
    private List<PhoneDTO> phones;
    private List<HobbyDTO> hobbies;

    public PersonDTO(String firstname, String lastname, AddressDTO address, CityInfoDTO cityInfo, List<PhoneDTO> phones, List<HobbyDTO> hobbies) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.cityInfo = cityInfo;
        this.phones = phones;
        this.hobbies = hobbies;
    }

    public PersonDTO(Person person) {
        if (person.getId() != null) {
            this.id = person.getId();
            this.firstname = person.getFirstname();
            this.lastname = person.getLastname();
            this.address = new AddressDTO(person.getAddress());
            this.cityInfo = new CityInfoDTO(person.getAddress().getCityInfo());
            this.phones = PhoneDTO.convertToDTO(person.getPhones());
            this.hobbies = HobbyDTO.convertToDTO(person.getHobbies());
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public CityInfoDTO getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfoDTO cityInfo) {
        this.cityInfo = cityInfo;
    }

    public void addPhone(PhoneDTO phoneDTO) {
        this.phones.add(phoneDTO);
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }

    public List<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }

    public static List<PersonDTO> convertToDTO(List<Person> personList) {
        List<PersonDTO> personDTOList = new ArrayList<>();
        for (Person person : personList) {
            personDTOList.add(new PersonDTO(person));
        }

        return personDTOList;
    }
}
