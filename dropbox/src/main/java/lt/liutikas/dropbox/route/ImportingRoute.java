package lt.liutikas.dropbox.route;

import lt.liutikas.model.Person;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.util.ArrayList;
import java.util.List;

public class ImportingRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:importing")
                .routeId("importing")
                .process(getParsingProcessor())
                .process(exchange -> {
                    List<Person> persons = exchange.getIn().getBody(List.class);


                });
    }

    private Processor getParsingProcessor() {
        return exchange -> {
            List body = exchange.getIn().getBody(List.class);

            List<String> properties = (List<String>) body.get(0);
            List<List<String>> unparsedPersons = body.subList(1, body.size());

            var parsedPersons = new ArrayList<Person>();

            unparsedPersons.forEach(row -> {
                parsedPersons.add(getParsedPerson(properties, row));
            });

            exchange.getIn().setBody(parsedPersons);
        };
    }

    private Person getParsedPerson(List<String> properties, List<String> row) {
        Person person = new Person();
        for (int i = 0; i < row.size(); i++) {
            String property = properties.get(i);
            String value = row.get(i);
            switch (property) {
                case "name":
                    person.setName(value);
                    break;
                case "officialId":
                    person.setOfficialId(Long.parseLong(value));
                    break;
                case "languageId":
                    person.setLanguageId(Long.parseLong(value));
                    break;
            }
        }
        return person;
    }
}
