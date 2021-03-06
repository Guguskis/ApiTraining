package lt.liutikas.dropbox.route;

import lt.liutikas.model.Person;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParsingRouteBuilder extends RouteBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParsingRouteBuilder.class);

    @Override
    public void configure() {
        from("direct:parse")
                .routeId("parse")
                .process(parsePersons());
    }

    private Processor parsePersons() {
        return new Processor() {
            @Override
            public void process(Exchange exchange) {
                List<List<String>> body = (List<List<String>>) exchange.getIn().getBody(List.class);
                List<Person> parsedPersons = null;

                try {
                    parsedPersons = tryParse(body);
                } catch (Exception e) {
                    LOGGER.info(e.getMessage());
                }

                exchange.getIn().setBody(parsedPersons);
            }
        };
    }

    private List<Person> tryParse(List<List<String>> body) {
        List<String> properties = body.get(0);
        List<List<String>> unparsedPersons = body.subList(1, body.size());

        List<Person> parsedPersons = new ArrayList<>();

        for (List<String> unparsedPerson : unparsedPersons) {
            parsedPersons.add(getParsedPerson(properties, unparsedPerson));
        }

        return parsedPersons;
    }

    private Person getParsedPerson(List<String> properties, List<String> row) {
        Person person = new Person();
        for (int i = 0; i < row.size(); i++) {
            String property = properties.get(i).toLowerCase().trim();
            String value = row.get(i).trim();
            setValueOfPerson(person, property, value);
        }
        return person;
    }

    private void setValueOfPerson(Person person, String property, String value) {
        switch (property) {
            case "id":
                person.setId(Long.parseLong(value));
                break;
            case "name":
                person.setName(value);
                break;
            case "officialid":
                person.setOfficialId(Long.parseLong(value));
                break;
            case "languageid":
                person.setLanguageId(Long.parseLong(value));
                break;
        }
    }

}
