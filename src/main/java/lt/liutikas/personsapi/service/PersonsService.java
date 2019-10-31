package lt.liutikas.personsapi.service;

import lt.liutikas.paymentsapi.model.Payment;
import lt.liutikas.personsapi.exception.PersonNotFoundException;
import lt.liutikas.personsapi.model.Person;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

public interface PersonsService {
    List<Person> findAll();

    Person save(Person person);

    void delete(long id) throws PersonNotFoundException;

    Person find(long id) throws PersonNotFoundException;

}
