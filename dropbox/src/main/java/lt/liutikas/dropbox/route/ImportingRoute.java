package lt.liutikas.dropbox.route;

import lt.liutikas.model.Person;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.util.List;

public class ImportingRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:import")
                .process(setFirstPersonToBody())
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("http://localhost:8082/api/persons/");
//                .process(printHeader());
    }

    private Processor printHeader() {
        return new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                Object header = exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE);
                System.out.println("header = " + header);
            }
        };
    }

    private Processor setFirstPersonToBody() {
        return exchange -> {
            List<Person> people = exchange.getIn().getBody(List.class);
//          printNames(people);
            exchange.getIn().setBody(people.get(0));
        };
    }

    private void printNames(List<Person> people) {
        people.forEach(person -> {
            System.out.println(person.getName());
        });
    }
}
