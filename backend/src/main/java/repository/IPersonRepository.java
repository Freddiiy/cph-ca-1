package repository;

import dtos.PersonDTO;

import java.util.List;

public interface IPersonRepository {
    public PersonDTO add(PersonDTO personDTO);
    public PersonDTO delete(Long id);
    public PersonDTO get(Long id);
    public List<PersonDTO> getAll();
    public PersonDTO edit(PersonDTO personDTO);
}
