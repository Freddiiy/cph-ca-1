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
    PersonDTO getByPhone(String phone);
    List<PersonDTO> getAll();
    List<PersonDTO> getAllByHobby(String hobby);
    List<PersonDTO> getAllByCity(CityInfoDTO cityInfoDTO);
    PersonDTO edit(PersonDTO personDTO);
    List<CityInfoDTO> getZipCode();
}
