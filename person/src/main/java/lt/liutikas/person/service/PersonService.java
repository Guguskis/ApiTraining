package lt.liutikas.person.service;

import lt.liutikas.dto.CreatePersonDto;
import lt.liutikas.dto.LanguagePersonDto;
import lt.liutikas.exception.PersonAlreadyExistsException;
import lt.liutikas.exception.PersonNotFoundException;
import lt.liutikas.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();

    void create(CreatePersonDto person) throws PersonAlreadyExistsException;

    void create(List<CreatePersonDto> people) throws PersonAlreadyExistsException;

    void delete(long id) throws PersonNotFoundException;

    Person find(long id) throws PersonNotFoundException;

    List<LanguagePersonDto> findAllMapped();

}
