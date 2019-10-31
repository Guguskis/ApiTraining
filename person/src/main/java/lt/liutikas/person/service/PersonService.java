package lt.liutikas.person.service;

import lt.liutikas.person.exception.PersonNotFoundException;
import lt.liutikas.person.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();

    Person save(Person person);

    void delete(long id) throws PersonNotFoundException;

    Person find(long id) throws PersonNotFoundException;

}
