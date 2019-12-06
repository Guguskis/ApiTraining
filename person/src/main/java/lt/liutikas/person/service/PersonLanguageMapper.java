package lt.liutikas.person.service;

import lt.liutikas.model.LanguagePersonDTO;
import lt.liutikas.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class PersonLanguageMapper {

    private HashMap<Long, String> languages = new HashMap<>();

    public PersonLanguageMapper() {
        languages.put(1L, "Lithuanian");
        languages.put(2L, "English");
        languages.put(3L, "Indian");
        languages.put(4L, "Australian");
        languages.put(5L, "Gibberish");
    }


    public List<LanguagePersonDTO> getMappedPersons(List<Person> persons) {
        List<LanguagePersonDTO> mappedPersons = new ArrayList<>();
        persons.forEach(person -> mappedPersons.add(getLanguagePersonDTO(person)));
        return mappedPersons;
    }

    private LanguagePersonDTO getLanguagePersonDTO(Person person) {
        LanguagePersonDTO mappedPerson = new LanguagePersonDTO();
        String language = languages.get(person.getLanguageId());

        mappedPerson.setId(person.getId());
        mappedPerson.setOfficialId(person.getOfficialId());
        mappedPerson.setName(person.getName());
        mappedPerson.setLanguage(language);
        return mappedPerson;
    }
}
