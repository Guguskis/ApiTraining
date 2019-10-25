package lt.liutikas.personsapi.service;

import lt.liutikas.personsapi.model.Person;
import lt.liutikas.personsapi.repository.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
    public Person create(Person person) {
        return repository.save(person);
    }

    @Override
    public void delete(long id) {
        var personToDelete = repository.findById(id).get();
        if (personToDelete != null) {
            repository.delete(personToDelete);
        }
    }
}
