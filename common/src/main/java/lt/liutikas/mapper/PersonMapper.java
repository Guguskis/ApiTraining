package lt.liutikas.mapper;

import lt.liutikas.dto.CreatePersonDto;
import lt.liutikas.dto.LanguagePersonDto;
import lt.liutikas.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class PersonMapper {

    private HashMap<Long, String> languages = new HashMap<>();

    public PersonMapper() {
        languages.put(1L, "Lithuanian");
        languages.put(2L, "English");
        languages.put(3L, "Indian");
        languages.put(4L, "Australian");
        languages.put(5L, "Gibberish");
    }

    public Person toPerson(CreatePersonDto dto) {
        Person person = new Person();
        person.setName(dto.getName());
        person.setOfficialId(dto.getOfficialId());
        person.setLanguageId(dto.getLanguagelId());
        return person;
    }

    public List<LanguagePersonDto> getLanguagePersonsDto(List<Person> persons) {
        List<LanguagePersonDto> mappedPersons = new ArrayList<>();
        persons.forEach(person -> mappedPersons.add(getLanguagePersonDto(person)));
        return mappedPersons;
    }

    private LanguagePersonDto getLanguagePersonDto(Person person) {
        LanguagePersonDto mappedPerson = new LanguagePersonDto();
        String language = languages.get(person.getLanguageId());

        mappedPerson.setId(person.getId());
        mappedPerson.setOfficialId(person.getOfficialId());
        mappedPerson.setName(person.getName());
        mappedPerson.setLanguage(language);
        return mappedPerson;
    }
}
