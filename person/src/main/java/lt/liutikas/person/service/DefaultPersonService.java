package lt.liutikas.person.service;

import lt.liutikas.exception.PersonAlreadyExistsException;
import lt.liutikas.exception.PersonNotFoundException;
import lt.liutikas.mapper.PersonLanguageMapper;
import lt.liutikas.model.LanguagePersonDTO;
import lt.liutikas.model.Person;
import lt.liutikas.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultPersonService implements PersonService {

    private PersonRepository repository;
    private PersonLanguageMapper languageMapper;

    @Autowired
    public DefaultPersonService(PersonRepository repository, PersonLanguageMapper languageMapper) {
        this.repository = repository;
        this.languageMapper = languageMapper;
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public List<LanguagePersonDTO> findAllMapped() {
        List<Person> persons = repository.findAll();
        return languageMapper.getLanguagePersonsDto(persons);
    }

    @Override
    public void create(Person person) throws PersonAlreadyExistsException {
        try {
            repository.save(person);
        } catch (DataIntegrityViolationException e) {
            throw new PersonAlreadyExistsException();
        }
    }

    @Override
    public void delete(long officialId) {
        repository.deleteByOfficialId(officialId);
    }

    @Override
    public Person find(long officialId) throws PersonNotFoundException {
        Optional<Person> person = Optional.ofNullable(repository.findByOfficialId(officialId));

        if (person.isPresent()) {
            return person.get();
        } else {
            throw new PersonNotFoundException();
        }
    }

    @Override
    public void create(List<Person> peopleToCreate) throws PersonAlreadyExistsException {
        List<Person> uncreatedPeople = new ArrayList<>();
        peopleToCreate.forEach(person -> {
            try {
                create(person);
            } catch (PersonAlreadyExistsException e) {
                uncreatedPeople.add(person);
            }
        });

        if (!uncreatedPeople.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("People that couldn't be created:");
            uncreatedPeople.forEach(builder::append);
            throw new PersonAlreadyExistsException(builder.toString());
        }
    }
}
