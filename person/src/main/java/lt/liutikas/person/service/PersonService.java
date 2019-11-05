package lt.liutikas.person.service;

import lt.liutikas.person.exception.PersonAlreadyExistsException;
import lt.liutikas.person.exception.PersonNotFoundException;
import lt.liutikas.person.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();

    void create(Person person) throws PersonAlreadyExistsException;

    void delete(long id) throws PersonNotFoundException;

    Person find(long id) throws PersonNotFoundException;

}
