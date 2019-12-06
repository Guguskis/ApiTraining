package lt.liutikas.dropbox.route;

import lt.liutikas.model.Person;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImportingRoute extends RouteBuilder {

    private RestTemplate template = new RestTemplate();

    @Override
    public void configure() {
        from("direct:import")
//                .process(setFirstPersonToBody());
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .marshal().json()
                .to("http://localhost:8082/api/persons")
                .unmarshal().json()
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        List<Map> unmappedPeople = exchange.getIn().getBody(List.class);
                        List<Person> mappedPeople = new ArrayList<>();

                        unmappedPeople.forEach(unmappedPerson -> {
                            Person mappedPerson = new Person();
                            unmappedPerson.forEach((property, value) -> {
                                setValueOfPerson(mappedPerson, property.toString(), value.toString());
                            });
                            mappedPeople.add(mappedPerson);
                        });

                        exchange.getIn().setBody(mappedPeople);
                    }
                })
                .process(exchange -> {
                    List<Person> people = exchange.getIn().getBody(List.class);
                    printNames(people);
                });
    }

    private void setValueOfPerson(Person person, String property, String value) {
        switch (property) {
            case "id":
                person.setId(Long.parseLong(value));
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

    private void printNames(List<Person> people) {
        people.forEach(person -> {
            System.out.println(person.getName());
        });
    }
}
