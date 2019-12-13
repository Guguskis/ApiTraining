package lt.liutikas.dropbox.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class MainRouteBuilder extends RouteBuilder {
    private final String ROOT_URL = "http://localhost:8082/api";
    private final String ROOT_FOLDER = "file://dropbox/files";

    // @formatter:off
    @Override
    public void configure() {
        from(ROOT_FOLDER + "/in?move=../success&moveFailed=../failure")
                .routeId("simplerMainRoute")
                    .unmarshal().csv()
                    .to("direct:parse")
                    .to("direct:import")
                .end();

        from("direct:import")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .marshal().json()
                .to(ROOT_URL + "/persons/batch");
    }
    // @formatter:on

}
