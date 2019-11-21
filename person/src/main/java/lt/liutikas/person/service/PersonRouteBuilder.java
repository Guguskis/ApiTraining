package lt.liutikas.person.service;

import lt.liutikas.model.LanguagePersonDTO;
import lt.liutikas.model.Person;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class PersonRouteBuilder extends RouteBuilder {

    private HashMap<Long, String> languages;

    public PersonRouteBuilder() {
        languages = new HashMap<>();
        languages.put(1L, "Lithuanian");
        languages.put(2L, "English");
        languages.put(3L, "Indian");
        languages.put(4L, "Australian");
        languages.put(5L, "Gibberish");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void configure() throws Exception {
        from("direct:mapLanguageIdToLanguage")
                .process(exchange -> {
                    List<Person> unmappedPersons = exchange.getIn().getBody(List.class);
                    List<LanguagePersonDTO> mappedPersons = getMappedPersons(unmappedPersons);

                    // Q: should this variable be inlined?
                    var out = exchange.getOut();
                    out.setBody(mappedPersons);
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));
    }

    private List<LanguagePersonDTO> getMappedPersons(List<Person> persons) {
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
