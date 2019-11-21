package lt.liutikas.person.service;

import lt.liutikas.exception.PersonAlreadyExistsException;
import lt.liutikas.exception.PersonNotFoundException;
import lt.liutikas.model.LanguagePersonDTO;
import lt.liutikas.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();

    void create(Person person) throws PersonAlreadyExistsException;

    void delete(long id) throws PersonNotFoundException;

    Person find(long id) throws PersonNotFoundException;

    List<LanguagePersonDTO> findAllMapped();
}
