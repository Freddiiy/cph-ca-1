package entities;

import dtos.HobbyDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;

    public Hobby() {
    }

    @ManyToMany(mappedBy = "hobbies", fetch = FetchType.LAZY)
    private List<Person> persons = new ArrayList<>();

    public Hobby(String name) {
        this.name = name;
    }

    public Hobby(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Hobby(HobbyDTO hobbyDTO) {
        if (hobbyDTO.getName() != null) {
            this.name = hobbyDTO.getName();
            this.description = hobbyDTO.getDescription();
        }
    }

    public Hobby(Long id) {
        this.id = id;
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

    public static List<Hobby> convertfromDTO(List<HobbyDTO> hobbieDTOs) {
        List<Hobby> hobbyList = new ArrayList<>();
        for (HobbyDTO hobbyDTO: hobbieDTOs) {
            hobbyList.add(new Hobby(hobbyDTO));
        }

        return hobbyList;
    }
}
