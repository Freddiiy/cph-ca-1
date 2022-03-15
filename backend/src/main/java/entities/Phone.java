package entities;

import dtos.PhoneDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person owner;

    private String number;
    private String description;

    public Phone(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public Phone(PhoneDTO phoneDTO) {
        if (phoneDTO.getNumber() != null) {
            this.number = phoneDTO.getNumber();
            this.description = phoneDTO.getDescription();
        }
    }

    public Phone()
    {
    }

    public static List<Phone> convertFromDTO(List<PhoneDTO> phoneDTOs) {
        List<Phone> phoneList = new ArrayList<>();
        for (PhoneDTO phoneDTO : phoneDTOs) {
            phoneList.add(new Phone(phoneDTO));
        }

        return phoneList;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return "Phone:" + "\n" +
                "owner: " + owner + "\n" +
                "number: " + number + '\n' +
                "description: " + description;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setOwner(Person owner)
    {
        this.owner = owner;
    }

    public Person getOwner() {
        return owner;
    }
}
