package lt.liutikas.person.service;

import lt.liutikas.person.model.Person;
import lt.liutikas.person.repository.PersonRepository;
import lt.liutikas.person.exception.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultPersonService implements PersonService {

    private PersonRepository repository;

    @Autowired
    public DefaultPersonService(PersonRepository repository) {
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
    public void delete(long id) throws PersonNotFoundException {
        if (personExists(id)) {
            // MM: use Person type instead of var
            var personToDelete = find(id);
            repository.delete(personToDelete);
        }
        /*
        MM: In this method you make 3 calls to DB: firstly check if exists in DB, then get from DB and finally delete from DB.
        You can just use repository.deleteById(id);
         */
    }

    @Override
    public Person find(long id) throws PersonNotFoundException {
        Person person = repository.findByOfficialId(id);

        if (person != null) {
            return person;
        } else {
            throw new PersonNotFoundException();
        }
    }

    private boolean personExists(long id) {
        try {
            find(id);
            return true;
        } catch (PersonNotFoundException e) {
            return false;
        }
    }
}
