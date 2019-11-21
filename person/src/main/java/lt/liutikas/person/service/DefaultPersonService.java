package lt.liutikas.person.service;

import lt.liutikas.exception.PersonAlreadyExistsException;
import lt.liutikas.exception.PersonNotFoundException;
import lt.liutikas.model.LanguagePersonDTO;
import lt.liutikas.model.Person;
import lt.liutikas.person.repository.PersonRepository;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultPersonService implements PersonService {

    private final ProducerTemplate template;

    private PersonRepository repository;

    @Autowired
    public DefaultPersonService(PersonRepository repository, ProducerTemplate template) {
        this.repository = repository;
        this.template = template;
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public List<LanguagePersonDTO> findAllMapped() {
        List<Person> persons = repository.findAll();
        return template.requestBody("direct://mapLanguageIdToLanguage", persons, List.class);
    }

    @Override
    public void create(Person person) throws PersonAlreadyExistsException {
        Person personFound = repository.findByOfficialId(person.getOfficialId());

        if (personFound == null) {
            repository.save(person);
        } else {
            throw new PersonAlreadyExistsException();
        }
    }

    @Override
    public void delete(long officialId) {
        repository.deleteByOfficialId(officialId);
    }

    @Override
    public Person find(long officialId) throws PersonNotFoundException {
        Person person = repository.findByOfficialId(officialId);

        if (person != null) {
            return person;
        } else {
            throw new PersonNotFoundException();
        }
    }

}
