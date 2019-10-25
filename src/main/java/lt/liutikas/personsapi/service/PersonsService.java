package lt.liutikas.personsapi.service;

import lt.liutikas.personsapi.model.Person;

import java.util.List;

public interface PersonsService {
    List<Person> findAll();

    Person create(Person person);

    void delete(long id);
}
