package lt.liutikas.dropbox.route.validation;

import org.apache.camel.builder.RouteBuilder;

public class ValidationRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:validation")
                .routeId("validation")
                .process(new ValidationProcessor())
                //Todo move mock to tests
                .to("mock:result");
    }
}
