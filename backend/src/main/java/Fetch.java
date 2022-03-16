import dtos.CityInfoDTO;
import dtos.PersonDTO;
import entities.CityInfo;
import repository.PersonRepository;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class Fetch {
    public static void main(String[] args) {
        PersonRepository.getCityInfo();
    }
}
