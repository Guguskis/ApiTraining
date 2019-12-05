package lt.liutikas.dropbox.validation;

import org.apache.camel.builder.RouteBuilder;

public class ValidationRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:validation")
                .routeId("validationRoute")
                .process(new ValidationProcessor())
                .to("mock:result");
    }
}
