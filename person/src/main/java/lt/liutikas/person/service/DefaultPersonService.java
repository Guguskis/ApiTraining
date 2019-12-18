package lt.liutikas.person.service;

import lt.liutikas.dto.CreatePersonDto;
import lt.liutikas.dto.LanguagePersonDto;
import lt.liutikas.exception.PersonAlreadyExistsException;
import lt.liutikas.exception.PersonNotFoundException;
import lt.liutikas.mapper.PersonMapper;
import lt.liutikas.model.Person;
import lt.liutikas.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultPersonService implements PersonService {

    private final PersonRepository repository;
    private final PersonMapper mapper;

    @Autowired
    public DefaultPersonService(PersonRepository repository, PersonMapper languageMapper) {
        this.repository = repository;
        this.mapper = languageMapper;
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public List<LanguagePersonDto> findAllMapped() {
        List<Person> persons = repository.findAll();
        return mapper.getLanguagePersonsDto(persons);
    }

    @Override
    public void create(CreatePersonDto dto) throws PersonAlreadyExistsException {
        try {
            repository.save(mapper.toPerson(dto));
        } catch (DataIntegrityViolationException e) {
            throw new PersonAlreadyExistsException(e);
        }
    }

    @Override
    public void delete(long officialId) {
        repository.deleteByOfficialId(officialId);
    }

    @Override
    public Person find(long officialId) throws PersonNotFoundException {
        return Optional
                .ofNullable(repository.findByOfficialId(officialId))
                .orElseThrow(PersonNotFoundException::new);

    }

    @Override
    public void create(List<CreatePersonDto> personsDto) throws PersonAlreadyExistsException {
        List<CreatePersonDto> uncreatedPeople = new ArrayList<>();
        personsDto.forEach(personDto -> {
            try {
                create(personDto);
            } catch (PersonAlreadyExistsException e) {
                uncreatedPeople.add(personDto);
            }
        });

        if (!uncreatedPeople.isEmpty()) {
            String message = getUncreatedPeopleMessage(uncreatedPeople);
            throw new PersonAlreadyExistsException(message);
        }
    }

    private String getUncreatedPeopleMessage(List<CreatePersonDto> uncreatedPeople) {
        return uncreatedPeople
                .stream()
                .map(CreatePersonDto::toString)
                .collect(Collectors.joining(System.lineSeparator(),
                        "Unable to create these persons:" + System.lineSeparator(), ""));
    }
}
