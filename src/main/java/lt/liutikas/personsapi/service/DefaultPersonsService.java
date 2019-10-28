package lt.liutikas.personsapi.service;

import lt.liutikas.paymentsapi.model.Payment;
import lt.liutikas.personsapi.exception.PersonNotFoundException;
import lt.liutikas.personsapi.model.Person;
import lt.liutikas.personsapi.repository.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultPersonsService implements PersonsService {

    private PersonsRepository repository;

    @Autowired
    public DefaultPersonsService(PersonsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public Person save(Person person) {
        return repository.save(person);
    }

    @Override
    public void delete(long id) {
        if (personExists(id)) {
            var personToDelete = getPerson(id);
            repository.delete(personToDelete);
        }
    }

    private Person getPerson(long id) {
        return repository.findById(id).get();
    }

    @Override
    public Person find(long id) throws PersonNotFoundException {
        var personToFind = repository.findById(id);
        if (personToFind.isPresent()) {
            return personToFind.get();
        } else {
            throw new PersonNotFoundException();
        }
    }

    private boolean personExists(long id) {
        return repository.findById(id).isPresent();
    }


}
