package dtos;

import entities.Hobby;

import java.util.ArrayList;
import java.util.List;

public class HobbyDTO {
    private String name;
    private String description;

    public HobbyDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public HobbyDTO(Hobby hobby) {
        this.name = hobby.getName();
        this.description = hobby.getDescription();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<HobbyDTO> convertToDTO(List<Hobby> hobbies) {
        List<HobbyDTO> hobbyDTOList = new ArrayList<>();
        for (Hobby hobby: hobbies) {
            hobbyDTOList.add(new HobbyDTO(hobby));
        }

        return hobbyDTOList;
    }
}
