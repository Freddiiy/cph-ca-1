package repository;

import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;

import java.util.List;

public interface IPersonRepository {
    PersonDTO add(PersonDTO personDTO);
    PersonDTO delete(Long id);
    PersonDTO getById(Long id);
    PersonDTO getByPhone(PhoneDTO phoneDTO);
    List<PersonDTO> getAll();
    List<PersonDTO> getAllByHobby(HobbyDTO hobbyDTO);
    List<PersonDTO> getAllByCity(CityInfoDTO cityInfoDTO);
    PersonDTO edit(PersonDTO personDTO);
}
