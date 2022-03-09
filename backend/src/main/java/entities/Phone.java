package entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Phone {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person owner;
    private String number;
    private String description;

    public Phone(String number) {
        this.number = number;
    }

    public Phone(String number, String description) {
        this.number = number;
        this.description = description;
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
}
