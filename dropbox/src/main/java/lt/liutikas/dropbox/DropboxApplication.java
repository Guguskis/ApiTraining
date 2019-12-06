package lt.liutikas.dropbox;

import lt.liutikas.dropbox.route.HandlingRoute;
import lt.liutikas.dropbox.route.ImportingRoute;
import lt.liutikas.dropbox.route.MainRoute;
import lt.liutikas.dropbox.route.ParsingRoute;
import lt.liutikas.dropbox.route.validation.ValidationRoute;
import lt.liutikas.model.Person;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DropboxApplication extends RouteBuilder {
    private final CamelContext context;

    public DropboxApplication(CamelContext context) {
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(DropboxApplication.class, args);
    }

    @Override
    public void configure() throws Exception {
        context.addRoutes(new ValidationRoute());
        context.addRoutes(new HandlingRoute());
        context.addRoutes(new ParsingRoute());
        context.addRoutes(new ImportingRoute());
        context.addRoutes(new MainRoute());
    }

    private Processor mappingProcessor() {
        return new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                List<List<String>> data = exchange.getIn().getBody(List.class);
                List<String> columnNames = data.remove(0);
                List<Person> persons = new ArrayList<>();
                // Todo use stream (reducer?)
                data.forEach(line -> {
                    Person person = getMappedPerson(columnNames, line);
                    persons.add(person);
                });
                exchange.getIn().setBody(persons);
            }

            private Person getMappedPerson(List<String> columnNames, List<String> line) {
                Person person = new Person();
                for (int i = 0; i < columnNames.size(); i++) {
                    String key = columnNames.get(i).trim();
                    String value = line.get(i).trim();
                    switch (key) {
                        case "name":
                            person.setName(value);
                            break;
                        case "officialId":
                            person.setOfficialId(Long.parseLong(value));
                            break;
                        case "languageId":
                            person.setLanguageId(Long.parseLong(value));
                            break;
                        default:
                            // Todo use String format
                            // String.format()
                            System.out.println("Column name \"" + key + "\" is incorrect");
                    }
                }
                return person;
            }
        };
    }

}
