package entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Hobby {

    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;

    @ManyToMany(mappedBy = "hobbies")
    private List<Person> persons;

    public Hobby(String name) {
        this.name = name;
    }

    public Hobby(String name, String description) {
        this.name = name;
        this.description = description;
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
}
