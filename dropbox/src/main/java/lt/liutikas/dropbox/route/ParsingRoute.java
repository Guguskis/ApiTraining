package lt.liutikas.dropbox.route;

import lt.liutikas.model.Person;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;

public class ParsingRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:parse")
                .routeId("parse")
                .process(parsePersons());
    }

    private Processor parsePersons() {
        return new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                List body = exchange.getIn().getBody(List.class);

                ArrayList<Person> parsedPersons = null;
                try {
                    parsedPersons = tryParse(body);
                } catch (Exception e) {
                    throw new MalformedInputException(0);
                }

                exchange.getIn().setBody(parsedPersons);
            }
        };
    }

    private ArrayList<Person> tryParse(List body) {
        List<String> properties = (List<String>) body.get(0);
        List<List<String>> unparsedPersons = body.subList(1, body.size());

        var parsedPersons = new ArrayList<Person>();

        unparsedPersons.forEach(row -> {
            parsedPersons.add(getParsedPerson(properties, row));
        });
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
