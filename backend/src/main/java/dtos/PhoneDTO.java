package dtos;

import entities.Phone;

import java.util.ArrayList;
import java.util.List;

public class PhoneDTO {
    private String number;
    private String description;

    public PhoneDTO(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public PhoneDTO(Phone phone) {
        this.number = phone.getNumber();
        this.description = phone.getDescription();
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

    public static List<PhoneDTO> convertToDTO(List<Phone> phones) {
        List<PhoneDTO> phoneDTOList = new ArrayList<>();
        for (Phone phone : phones) {
            phoneDTOList.add(new PhoneDTO(phone));
        }

        return phoneDTOList;
    }
}
